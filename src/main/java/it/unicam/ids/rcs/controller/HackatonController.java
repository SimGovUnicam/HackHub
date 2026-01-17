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
import it.unicam.ids.rcs.repository.HackatonRepository;
import it.unicam.ids.rcs.util.GestoreNotifiche;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        if (!Hackaton.validaInfo(dimensioneMassimaTeam, scadenzaIscrizioni, inizio, fine, premio))
            return false;

        var hackaton = new Hackaton();
        hackaton.setDimensioneMassimaTeam(dimensioneMassimaTeam);
        hackaton.setRegolamento(regolamento);
        hackaton.setScadenzaIscrizioni(scadenzaIscrizioni);
        hackaton.setInizio(inizio);
        hackaton.setFine(fine);
        hackaton.setLuogo(luogo);
        hackaton.setPremio(premio);

        this.setHackaton(hackaton);
        return true;
    }

    /**
     * Assegna l'utente indicato attraverso l'e-mail come giudice dell'hackaton
     * @param email L'e-mail dell'utente da designare come giudice
     * @return <code>True</code> se l'operazione termina con successo, <code>false</code> altrimenti
     * (e.g. Non esiste un utente con l'e-mail fornita)
     */
    public boolean assegnaGiudice(String email) {
        var giudice = this.getUtenteController().cercaUtente(email);
        if (giudice == null) {
            return false;
        }
        this.getHackaton().setGiudice(giudice);
        return true;
    }

    /**
     * Assegna l'utente indicato attraverso l'e-mail come mentore dell'hackaton
     * @param email L'e-mail dell'utente da designare come mentore
     * @return <code>True</code> se l'operazione termina con successo, <code>false</code> altrimenti
     * (e.g. Non esiste un utente con l'e-mail fornita)
     */
    public boolean aggiungiMentore(String email) {
        var mentore = this.getUtenteController().cercaUtente(email);
        if (mentore == null) {
            return false;
        }
        this.getHackaton().aggiungiMentore(mentore);
        return true;
    }

    /**
     * Registra l'hackaton nel sistema indicato l'utente che lo ha creato come
     * organizzatore dell'hackaton stesso
     * @param emailCreatore L'e-mail dell'utente che ha creato l'hackaton
     * @return L'istanza di <code>Hackaton</code> aggiunto in caso di successo,
     * <code>null</code> altrimenti
     */
    public Hackaton registraHackaton(String emailCreatore) {
        var organizzatore = this.getUtenteController().cercaUtente(emailCreatore);
        if (organizzatore == null) {
            // TODO valutare lancio eccezione
            return null;
        }
        var hackaton = this.getHackaton();
        hackaton.setOrganizzatore(organizzatore);
        this.getHackatonRepository().registraHackaton(hackaton);

        this.notificaHackatonCreato(hackaton);

        return hackaton;
    }

    /**
     * Notifica lo staff di un hackaton appena creato della creazione dell'hackaton
     * @param hackaton L'hackaton creato
     */
    private void notificaHackatonCreato(Hackaton hackaton) {
        var gestoreNotifiche = new GestoreNotifiche();
        var messaggio = "Nuovo hackaton creato: " + hackaton;

        gestoreNotifiche.inviaNotifica(messaggio, hackaton.getOrganizzatore());
        gestoreNotifiche.inviaNotifica(messaggio, hackaton.getGiudice());
        gestoreNotifiche.inviaNotifica(messaggio, hackaton.getMentori().getFirst());
    }
}
