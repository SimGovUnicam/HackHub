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

import it.unicam.ids.rcs.model.Hackaton;
import it.unicam.ids.rcs.model.Utente;

/**
 * Questa classe estende la classe notifica
 * Questa classe rappresenta una notifica per l'annullamento dell'Hackaton
 * Questa classe fa parte del design pattern Factory Method e svolge
 * il ruolo di Prodotto Concreto
 */
public class NotificaAnnullamentoHackaton extends Notifica {
    private Hackaton hackaton;

    public NotificaAnnullamentoHackaton(Utente mittente, Utente destinatario, Hackaton hackaton) {
        super(mittente, destinatario);
        this.hackaton = hackaton;
    }

    public Hackaton getHackaton() {
        return this.hackaton;
    }

    private void setHackaton(Hackaton hackaton) {
        this.hackaton = hackaton;
    }

    @Override
    public String ottieniMessaggioPerOrganizzatore() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Messaggio per organizzatore dello staff non previsto");
    }

    @Override
    public String ottieniMessaggioPerGiudice() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Messaggio per giudice dello staff non previsto");
    }

    @Override
    public String ottieniMessaggioPerMentore() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Messaggio per mentore dello staff non previsto");
    }

    @Override
    public String ottieniMessaggioPerMembroDelloStaff() throws UnsupportedOperationException {
        return "L'hackaton " + this.getHackaton().getNome() + " è stato annullato";
    }

    @Override
    public String ottieniMessaggioPerMembroDelTeam() throws UnsupportedOperationException {
        return "L'hackaton " + this.getHackaton().getNome() + " è stato annullato";
    }

    @Override
    public String ottieniMessaggioPerUtente() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Messaggio per utente dello staff non previsto");
    }
}
