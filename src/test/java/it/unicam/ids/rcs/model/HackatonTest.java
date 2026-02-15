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

package it.unicam.ids.rcs.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

public class HackatonTest {

    public static Hackaton creaHackatonBase() {
        Hackaton hackaton = new Hackaton();
        hackaton.setNome("Basic Hackaton");
        hackaton.setDimensioneMassimaTeam(3);
        hackaton.setScadenzaIscrizioni(LocalDate.of(2026, 10, 7));
        hackaton.setInizio(LocalDateTime.of(2026, 10, 21, 10, 0));
        hackaton.setFine(LocalDateTime.of(2026, 10, 24, 15, 30));
        hackaton.setLuogo("Casa di Pippo");
        hackaton.setPremio(30.50);
        hackaton.setOrganizzatore(creaOrganizzatore());
        hackaton.setGiudice(creaGiudice());
        hackaton.setMentori(creaMentori(3));
        hackaton.setIscritti(creaTeams(4, hackaton.getDimensioneMassimaTeam()));
        return hackaton;
    }

    public static Utente creaOrganizzatore() {
        Utente organizzatore = new Utente();
        organizzatore.setNome("John");
        organizzatore.setCognome("Doe");
        organizzatore.setEmail("John.Doe@email.com");
        organizzatore.setAccessoEffettuato(true);
        return organizzatore;
    }

    public static Utente creaGiudice() {
        Utente giudice = new Utente();
        giudice.setNome("Joe");
        giudice.setCognome("Smith");
        giudice.setEmail("Joe.Smith@email.com");
        giudice.setAccessoEffettuato(false);
        return giudice;
    }

    public static List<Utente> creaMentori(int numero) {
        List<Utente> mentori = new ArrayList<>();
        while (numero-- > 0) {
            mentori.add(creaUtenteRandom());
        }
        return mentori;
    }

    public static Utente creaUtenteRandom() {
        var uuid = UUID.randomUUID().toString();
        Utente utente = new Utente();
        utente.setNome("Utente_Nome_" + uuid);
        utente.setCognome("Utente_Cognome" + uuid);
        utente.setEmail("email_" + uuid + "@email.com");
        utente.setAccessoEffettuato(false);
        return utente;
    }

    public static List<Team> creaTeams(int numero, int dimensioneMassima) {
        List<Team> teams = new ArrayList<>();
        while (numero-- > 0) {
            teams.add(creaTeam(dimensioneMassima));
        }
        return teams;
    }

    public static Team creaTeam(int dimensioneMassima) {
        int dimensione = ThreadLocalRandom.current().nextInt(2, dimensioneMassima);
        Team team = new Team();
        while (dimensione-- > 0) {
            team.aggiungiMembro(creaUtenteRandom());
        }
        return team;
    }

    @Test
    public void impossibileAggiungereMentoreDuplicato() {
        Hackaton hackaton = new Hackaton();
        assertEquals(0, hackaton.getMentori().size());

        Utente mentore1 = creaUtenteRandom();
        hackaton.aggiungiMentore(mentore1);
        assertEquals(1, hackaton.getMentori().size());

        Utente mentore2 = creaUtenteRandom();
        hackaton.aggiungiMentore(mentore2);
        assertEquals(2, hackaton.getMentori().size());

        hackaton.aggiungiMentore(mentore2);
        assertEquals(2, hackaton.getMentori().size());
    }

    @Test
    public void utenteRisultaOrganizzatoreEMembroDelloStaff() {
        Hackaton hackaton = new Hackaton();
        assertNull(hackaton.getOrganizzatore());

        Utente organizzatore = creaUtenteRandom();
        assertFalse(hackaton.isOrganizzatore(organizzatore));
        assertFalse(hackaton.isMembroDelloStaff(organizzatore));

        hackaton.setOrganizzatore(organizzatore);
        assertTrue(hackaton.isOrganizzatore(organizzatore));
        assertTrue(hackaton.isMembroDelloStaff(organizzatore));

        Utente altroUtente = creaUtenteRandom();
        assertFalse(hackaton.isOrganizzatore(altroUtente));
    }

    @Test
    public void utenteRisultaGiudiceEMembroDelloStaff() {
        Hackaton hackaton = new Hackaton();
        assertNull(hackaton.getGiudice());

        Utente giudice = creaUtenteRandom();
        assertFalse(hackaton.isGiudice(giudice));
        assertFalse(hackaton.isMembroDelloStaff(giudice));

        hackaton.setGiudice(giudice);
        assertTrue(hackaton.isGiudice(giudice));
        assertTrue(hackaton.isMembroDelloStaff(giudice));

        Utente altroUtente = creaUtenteRandom();
        assertFalse(hackaton.isGiudice(altroUtente));
    }

    @Test
    public void utenteRisultaMentoreEMembroDelloStaff() {
        Hackaton hackaton = new Hackaton();
        assertTrue(hackaton.getMentori().isEmpty());

        Utente mentore1 = creaUtenteRandom();
        assertFalse(hackaton.isMentore(mentore1));
        assertFalse(hackaton.isMembroDelloStaff(mentore1));

        hackaton.aggiungiMentore(mentore1);
        assertTrue(hackaton.isMentore(mentore1));
        assertTrue(hackaton.isMembroDelloStaff(mentore1));

        Utente altroUtente = creaUtenteRandom();
        assertFalse(hackaton.isMentore(altroUtente));

        Utente mentore2 = creaUtenteRandom();
        hackaton.aggiungiMentore(mentore2);
        hackaton.aggiungiMentore(mentore2);
        assertTrue(hackaton.isMentore(mentore2));
        assertTrue(hackaton.isMembroDelloStaff(mentore2));
    }

    @Test
    public void membriDeiTeamRisultanoUtentiIscritti() {
        var teams = creaTeams(4, 12);
        var hackaton = new Hackaton();
        hackaton.setIscritti(teams);
        for(var team : teams) {
            for(var membroDelTeam : team.getMembri()) {
                assertTrue(hackaton.isMembroDelTeamIscritto(membroDelTeam));
            }
        }

        Utente altroUtente = new Utente();
        altroUtente.setEmail("altro.utente@email.com");
        assertFalse(hackaton.isMembroDelTeamIscritto(altroUtente));
    }
}
