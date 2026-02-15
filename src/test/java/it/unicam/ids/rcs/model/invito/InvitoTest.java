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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class InvitoTest {
    private Invito invito;

    public Invito getInvito() {
        return this.invito;
    }

    @BeforeEach
    public void initInvito() {
        var mittente = HackatonTest.creaUtenteRandom();
        var invitato = HackatonTest.creaUtenteRandom();
        this.invito = new InvitoGenerico(mittente, invitato);
    }

    @Test
    void invitoHaStatoInizialeInAttesa() {
        assertEquals(StatoInvito.IN_ATTESA, this.getInvito().getStato());
    }

    @Test
    void invitoHaStatoAccettatoDopoAccettazione() {
        Invito invito = this.getInvito();
        assumeTrue(invito.getStato().equals(StatoInvito.IN_ATTESA));
        invito.accetta();
        assertEquals(StatoInvito.ACCETTATO, invito.getStato());
    }

    @Test
    void invitoHaStatoDeclinatoDopoDeclinazione() {
        Invito invito = this.getInvito();
        assumeTrue(invito.getStato().equals(StatoInvito.IN_ATTESA));
        invito.accetta();
        assertEquals(StatoInvito.ACCETTATO, invito.getStato());
    }

    @Test
    void invitoAccettatoNonPuoEssereAccettato() {
        Invito invito = this.getInvito();
        assumeTrue(invito.getStato().equals(StatoInvito.IN_ATTESA));
        invito.accetta();
        assertEquals(StatoInvito.ACCETTATO, invito.getStato());
        assertThrows(IllegalStateException.class, invito::accetta);
    }

    @Test
    void invitoAccettatoNonPuoEssereDeclinato() {
        Invito invito = this.getInvito();
        assumeTrue(invito.getStato().equals(StatoInvito.IN_ATTESA));
        invito.accetta();
        assertEquals(StatoInvito.ACCETTATO, invito.getStato());
        assertThrows(IllegalStateException.class, invito::declina);
    }

    @Test
    void invitoDeclinatoNonPuoEssereAccettato() {
        Invito invito = this.getInvito();
        assumeTrue(invito.getStato().equals(StatoInvito.IN_ATTESA));
        invito.declina();
        assertEquals(StatoInvito.DECLINATO, invito.getStato());
        assertThrows(IllegalStateException.class, invito::accetta);
    }

    @Test
    void invitoDeclinatoNonPuoEssereDeclinato() {
        Invito invito = this.getInvito();
        assumeTrue(invito.getStato().equals(StatoInvito.IN_ATTESA));
        invito.declina();
        assertEquals(StatoInvito.DECLINATO, invito.getStato());
        assertThrows(IllegalStateException.class, invito::declina);
    }

    private static class InvitoGenerico extends Invito {
        public InvitoGenerico(Utente mittente, Utente invitato) {
            super(mittente, invitato);
        }

        @Override
        protected void elaboraAccettazione() {

        }

        @Override
        protected void elaboraDeclinazione() {

        }

        @Override
        public String ottieniMessaggioPerOrganizzatore() throws UnsupportedOperationException {
            return null;
        }

        @Override
        public String ottieniMessaggioPerGiudice() throws UnsupportedOperationException {
            return null;
        }

        @Override
        public String ottieniMessaggioPerMentore() throws UnsupportedOperationException {
            return null;
        }

        @Override
        public String ottieniMessaggioPerMembroDelloStaff() throws UnsupportedOperationException {
            return null;
        }

        @Override
        public String ottieniMessaggioPerMembroDelTeam() throws UnsupportedOperationException {
            return null;
        }

        @Override
        public String ottieniMessaggioPerUtente() throws UnsupportedOperationException {
            return null;
        }
    }
}
