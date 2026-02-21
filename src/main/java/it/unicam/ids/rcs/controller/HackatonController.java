/*
 * MIT License
 *
 * Copyright (c) 2026 Riccardo Farina, Cristian Bellesi, Simone Governatori
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package it.unicam.ids.rcs.controller;

import it.unicam.ids.rcs.model.Hackaton;
import it.unicam.ids.rcs.model.Team;
import it.unicam.ids.rcs.model.Utente;
import it.unicam.ids.rcs.model.notifica.Notifica;
import it.unicam.ids.rcs.repository.HackatonRepository;
import it.unicam.ids.rcs.util.ValidatoreHackaton;
import it.unicam.ids.rcs.util.notifica.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe si occupa della gestione delle operazioni che riguardano gli hackaton
 */
@Service
public class HackatonController {
    private Hackaton hackaton;
    private HackatonRepository hackatonRepository;
    private UtenteController utenteController;

    public HackatonController() {
        this.hackatonRepository = new HackatonRepository();
        this.utenteController = new UtenteController();
    }

    public HackatonController(HackatonRepository hackatonRepository) {
        this.hackatonRepository = hackatonRepository;
        this.utenteController = new UtenteController();
    }

    public Hackaton getHackaton() {
        return this.hackaton;
    }

    protected void setHackaton(Hackaton hackaton) {
        this.hackaton = hackaton;
    }

    public HackatonRepository getHackatonRepository() {
        return this.hackatonRepository;
    }

    private void setHackatonRepository(HackatonRepository hackatonRepository) {
        this.hackatonRepository = hackatonRepository;
    }

    public UtenteController getUtenteController() {
        return this.utenteController;
    }

    private void setUtenteController(UtenteController utenteController) {
        this.utenteController = utenteController;
    }

    /**
     * Verifica se le informazioni di base per la creazione dell'hackaton sono
     * valide e, se lo sono, avvia il processo di creazione dell'hackaton.
     *
     * @param dimensioneMassimaTeam La dimensione massima di ciascun team
     * @param regolamento           Il regolamento completo dell'hackaton
     * @param scadenzaIscrizioni    La data di scadenza delle iscrizioni
     * @param inizio                Data di inizio dell'hackaton
     * @param fine                  Data di fine dell'hackaton
     * @param luogo                 Il luogo in cui si svolge l'hackaton
     * @param premio                Il premio in denaro per il vincitore dell'hackaton
     */
    public void creaHackaton(String nome, int dimensioneMassimaTeam, String regolamento, LocalDate scadenzaIscrizioni, LocalDateTime inizio, LocalDateTime fine, String luogo, Double premio) {
        Hackaton hackaton = new Hackaton();
        hackaton.setNome(nome);
        hackaton.setDimensioneMassimaTeam(dimensioneMassimaTeam);
        hackaton.setRegolamento(regolamento);
        hackaton.setScadenzaIscrizioni(scadenzaIscrizioni);
        hackaton.setInizio(inizio);
        hackaton.setFine(fine);
        hackaton.setLuogo(luogo);
        hackaton.setPremio((double) Math.round(premio * 100) / 100);
        hackaton.setOrganizzatore(UtenteController.getUtenteInSessione());

        this.setHackaton(hackaton);
    }

    /**
     * Assegna l'utente indicato attraverso l'e-mail come giudice dell'hackaton
     *
     * @param email L'e-mail dell'utente da designare come giudice
     */
    public void assegnaGiudice(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Nessuna e-mail fornita");
        }

        Utente giudice = this.getUtenteController().cercaUtente(email);
        if (giudice == null) {
            throw new IllegalArgumentException("Utente non trovato con email: " + email);
        }

        if (this.getHackaton().isMembroDelloStaff(giudice)) {
            // Il giudice è già designato come membro dello staff (i.e. Organizzatore, mentore)
            throw new IllegalArgumentException("L'utente è già un membro dello staff: " + email);
        }

        this.getHackaton().setGiudice(giudice);
    }

    /**
     * Assegna gli utenti indicato attraverso le e-mail come mentori dell'hackaton
     *
     * @param emails Le e-mail degli utenti da designare come mentori
     */
    public void aggiungiMentori(List<String> emails) {
        if (emails.isEmpty()) {
            throw new IllegalArgumentException("Nessuna e-mail fornita");
        }

        for (String email : emails) {
            if (email.isBlank()) {
                throw new IllegalArgumentException("E-mail non valorizzata");
            }
            Utente mentore = this.getUtenteController().cercaUtente(email);
            if (mentore == null) {
                throw new IllegalArgumentException("Utente non trovato con email: " + email);
            }

            if (this.getHackaton().isOrganizzatore(mentore) || this.getHackaton().isGiudice(mentore)) {
                // L'utente è già membro dello staff (i.e. Organizzatore, giudice)
                throw new IllegalArgumentException("L'utente è già un membro dello staff: " + email);
            }

            this.getHackaton().aggiungiMentore(mentore);
        }
    }

    /**
     * Registra l'hackaton nel sistema indicando l'utente che lo ha creato come
     * organizzatore dell'hackaton stesso
     *
     * @return L'istanza di <code>Hackaton</code> aggiunto in caso di successo,
     * <code>null</code> altrimenti
     */
    public Hackaton registraHackaton() {
        Hackaton nuovoHackaton = this.getHackaton();
        ValidatoreHackaton validatore = new ValidatoreHackaton(nuovoHackaton, this, this.getUtenteController());
        if (!validatore.validaNuovoHackaton()) {
            return null;
        }

        this.getHackatonRepository().registraHackaton(nuovoHackaton);

        this.notificaHackatonCreato(nuovoHackaton);

        return nuovoHackaton;
    }

    /**
     * Notifica lo staff di un hackaton appena creato della creazione dell'hackaton
     *
     * @param hackaton L'hackaton creato
     */
    private void notificaHackatonCreato(Hackaton hackaton) {
        var gestoreNotifiche = new GestoreNotifiche();
        NotificaFactory factory = new NotificaCreazioneHackatonFactory(hackaton);
        var notificaPerOrganizzatore = factory.getNotifica(hackaton.getOrganizzatore(), hackaton.getOrganizzatore());
        gestoreNotifiche.inviaNotifica(notificaPerOrganizzatore);
        var notificaPerGiudice = factory.getNotifica(hackaton.getOrganizzatore(), hackaton.getGiudice());
        try {
            notificaPerOrganizzatore.ottieniMessaggioPerOrganizzatore();
            notificaPerGiudice.ottieniMessaggioPerGiudice();
        } catch (UnsupportedOperationException e) {
            System.out.println("Eccezione in notificaHackatonCreato: " + e.getMessage()); // TODO rimuovere dopo porting su Springboot
            return;
        }
        gestoreNotifiche.inviaNotifica(notificaPerGiudice);
        for (var mentore : hackaton.getMentori()) {
            var notificaPerMentore = factory.getNotifica(hackaton.getOrganizzatore(), mentore);
            try {
                notificaPerMentore.ottieniMessaggioPerMentore();
            } catch (UnsupportedOperationException e) {
                System.out.println("Eccezione in notificaHackatonCreato: " + e.getMessage()); // TODO rimuovere dopo porting su Springboot
                return;
            }
            gestoreNotifiche.inviaNotifica(notificaPerMentore);
        }
    }

    /**
     * Questo metodo esegue la richiesta verso la repository degli hackaton
     * per recuperare tutti gli hackaton ancora aperti creati da quello specifico
     * utente
     *
     * @param organizzatoreHackaton L'utente che ha organizzato gli hackaton
     * @return una lista di <code>Hackaton</code>
     */
    public List<Hackaton> getListaHackatonModificabili(Utente organizzatoreHackaton) {
        return this.hackatonRepository.getHackatonsConIscrizioniAperte(organizzatoreHackaton);
    }

    /**
     * Questo metodo esegue la richiesta verso la repository degli hackaton
     * per recuperare tutti gli hackaton ancora aperti creati da quello specifico
     * utente.
     *
     * @return una lista di <code>Hackaton</code>
     */
    public List<Hackaton> getListaHackatonAnnullabili() {
        Utente organizzatore = UtenteController.getUtenteInSessione();
        return this.hackatonRepository.getHackatonsConIscrizioniAperte(organizzatore);
    }

    /**
     * Dato il nome di un hackaton, lo seleziona per poter essere modificato
     *
     * @param nomeHackaton Il nome dell'hackaton da selezionare
     * @return L'hackaton trovato
     */
    public Hackaton selezionaHackaton(String nomeHackaton) {
        Hackaton hackatonOriginale = this.hackatonRepository.cercaPerNome(nomeHackaton);
        this.setHackaton(hackatonOriginale);
        return hackatonOriginale;
    }

    /**
     * Questo metodo si occupa di memorizzare le modifiche apportate ad uno specifico hackaton.
     *
     * @param nome                  Il nome dell'hackaton
     * @param dimensioneMassimaTeam La dimensione massima di ciascun team
     * @param regolamento           Il regolamento completo dell'hackaton
     * @param scadenzaIscrizioni    La data di scadenza delle iscrizioni
     * @param inizio                Data di inizio dell'hackaton
     * @param fine                  Data di fine dell'hackaton
     * @param luogo                 Il luogo in cui si svolge l'hackaton
     * @param premio                Il premio in denaro per il vincitore dell'hackaton
     * @param emailGiudice          L'email dell'utente assegnato al ruolo del giudice
     * @param emailMentori          Elenco di email di utenti assegnati come mentori
     * @return l'<code>hackaton</code> con tutte le informazioni aggiornate.
     */
    public Hackaton confermaModifica(String nome, int dimensioneMassimaTeam, String regolamento, LocalDate scadenzaIscrizioni,
                                     LocalDateTime inizio, LocalDateTime fine, String luogo, double premio, String emailGiudice, List<String> emailMentori) {
        List<Utente> mentoriHackatonOriginale = new ArrayList<>(this.hackaton.getMentori());
        Utente giudice = this.utenteController.cercaUtente(emailGiudice);
        List<Utente> mentori = new ArrayList<>();
        for (String emailMentore : emailMentori) {
            mentori.add(this.utenteController.cercaUtente(emailMentore));
        }
        Hackaton hackatonModificato = new Hackaton(nome, dimensioneMassimaTeam, regolamento, scadenzaIscrizioni, inizio, fine, luogo, premio, giudice, mentori);
        ValidatoreHackaton validatoreHackaton = new ValidatoreHackaton(hackatonModificato, this, this.getUtenteController());
        if (!validatoreHackaton.validaHackatonModificato((this.hackaton)))
            return null;
        this.aggiornaInfoHackaton(hackatonModificato);
        // Lo aggiorno sulla repository e poi allineo il riferimento interno
        this.hackaton = this.hackatonRepository.aggiornaHackaton(this.hackaton);
        this.notificaHackatonModificato(hackaton, mentoriHackatonOriginale);
        return this.hackaton;
    }

    /**
     * Questo metodo si occupa di inviare una notifica al giudice ed ai mentori che fanno
     * parte dello staff di un determinato hackaton.
     *
     * @param hackaton                 L'hackaton che è stato modificato.
     * @param mentoriHackatonOriginale i mentori, inizialmente assegnati all'hackaton, che devono essere avvisati della modifica.
     */
    private void notificaHackatonModificato(Hackaton hackaton, List<Utente> mentoriHackatonOriginale) {
        NotificaModificaHackatonFactory notificaFactory = new NotificaModificaHackatonFactory(hackaton);
        Notifica notificaPerGiudice = notificaFactory.getNotifica(hackaton.getOrganizzatore(), hackaton.getGiudice());
        var gestoreNotifiche = new GestoreNotifiche();
        try {
            notificaPerGiudice.ottieniMessaggioPerGiudice();
        } catch (UnsupportedOperationException e) {
            System.out.println("Eccezione in notificaHackatonModificato: " + e.getMessage()); // TODO rimuovere dopo porting su Springboot
            return;
        }

        gestoreNotifiche.inviaNotifica(notificaPerGiudice);
        List<Utente> mentoriDaNotificare = this.ottieniMentoriDaNotificare(mentoriHackatonOriginale, hackaton.getMentori());
        for (Utente mentore : mentoriDaNotificare) {
            Notifica notificaPerMentore = notificaFactory.getNotifica(hackaton.getOrganizzatore(), mentore);
            try {
                notificaPerMentore.ottieniMessaggioPerMentore();
            } catch (UnsupportedOperationException e) {
                System.out.println("Eccezione in notificaHackatonModificato: " + e.getMessage()); // TODO rimuovere dopo porting su Springboot
                return;
            }
            gestoreNotifiche.inviaNotifica(notificaPerMentore);
        }
    }

    /**
     * Questo metodo si occupa di unire le liste di mentori che devono essere notificati
     * della modifica di un hackaton, quindi comprende i mentori assegnati a uno specifico hackaton
     * prima e dopo la modifica
     *
     * @param MentoriHackatonOriginale
     * @param nuovaListaMentori
     * @return
     */
    private List<Utente> ottieniMentoriDaNotificare(List<Utente> MentoriHackatonOriginale, List<Utente> nuovaListaMentori) {
        List<Utente> mentoriDaNotificare = new ArrayList<>(MentoriHackatonOriginale);
        mentoriDaNotificare.addAll(nuovaListaMentori);
        return mentoriDaNotificare;
    }

    /**
     * Questo metodo aggiorna le info dell'hackaton attualmente salvato in memoria con le
     * info dell'hackaton modificato.
     *
     * @param hackatonModificato L'hackaton modificato.
     */
    private void aggiornaInfoHackaton(Hackaton hackatonModificato) {
        this.hackaton.setNome(hackatonModificato.getNome());
        this.hackaton.setDimensioneMassimaTeam(hackatonModificato.getDimensioneMassimaTeam());
        this.hackaton.setRegolamento(hackatonModificato.getRegolamento());
        this.hackaton.setScadenzaIscrizioni(hackatonModificato.getScadenzaIscrizioni());
        this.hackaton.setInizio(hackatonModificato.getInizio());
        this.hackaton.setFine(hackatonModificato.getFine());
        this.hackaton.setLuogo(hackatonModificato.getLuogo());
        this.hackaton.setGiudice(hackatonModificato.getGiudice());
        this.hackaton.setMentori(hackatonModificato.getMentori());
    }

    /**
     * Cerca un <code>hackaton</code> in base al suo nome
     *
     * @param nome Il nome per cui cercare l'<code>hackaton</code>
     * @return L'<code>hackaton</code> se trovato, null altrimenti
     */
    public Hackaton cercaHackaton(String nome) {
        return this.getHackatonRepository().cercaPerNome(nome);
    }

    /**
     * Questo metodo si occupa di confermare l'operazione di annullamento di hackaton
     * impostando il suo stato di annullato a true e aggiornandolo all'interno del database.
     *
     * @return l'<code>Hackaton</code> annullato
     */
    public Hackaton confermaAnnullamento() {
        this.hackaton.setAnnullato(true);
        this.hackatonRepository.aggiornaHackaton(this.hackaton);
        this.inviaNotificaHackatonAnnullato();
        return this.hackaton;
    }

    /**
     * Invia una notifica al destinatario dell'invito
     */
    private void inviaNotificaHackatonAnnullato() {
        Hackaton hackaton = this.getHackaton();
        NotificaFactory factory = new NotificaAnnullamentoHackatonFactory(hackaton);
        List<Utente> membriIscritti = hackaton.getIscritti().stream()
                .map(Team::getMembri)
                .reduce((l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                }).orElse(new ArrayList<>());

        List<Utente> utentiDaNotificare = new ArrayList<>();
        utentiDaNotificare.add(hackaton.getOrganizzatore());
        utentiDaNotificare.add(hackaton.getGiudice());
        utentiDaNotificare.addAll(hackaton.getMentori());
        utentiDaNotificare.addAll(membriIscritti);

        GestoreNotifiche gestoreNotifiche = new GestoreNotifiche();
        Utente organizzatore = hackaton.getOrganizzatore();
        for (Utente membro : utentiDaNotificare) {
            Notifica notifica = factory.getNotifica(organizzatore, membro);
            notifica.ottieniMessaggioPerUtente();
            gestoreNotifiche.inviaNotifica(notifica);
        }
    }
}