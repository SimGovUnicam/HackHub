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

import it.unicam.ids.rcs.model.Utente;

/**
 * Questa classe rappresenta un invito, ovvero una richiesta fatta da un utente
 * a un altro che può essere accettata o declinata (ma non entrambe).
 * Per farlo, questa classe utilizza il design pattern Template Method
 */
public abstract class Invito {
    private Utente mittente;
    private Utente invitato;
    private StatoInvito stato = StatoInvito.IN_ATTESA;

    protected Invito(Utente mittente, Utente invitato) {
        this.mittente = mittente;
        this.invitato = invitato;
    }

    public Utente getMittente() {
        return this.mittente;
    }

    protected void setMittente(Utente mittente) {
        this.mittente = mittente;
    }

    public Utente getInvitato() {
        return this.invitato;
    }

    protected void setInvitato(Utente invitato) {
        this.invitato = invitato;
    }

    public StatoInvito getStato() {
        return this.stato;
    }

    private void setStato(StatoInvito stato) {
        this.stato = stato;
    }

    /**
     * Questo metodo permette di accettare l'invito. L'invito si può accettare
     * soltanto se il suo stato è "in attesa". Una volta accettato l'invito, il suo
     * stato diventa "accettato"
     * [Questo metodo definisce il template per l'accettazione dell'invito (design
     * pattern: Template Method) e non può essere sovrascritto. La logica di
     * accettazione effettiva deve essere definita implementando il metodo
     * astratto `elaboraAccettazione()`]
     */
    public final void accetta() {
        if (this.getStato() != StatoInvito.IN_ATTESA) {
            return;
        }

        this.elaboraAccettazione();

        this.setStato(StatoInvito.ACCETTATO);
    }

    /**
     * Questo metodo effettua le operazioni necessarie all'accettazione dell'invito
     */
    protected abstract void elaboraAccettazione();

    /**
     * Questo metodo permette di declinare l'invito. L'invito si può declinare
     * soltanto se il suo stato è "in attesa". Una volta declinato l'invito, il suo
     * stato diventa "declinato"
     * [Questo metodo definisce il template per la declinazione dell'invito (design
     * pattern: Template Method) e non può essere sovrascritto. La logica di
     * declinazione effettiva deve essere definita implementando il metodo astratto
     * `elaboraDeclinazione()`]
     */
    public final void declina() {
        if (this.getStato() != StatoInvito.IN_ATTESA) {
            return;
        }

        this.elaboraDeclinazione();

        this.setStato(StatoInvito.DECLINATO);
    }

    /**
     * Questo metodo effettua le operazioni necessarie alla declinazione dell'invito
     */
    protected abstract void elaboraDeclinazione();
}
