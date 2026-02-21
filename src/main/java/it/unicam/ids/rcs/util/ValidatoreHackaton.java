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

import it.unicam.ids.rcs.controller.HackatonController;
import it.unicam.ids.rcs.controller.UtenteController;
import it.unicam.ids.rcs.model.Hackaton;
import it.unicam.ids.rcs.model.Utente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe ha la responsabilità di effettuare la validazione dei dati di
 * un hackaton
 */
public class ValidatoreHackaton {
    public static final int PERIODO_MINIMO_SCADENZA_ISCRIZIONI = 7;

    private Hackaton hackatonDaValidare;
    private HackatonController controllerHackaton;
    private UtenteController controllerUtente;
    private List<String> erroriDiValidazione;

    public ValidatoreHackaton(Hackaton hackatonDaValidare, HackatonController controllerHackaton, UtenteController controllerUtente) {
        this.hackatonDaValidare = hackatonDaValidare;
        this.controllerHackaton = controllerHackaton;
        this.controllerUtente = controllerUtente;
        this.resetErroriDiValidazione();
    }

    public Hackaton getHackatonDaValidare() {
        return this.hackatonDaValidare;
    }

    public void setHackatonDaValidare(Hackaton hackatonDaValidare) {
        this.hackatonDaValidare = hackatonDaValidare;
    }

    private HackatonController getControllerHackaton() {
        return this.controllerHackaton;
    }

    private void setControllerHackaton(HackatonController controllerHackaton) {
        this.controllerHackaton = controllerHackaton;
    }

    private UtenteController getControllerUtente() {
        return this.controllerUtente;
    }

    private void setControllerUtente(UtenteController controllerUtente) {
        this.controllerUtente = controllerUtente;
    }

    public List<String> getErroriDiValidazione() {
        return this.erroriDiValidazione;
    }

    public void resetErroriDiValidazione() {
        this.erroriDiValidazione = new ArrayList<>();
    }

    private void aggiungiErroreDiValidazione(String errore) {
        this.getErroriDiValidazione().add(errore);
    }

    public String getErroriFormattati() {
        StringBuilder sb = new StringBuilder();
        for (String errore : this.getErroriDiValidazione()) {
            sb.append(errore).append("\n");
        }
        return sb.toString();
    }

    /**
     * Determina se l'hackaton risulta valido per poter essere aggiunto al sistema
     *
     * @return <code>True</code> se l'hackaton è valido per l'operazione di aggiunta,
     * <code>false</code> altrimenti
     */
    public boolean validaNuovoHackaton() {
        this.resetErroriDiValidazione();
        Hackaton hackaton = this.getHackatonDaValidare();
        LocalDate oggi = LocalDate.now();
        boolean scadenzaIscrizioniValida = hackaton.getScadenzaIscrizioni().isAfter(oggi.plusDays(PERIODO_MINIMO_SCADENZA_ISCRIZIONI));
        if (!scadenzaIscrizioniValida) {
            String errore = "La data di scadenza iscrizioni deve essere tra almeno " + PERIODO_MINIMO_SCADENZA_ISCRIZIONI + " giorni";
            this.aggiungiErroreDiValidazione(errore);
        }
        return this.validaInfoOrganizzativeDiBase() &&
                scadenzaIscrizioniValida &&
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
        boolean dimensioneMassimaValida = hackaton.getDimensioneMassimaTeam() > 0;
        if (!dimensioneMassimaValida) {
            this.aggiungiErroreDiValidazione("La dimensione massima dei team deve essere positiva");
        }

        boolean scadenzaIscrizioniPrimaDiInizio = scadenzaIscrizioni.plusDays(1).atStartOfDay().isBefore(hackaton.getInizio());
        if (!scadenzaIscrizioniPrimaDiInizio) {
            this.aggiungiErroreDiValidazione("La data di scadenza iscrizioni deve essere precedente alla data di inizio");
        }

        boolean dataInizioPrimaDiFine = hackaton.getInizio().isBefore(hackaton.getFine());
        if (!dataInizioPrimaDiFine) {
            this.aggiungiErroreDiValidazione("La data di inizio deve essere precedente alla data di fine");
        }

        boolean premioNonNegativo = hackaton.getPremio() >= 0.0;
        if (!premioNonNegativo) {
            this.aggiungiErroreDiValidazione("Il premio non può essere un numero negativo");
        }
        return dimensioneMassimaValida &&
                scadenzaIscrizioniPrimaDiInizio &&
                dataInizioPrimaDiFine &&
                premioNonNegativo;
    }

    /**
     * Determina se il nome dell'hackaton è valido, ovvero se non esistono altri
     * hackaton con lo stesso nome già registrati nel sistema
     *
     * @return <code>True</code> se il nome dell'hackaton è valido
     * <code>false</code> altrimenti
     */
    private boolean validaNomeHackaton() {
        String nomeHackaton = this.getHackatonDaValidare().getNome();
        boolean nomeHackatonValido = nomeHackaton != null && !nomeHackaton.isBlank() &&
                this.getControllerHackaton().cercaHackaton(nomeHackaton) == null;
        if (!nomeHackatonValido) {
            this.aggiungiErroreDiValidazione("Il nome dell'hackaton è già in uso");
        }
        return nomeHackatonValido;
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
        this.resetErroriDiValidazione();
        Hackaton hackatonModificato = this.getHackatonDaValidare();
        boolean scadenzaIscrizioniValida = this.validaScadenzaIscrizioniModificata(
                hackatonOriginale.getScadenzaIscrizioni(),
                hackatonModificato.getScadenzaIscrizioni()
        );
        boolean dimensioneMassimaValida = hackatonModificato.getDimensioneMassimaTeam() >= hackatonOriginale.getDimensioneMassimaTeam();
        boolean nomeValido = hackatonOriginale.getNome().equals(hackatonModificato.getNome()) ||
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
        UtenteController utenteController = this.getControllerUtente();
        boolean utentiValidi = true;
        for (Utente mentore : hackaton.getMentori()) {
            if (utenteController.cercaUtente(mentore.getEmail()) == null ||
                    hackaton.isOrganizzatore(mentore) ||
                    hackaton.isGiudice(mentore) ||
                    hackaton.isMembroDelTeamIscritto(mentore)
            ) {
                this.aggiungiErroreDiValidazione("Errore durante la validazione del mentore: " + mentore);
                utentiValidi = false;
            }
        }

        Utente giudice = hackaton.getGiudice();
        if (utenteController.cercaUtente(giudice.getEmail()) == null ||
                hackaton.isOrganizzatore(giudice) ||
                hackaton.isMentore(giudice) ||
                hackaton.isMembroDelTeamIscritto(giudice)
        ) {
            this.aggiungiErroreDiValidazione("Errore durante la validazione del giudice: " + giudice);
            utentiValidi = false;
        }

        return utentiValidi;
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
    private boolean validaScadenzaIscrizioniModificata(LocalDate scadenzaIscrizioniOriginale,
                                                       LocalDate scadenzaIscrizioniModificata
    ) {
        LocalDate oggi = LocalDate.now();
        boolean posticipata = scadenzaIscrizioniModificata.isAfter(scadenzaIscrizioniOriginale);
        boolean almeno7GiorniDaOggi = scadenzaIscrizioniModificata.isAfter(oggi.plusDays(7));
        return posticipata || almeno7GiorniDaOggi;
    }
}