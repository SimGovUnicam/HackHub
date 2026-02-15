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

package it.unicam.ids.rcs.model.invito;

import it.unicam.ids.rcs.model.HackatonTest;
import it.unicam.ids.rcs.model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

public class InvitoAdesioneTeamTest {
    private Utente mittente;
    private Utente invitato;
    private Invito invito;

    public Utente getMittente() {
        return this.mittente;
    }

    public Utente getInvitato() {
        return this.invitato;
    }

    public Invito getInvito() {
        return this.invito;
    }

    @BeforeEach
    public void initInvito() {
        var team = HackatonTest.creaTeam(4);
        this.mittente = team.getMembri().getFirst();
        this.invitato = HackatonTest.creaUtenteRandom();
        this.invito = new InvitoAdesioneTeam(mittente, invitato);
    }

    @Test
    void accettaAggiungeInvitatoAiMembriDelTeam() {
        var team = this.getMittente().getTeam();
        var dimensioneTeam = team.getMembri().size();
        assumeFalse(team.getMembri().contains(this.getInvitato()));
        this.getInvito().accetta();
        assertTrue(team.getMembri().contains(this.getInvitato()));
        assertEquals(dimensioneTeam + 1, team.getMembri().size());
    }

    @Test
    void declinaNonAggiungeInvitatoAiMembriDelTeam() {
        var team = this.getMittente().getTeam();
        var dimensioneTeam = team.getMembri().size();
        assumeFalse(team.getMembri().contains(this.getInvitato()));
        this.getInvito().declina();
        assertFalse(team.getMembri().contains(this.getInvitato()));
        assertEquals(dimensioneTeam, team.getMembri().size());
    }

    @Test
    void messaggioPerOrganizzatoreNonPrevisto() {
        assertThrows(UnsupportedOperationException.class, this.getInvito()::ottieniMessaggioPerOrganizzatore);
    }

    @Test
    void messaggioPerGiudiceNonPrevisto() {
        assertThrows(UnsupportedOperationException.class, this.getInvito()::ottieniMessaggioPerGiudice);
    }

    @Test
    void messaggioPerMentoreNonPrevisto() {
        assertThrows(UnsupportedOperationException.class, this.getInvito()::ottieniMessaggioPerMentore);
    }

    @Test
    void messaggioPerMembroDelloStaffNonPrevisto() {
        assertThrows(UnsupportedOperationException.class, this.getInvito()::ottieniMessaggioPerMembroDelloStaff);
    }

    @Test
    void messaggioPerMembroDelTeamNonPrevisto() {
        assertThrows(UnsupportedOperationException.class, this.getInvito()::ottieniMessaggioPerMembroDelTeam);
    }

    @Test
    void messaggioPerUtenteValorizzato() {
        try {
            assertNotNull(this.getInvito().ottieniMessaggioPerUtente());
        } catch (UnsupportedOperationException exception) {
            fail(exception);
        }
    }
}
