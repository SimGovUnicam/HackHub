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

package it.unicam.ids.rcs.util;

import it.unicam.ids.rcs.controller.UtenteController;
import it.unicam.ids.rcs.model.Hackaton;
import it.unicam.ids.rcs.model.Utente;
import it.unicam.ids.rcs.repository.HackatonRepository;

import java.time.LocalDate;

/**
 * Questa classe ha la responsabilità di effettuare la validazione dei dati di
 * un hackaton
 */
public class ValidatoreHackaton {
    private Hackaton hackatonDaValidare;

    public ValidatoreHackaton(Hackaton hackatonDaValidare) {
        this.hackatonDaValidare = hackatonDaValidare;
    }

    public Hackaton getHackatonDaValidare() {
        return this.hackatonDaValidare;
    }

    public void setHackatonDaValidare(Hackaton hackatonDaValidare) {
        this.hackatonDaValidare = hackatonDaValidare;
    }

    /**
     * Determina se l'hackaton risulta valido per poter essere aggiunto al sistema
     *
     * @return <code>True</code> se l'hackaton è valido per l'operazione di aggiunta,
     * <code>false</code> altrimenti
     */
    public boolean validaNuovoHackaton() {
        Hackaton hackaton = this.getHackatonDaValidare();
        LocalDate oggi = LocalDate.now();
        return this.validaInfoOrganizzativeDiBase() &&
                hackaton.getScadenzaIscrizioni().isAfter(oggi.plusDays(7)) &&
                this.validaNomeHackaton() &&
                this.validaUtentiHackaton();
    }

    /**
     * Determina se le informazioni organizzative di base dell'hackaton sono valide
     *
     * @return <code>True</code> se le informazioni organizzative di base sono valide,
     * <code>false</code> altrimenti
     */
    private boolean validaInfoOrganizzativeDiBase() {
        Hackaton hackaton = this.getHackatonDaValidare();
        LocalDate scadenzaIscrizioni = hackaton.getScadenzaIscrizioni();
        return hackaton.getDimensioneMassimaTeam() > 0 &&
                hackaton.getInizio().isAfter(scadenzaIscrizioni.atStartOfDay()) &&
                hackaton.getInizio().isBefore(hackaton.getFine()) &&
                hackaton.getPremio() >= 0.0;
    }

    /**
     * Determina se il nome dell'hackaton è valido, ovvero se non esistono altri
     * hackaton con lo stesso nome già registrati nel sistema
     *
     * @return <code>True</code> se il nome dell'hackaton è valido
     * <code>false</code> altrimenti
     */
    private boolean validaNomeHackaton() {
        HackatonRepository hackatonRepository = new HackatonRepository();
        String nomeHackaton = this.getHackatonDaValidare().getNome();
        return hackatonRepository.cercaPerNome(nomeHackaton) == null;
    }

    /**
     * Determina se l'hackaton risulta valido per poter essere modificato. L'hackaton
     * da validare viene controllato anche rispetto a quello originale, fornito
     * come parametro
     *
     * @param hackatonOriginale L'hackaton originale con i dati precedenti
     *                          alla modifica
     * @return <code>True</code> se l'hackaton è valido per l'operazione di modifica,
     * <code>false</code> altrimenti
     */
    public boolean validaHackatonModificato(Hackaton hackatonOriginale) {
        Hackaton hackatonModificato = this.getHackatonDaValidare();
        boolean scadenzaIscrizioniValida = this.validaScadenzaIscrizioniModificata(
                hackatonOriginale.getScadenzaIscrizioni(),
                hackatonModificato.getScadenzaIscrizioni()
        );
        boolean dimensioneMassimaValida = hackatonModificato.getDimensioneMassimaTeam() < hackatonOriginale.getDimensioneMassimaTeam();
        boolean nomeValido = !hackatonOriginale.getNome().equals(hackatonModificato.getNome()) &&
                this.validaNomeHackaton();

        return this.validaInfoOrganizzativeDiBase() &&
                scadenzaIscrizioniValida &&
                dimensioneMassimaValida &&
                nomeValido &&
                this.validaUtentiHackaton();
    }

    /**
     * Determina se gli utenti relativi all'hackaton sono validi, ovvero se:
     * - Il giudice è un utente registrato, non è designato come organizzatore
     * o come mentore, non è membro di un team iscritto all'hackaton
     * - Ogni mentore è un utente registrato, non è designato come organizzatore
     * o come giudice, non è membro di un team iscritto all'hackaton
     *
     * @return <code>True</code> se tutti gli utenti sono validi,
     * <code>false</code> altrimenti
     */
    private boolean validaUtentiHackaton() {
        Hackaton hackaton = this.getHackatonDaValidare();
        UtenteController utenteController = new UtenteController();
        for (Utente mentore : hackaton.getMentori()) {
            if (utenteController.cercaUtente(mentore.getEmail()) == null ||
                    hackaton.isOrganizzatore(mentore) ||
                    hackaton.isGiudice(mentore) ||
                    hackaton.isMembroDelTeamIscritto(mentore)
            ) {
                System.out.println("Errore durante la validazione del mentore: " + mentore); // TODO rimuovere dopo porting su SpringBoot
                return false;
            }
        }

        Utente giudice = hackaton.getGiudice();
        if (utenteController.cercaUtente(giudice.getEmail()) == null ||
                hackaton.isOrganizzatore(giudice) ||
                hackaton.isMentore(giudice) ||
                hackaton.isMembroDelTeamIscritto(giudice)
        ) {
            System.out.println("Errore durante la validazione del giudice: " + giudice); // TODO rimuovere dopo porting su SpringBoot
            return false;
        }

        return true;
    }

    /**
     * Determina se la data di scadenza di iscrizioni modificata è valida. La data
     * di scadenza iscrizioni è valida se:
     * - Viene posticipata
     * OPPURE
     * - Viene anticipata e la nuova data è tra almeno una settimana (7 giorni)
     *
     * @param scadenzaIscrizioniOriginale  La data di scadenza iscrizioni dell'hackaton
     *                                     prima della modifica
     * @param scadenzaIscrizioniModificata La data di scadenza iscrizioni dell'hackaton
     *                                     modificata
     * @return <code>True</code> se la data di scadenza iscrizioni è valida,
     * <code>false</code> altrimenti
     */
    private boolean validaScadenzaIscrizioniModificata(
            LocalDate scadenzaIscrizioniOriginale,
            LocalDate scadenzaIscrizioniModificata
    ) {
        LocalDate oggi = LocalDate.now();
        return scadenzaIscrizioniModificata.isAfter(scadenzaIscrizioniOriginale) ||
                scadenzaIscrizioniModificata.isAfter(oggi.plusDays(7));
    }
}
