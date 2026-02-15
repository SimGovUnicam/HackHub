
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

import it.unicam.ids.rcs.model.Utente;

/**
 * Questa classe viene usata per la gestione delle notifiche
 * Questa classe fa parte del design pattern Factory Method e svolge
 * il ruolo di Prodotto
 */
public abstract class Notifica implements Notificabile {
    private Utente mittente;
    private Utente destinatario;
    private String messaggio;

    protected Notifica(Utente mittente, Utente destinatario) {
        this.mittente = mittente;
        this.destinatario = destinatario;
    }

    protected Notifica() {
    }

    public Utente getMittente() {
        return mittente;
    }

    private void setMittente(Utente mittente) {
        this.mittente = mittente;
    }

    public Utente getDestinatario() {
        return destinatario;
    }

    private void setDestinatario(Utente destinatario) {
        this.destinatario = destinatario;
    }

    public String getMessaggio() {
        return messaggio;
    }

    protected String setMessaggio(String messaggio) {
        this.messaggio = messaggio;
        return messaggio;
    }

    public abstract String ottieniMessaggioPerOrganizzatore() throws UnsupportedOperationException;

    public abstract String ottieniMessaggioPerGiudice() throws UnsupportedOperationException;

    public abstract String ottieniMessaggioPerMentore() throws UnsupportedOperationException;

    public abstract String ottieniMessaggioPerMembroDelloStaff() throws UnsupportedOperationException;

    public abstract String ottieniMessaggioPerMembroDelTeam() throws UnsupportedOperationException;

    public abstract String ottieniMessaggioPerUtente() throws UnsupportedOperationException;
}


