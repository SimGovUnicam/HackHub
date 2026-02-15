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

package it.unicam.ids.rcs.model.notifica;

import it.unicam.ids.rcs.model.HackatonTest;
import it.unicam.ids.rcs.model.Team;
import it.unicam.ids.rcs.model.Utente;
import it.unicam.ids.rcs.model.invito.InvitoAdesioneTeam;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificaInvitoTest {
    private Utente mittente;
    private Utente invitato;
    private Team team;
    private InvitoAdesioneTeam invito;
    private NotificaInvito notifica;

    @BeforeEach
    void setup() {

        team = HackatonTest.creaTeam(3);
        this.mittente = team.getMembri().get(1);
        this.invitato = HackatonTest.creaUtenteRandom();
        invito = new InvitoAdesioneTeam(mittente, invitato);
        notifica = new NotificaInvito(mittente, invitato, invito);

    }

    @Test
    void testMessaggioPerUtente() throws Exception {
        String messaggio = notifica.ottieniMessaggioPerUtente();
        assertNotNull(notifica.getMessaggio());
        assertNotNull(messaggio);
        assertEquals(notifica.getMessaggio(), messaggio);
    }

    @Test
    void testMessaggioPerMembroDelloStaffNonSupportato() {
        assertThrows(ExecutionControl.NotImplementedException.class, () -> notifica.ottieniMessaggioPerMembroDelloStaff());
    }

    @Test
    void testMessaggioPerMembroDelTeamNonSupportato() {
        assertThrows(ExecutionControl.NotImplementedException.class, () -> notifica.ottieniMessaggioPerMembroDelTeam());
    }

    @Test
    void testMessaggioPerOrganizzatoreNonSupportato() {
        assertThrows(ExecutionControl.NotImplementedException.class, () -> notifica.ottieniMessaggioPerOrganizzatore());
    }

    @Test
    void testMessaggioPerGiudiceNonSupportato() {
        assertThrows(ExecutionControl.NotImplementedException.class, () -> notifica.ottieniMessaggioPerGiudice());
    }

    @Test
    void testMessaggioPerMentoreNonSupportato() {
        assertThrows(ExecutionControl.NotImplementedException.class, () -> notifica.ottieniMessaggioPerMentore());
    }
}