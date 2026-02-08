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
import it.unicam.ids.rcs.model.Notifica;
import it.unicam.ids.rcs.model.Utente;
import it.unicam.ids.rcs.repository.HackatonRepository;
import it.unicam.ids.rcs.util.GestoreNotifiche;
import it.unicam.ids.rcs.util.NotificaModificaHackatonFactory;
import it.unicam.ids.rcs.util.ValidatoreHackaton;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe si occupa della gestione delle operazioni che riguardano gli hackaton
 */
public class HackatonController {
    private Hackaton hackaton;
    private HackatonRepository hackatonRepository;
    private UtenteController utenteController;

    public HackatonController() {
        this.hackatonRepository = new HackatonRepository();
        this.utenteController = new UtenteController();
    }

    public Hackaton getHackaton() {
        return this.hackaton;
    }

    private void setHackaton(Hackaton hackaton) {
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
     * @return <code>True</code> se l'operazione termina con successo, <code>false</code> altrimenti
     */
    public boolean creaHackaton(int dimensioneMassimaTeam, String regolamento, LocalDate scadenzaIscrizioni,
                                LocalDateTime inizio, LocalDateTime fine, String luogo, Double premio) {
        Hackaton hackaton = new Hackaton();
        hackaton.setDimensioneMassimaTeam(dimensioneMassimaTeam);
        hackaton.setRegolamento(regolamento);
        hackaton.setScadenzaIscrizioni(scadenzaIscrizioni);
        hackaton.setInizio(inizio);
        hackaton.setFine(fine);
        hackaton.setLuogo(luogo);
        hackaton.setPremio(premio);

        hackaton.setOrganizzatore(UtenteController.getUtenteInSessione());

        this.setHackaton(hackaton);
        return true;
    }

    /**
     * Assegna l'utente indicato attraverso l'e-mail come giudice dell'hackaton
     *
     * @param email L'e-mail dell'utente da designare come giudice
     * @return <code>True</code> se l'operazione termina con successo, <code>false</code> altrimenti
     * (e.g. Non esiste un utente con l'e-mail fornita)
     */
    public boolean assegnaGiudice(String email) {
        if (email == null || email.isEmpty()) {
            System.out.println("AssegnaGiudice - Nessuna e-mail fornita"); // TODO rimuovere dopo porting su SpringBoot
            return false;
        }

        Utente giudice = this.getUtenteController().cercaUtente(email);
        if (giudice == null) {
            System.out.println("AssegnaGiudice - Utente non trovato con email: " + email); // TODO rimuovere dopo porting su SpringBoot
            return false;
        }

        if (this.getHackaton().isMembroDelloStaff(giudice)) {
            // Il giudice è già designato come membro dello staff (i.e. Organizzatore, mentore)
            System.out.println("AssegnaGiudice - L'utente è già un membro dello staff: " + email); // TODO rimuovere dopo porting su SpringBoot
            return false;
        }

        this.getHackaton().setGiudice(giudice);
        return true;
    }

    /**
     * Assegna gli utenti indicato attraverso le e-mail come mentori dell'hackaton
     *
     * @param emails Le e-mail degli utenti da designare come mentori
     * @return <code>True</code> se l'operazione termina con successo, <code>false</code> altrimenti
     * (e.g. Non esiste un utente con l'e-mail fornita)
     */
    public boolean aggiungiMentori(List<String> emails) {
        if (emails.isEmpty()) {
            System.out.println("AssegnaMentore - Nessuna e-mail fornita"); // TODO rimuovere dopo porting su SpringBoot
            return false;
        }

        for (String email : emails) {
            Utente mentore = this.getUtenteController().cercaUtente(email);
            if (mentore == null) {
                System.out.println("AssegnaMentore - Utente non trovato con email: " + email); // TODO rimuovere dopo porting su SpringBoot
                return false;
            }

            if (this.getHackaton().isMembroDelloStaff(mentore)) {
                // L'utente è già membro dello staff (i.e. Organizzatore, giudice, mentore già aggiunto)
                System.out.println("AssegnaMentore - L'utente è già un membro dello staff: " + email); // TODO rimuovere dopo porting su SpringBoot
                return false;
            }

            this.getHackaton().aggiungiMentore(mentore);
        }

        return true;
    }

    /**
     * Registra l'hackaton nel sistema indicato l'utente che lo ha creato come
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
        var messaggio = "Nuovo hackaton creato: " + hackaton;

        gestoreNotifiche.inviaNotifica(messaggio, hackaton.getOrganizzatore());
        gestoreNotifiche.inviaNotifica(messaggio, hackaton.getGiudice());
        gestoreNotifiche.inviaNotifica(messaggio, hackaton.getMentori().getFirst());
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
        // return this.hackatonRepository.getHackatonsConIscrizioniAperte(organizzatoreHackaton);
        return null;
    }

    /**
     * Dato il nome di un hackaton, lo seleziona per poter essere modificato
     * @param nomeHackaton Il nome dell'hackaton da selezionare
     * @return L'hackaton trovato
     */
    public Hackaton selezionaHackaton(String nomeHackaton) {
        Hackaton hackatonOriginale = this.hackatonRepository.cercaPerNome(nomeHackaton);
        this.setHackaton(hackatonOriginale);
        return hackatonOriginale;
    }

    /**
     *
     * @param nome
     * @param dimensioneMassimaTeam
     * @param regolamento
     * @param scadenzaIscrizioni
     * @param inizio
     * @param fine
     * @param luogo
     * @param premio
     * @param emailGiudice
     * @param emailMentori
     * @return
     */
    public Hackaton confermaModifica(String nome, int dimensioneMassimaTeam, String regolamento, LocalDate scadenzaIscrizioni, LocalDateTime inizio,
                                     LocalDateTime fine, String luogo, double premio, String emailGiudice, List<String> emailMentori) {
        // TODO prendo all'inizio i mentori originali perchè successivamente mi serviranno per le notifiche
        List<Utente> mentoriHackatonOriginale = this.hackaton.getMentori();
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

        NotificaModificaHackatonFactory notificaFactory = new NotificaModificaHackatonFactory(this.hackaton);
        Notifica notificaPerGiudice = notificaFactory.getNotifica(this.hackaton.getOrganizzatore(), this.hackaton.getGiudice());
        String messaggioPerGiudice = notificaPerGiudice.ottieniMessaggioPerGiudice();

        // TODO inizializzazione gestoreNotifiche e invioNotifica() per il giudice

        List<Utente> mentoriDaNotificare = this.ottieniMentoriDaNotificare(mentoriHackatonOriginale, mentori);
        for (Utente mentore : mentoriDaNotificare) {
            Notifica notificaPerMentore = notificaFactory.getNotifica(this.hackaton.getOrganizzatore(), mentore);
            String messaggioPerMentore = notificaPerMentore.ottieniMessaggioPerMentore();
            // TODO invia notifica per mentore
        }
        return this.hackaton;
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
     *
     * @param hackatonModificato
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
     * @param nome Il nome per cui cercare l'<code>hackaton</code>
     * @return L'<code>hackaton</code> se trovato, null altrimenti
     */
    public Hackaton cercaHackaton(String nome) {
        return this.getHackatonRepository().cercaPerNome(nome);
    }
}
