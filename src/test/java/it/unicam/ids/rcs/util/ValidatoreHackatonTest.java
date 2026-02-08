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
import it.unicam.ids.rcs.model.HackatonTest;
import it.unicam.ids.rcs.model.Utente;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatoreHackatonTest {
    private Hackaton hackaton;
    private HackatonControllerMock hackatonController;
    private UtenteControllerMock utenteController;
    private ValidatoreHackaton validatore;

    private Hackaton getHackaton() {
        return this.hackaton;
    }

    private ValidatoreHackaton getValidatore() {
        return this.validatore;
    }

    private HackatonControllerMock getHackatonController() {
        return this.hackatonController;
    }

    private UtenteControllerMock getUtenteController() {
        return this.utenteController;
    }

    @BeforeEach
    public void init() {
        this.hackaton = HackatonTest.creaHackatonBase();
        this.initHackatonController(this.getHackaton());
        this.initUtenteController(this.getHackaton());
        this.validatore = new ValidatoreHackaton(this.getHackaton(), this.getHackatonController(), this.getUtenteController());
    }

    /**
     * Inizializza un'istanza della classe mock del controller hackaton
     *
     * @param hackaton L'hackaton con cui inizializzare la classe controller
     */
    private void initHackatonController(Hackaton hackaton) {
        this.hackatonController = new HackatonControllerMock();
        this.getHackatonController().setHackaton(hackaton);
        // Ricordarsi di registrare l'hackaton nei test in cui è necessario (e.g. Modifica, Annulla)
    }

    /**
     * Inizializza un'istanza della classe mock del controller utente
     *
     * @param hackaton L'hackaton con cui inizializzare la classe controller
     */
    private void initUtenteController(Hackaton hackaton) {
        this.utenteController = new UtenteControllerMock();
        this.getUtenteController().aggiungiUtente(hackaton.getOrganizzatore());
        this.getUtenteController().aggiungiUtente(hackaton.getGiudice());
        for (Utente mentore : hackaton.getMentori()) {
            this.getUtenteController().aggiungiUtente(mentore);
        }

        // Non registro gli iscritti perché non necessario in questo momento
        // TODO aggiunta utenti iscritti
    }

    @Test
    public void nuovoHackatonValido() {
        assertTrue(this.getValidatore().validaNuovoHackaton());
    }

    @Test
    public void nuovoHackatonDimensioneMassimaNonPositiva() {
        Assumptions.assumeTrue(this.getValidatore().validaNuovoHackaton());

        this.getHackaton().setDimensioneMassimaTeam(0);
        assertFalse(this.getValidatore().validaNuovoHackaton());
    }

    @Test
    public void nuovoHackatonDataInizioPrimaDiScadenzaIscrizioni() {
        Assumptions.assumeTrue(this.getValidatore().validaNuovoHackaton());

        LocalDateTime inizio = LocalDateTime.of(2026, 10, 12, 15, 14);
        LocalDate scadenzaIscrizioni = inizio.toLocalDate().plusDays(2); // 2 giorni dopo inizio
        hackaton.setInizio(inizio);
        hackaton.setScadenzaIscrizioni(scadenzaIscrizioni);

        assertFalse(this.validatore.validaNuovoHackaton());
    }

    @Test
    public void nuovoHackatonDataFinePrimaDiDataInizio() {
        Assumptions.assumeTrue(this.getValidatore().validaNuovoHackaton());

        LocalDateTime inizio = LocalDateTime.of(2026, 10, 12, 15, 14);
        LocalDateTime fine = inizio.minusDays(2); // 2 giorni prima di inizio
        hackaton.setInizio(inizio);
        hackaton.setFine(fine);

        assertFalse(this.getValidatore().validaNuovoHackaton());
    }

    @Test
    public void nuovoHackatonPremioNegativo() {
        Assumptions.assumeTrue(this.getValidatore().validaNuovoHackaton());

        double premio = -1.0;
        hackaton.setPremio(premio);

        assertFalse(this.getValidatore().validaNuovoHackaton());
    }

    @Test
    public void nuovoHackatonScadenzaIscrizioniPrimaDelThreshold() {
        Assumptions.assumeTrue(this.getValidatore().validaNuovoHackaton());

        LocalDate oggi = LocalDate.now();
        int giorniOffset = ThreadLocalRandom.current().nextInt(1, ValidatoreHackaton.PERIODO_MINIMO_SCADENZA_ISCRIZIONI);
        LocalDate scadenzaIscrizioni = oggi.minusDays(giorniOffset);
        // scadenzaIscrizioni è minore rispetto a oggi di un numero di giorni nell'intervallo [1, PERIODO_MINIMO_SCADENZA_ISCRIZIONI)
        this.getHackaton().setScadenzaIscrizioni(scadenzaIscrizioni);

        assertFalse(this.getValidatore().validaNuovoHackaton());
    }

    @Test
    public void nuovoHackatonNomeHackatonGiaInUso() {
        Assumptions.assumeTrue(this.getValidatore().validaNuovoHackaton());

        String nomeHackaton = this.getHackaton().getNome();
        Hackaton altroHackaton = new Hackaton();
        altroHackaton.setNome(nomeHackaton);
        this.getHackatonController().aggiungiHackaton(altroHackaton);

        assertFalse(this.getValidatore().validaNuovoHackaton());

        this.getHackaton().setNome("Altro nome");
        assertTrue(this.getValidatore().validaNuovoHackaton());
    }

    @Test
    public void nuovoHackatonGiudiceNonEsiste() {
        Assumptions.assumeTrue(this.getValidatore().validaNuovoHackaton());

        Utente giudice = new Utente();
        giudice.setEmail("emailUtenteNonRegistrato@email.com");
        this.getHackaton().setGiudice(giudice);
        assertFalse(this.getValidatore().validaNuovoHackaton());

        this.getUtenteController().aggiungiUtente(giudice);
        assertTrue(this.getValidatore().validaNuovoHackaton());
    }

    @Test
    public void nuovoHackatonMentoreNonEsiste() {
        Assumptions.assumeTrue(this.getValidatore().validaNuovoHackaton());

        Utente mentore = new Utente();
        mentore.setEmail("emailUtenteNonRegistrato@email.com");
        this.getHackaton().aggiungiMentore(mentore);
        assertFalse(this.getValidatore().validaNuovoHackaton());

        this.getUtenteController().aggiungiUtente(mentore);
        assertTrue(this.getValidatore().validaNuovoHackaton());
    }

    @Test
    public void nuovoHackatonGiudiceGiaMembroDelloStaff() {
        var hackaton = this.getHackaton();
        var validatore = this.getValidatore();
        Assumptions.assumeTrue(this.getValidatore().validaNuovoHackaton());

        Utente organizzatoreOriginale = hackaton.getOrganizzatore();
        hackaton.setOrganizzatore(hackaton.getGiudice());
        assertFalse(validatore.validaNuovoHackaton());
        hackaton.setOrganizzatore(organizzatoreOriginale);

        hackaton.aggiungiMentore(hackaton.getGiudice());
        assertFalse(validatore.validaNuovoHackaton());
    }

    @Test
    public void nuovoHackatonMentoreGiaMembroDelloStaff() {
        var hackaton = this.getHackaton();
        var validatore = this.getValidatore();
        Assumptions.assumeTrue(this.getValidatore().validaNuovoHackaton());

        Utente organizzatoreOriginale = hackaton.getOrganizzatore();
        hackaton.setOrganizzatore(hackaton.getMentori().getFirst());
        assertFalse(validatore.validaNuovoHackaton());
        hackaton.setOrganizzatore(organizzatoreOriginale);

        Utente mentore = HackatonTest.creaUtenteRandom();
        this.getUtenteController().aggiungiUtente(mentore);
        hackaton.aggiungiMentore(mentore);
        assertTrue(validatore.validaNuovoHackaton());
        hackaton.aggiungiMentore(mentore); // Aggiungo di nuovo lo stesso mentore
        assertTrue(validatore.validaNuovoHackaton());
    }

    @Test
    public void hackatonModificatoScadenzaIscrizioniDopoScadenzaIscrizioniOriginale(){
        Assumptions.assumeTrue(this.getValidatore().validaNuovoHackaton());

        int giorniOffset = ThreadLocalRandom.current().nextInt(1, ValidatoreHackaton.PERIODO_MINIMO_SCADENZA_ISCRIZIONI);
        LocalDate nuovaScadenza = this.getHackaton().getScadenzaIscrizioni().plusDays(giorniOffset);
        this.getHackaton().setScadenzaIscrizioni(nuovaScadenza);

        assertTrue(this.getValidatore().validaNuovoHackaton());
    }


    @Test
    public void hackatonModificatoScadenzaIscrizioniAlmenoTraUnaSettimana(){
        //La nuova scadenza di iscrizioni ha meno di 7 giorni di margine a partire dal momento della modifica
        Assumptions.assumeTrue(this.getValidatore().validaNuovoHackaton());
        LocalDate oggi = LocalDate.now();
        int giorniOffset = ThreadLocalRandom.current().nextInt(0, ValidatoreHackaton.PERIODO_MINIMO_SCADENZA_ISCRIZIONI);
        LocalDate scadenzaIscrizioni = oggi.plusDays(giorniOffset);
        this.getHackaton().setScadenzaIscrizioni(scadenzaIscrizioni);

        assertFalse(this.getValidatore().validaNuovoHackaton());
    }
    /**
     * Mock di un controller hackaton per gestire le operazioni esclusivamente in memoria
     */
    public static class HackatonControllerMock extends HackatonController {
        private final Map<String, Hackaton> hackatonDB;

        public HackatonControllerMock() {
            this.hackatonDB = new HashMap<>();
        }

        @Override
        public void setHackaton(Hackaton hackaton) {
            super.setHackaton(hackaton);
        }

        @Override
        public Hackaton cercaHackaton(String nome) {
            return this.hackatonDB.get(nome);
        }

        /**
         * Registra l'hackaton fornito
         *
         * @param hackaton L'hackaton da registrare
         */
        public void aggiungiHackaton(Hackaton hackaton) {
            this.hackatonDB.put(hackaton.getNome(), hackaton);
        }

        @Override
        public Hackaton registraHackaton() {
            return this.hackatonDB.put(this.getHackaton().getNome(), this.getHackaton());
        }
    }

    /**
     * Mock di un controller hackaton per gestire le operazioni esclusivamente in memoria
     */
    public static class UtenteControllerMock extends UtenteController {
        private final Map<String, Utente> utenti;

        public UtenteControllerMock() {
            this.utenti = new HashMap<>();
        }

        public void aggiungiUtente(Utente utente) {
            utenti.put(utente.getEmail(), utente);
        }

        @Override
        public Utente cercaUtente(String nome) {
            return utenti.get(nome);
        }
    }
}
