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
 * Questa classe rappresenta una notifica per la creazione dell'Hackaton
 * Questa classe fa parte del design pattern Factory Method e svolge
 * il ruolo di Prodotto Concreto
 */
public class NotificaCreazioneHackaton extends Notifica {
    private Hackaton hackaton;

    public NotificaCreazioneHackaton(Utente mittente, Utente destinatario, Hackaton hackaton) {
        super(mittente, destinatario);
        this.hackaton = hackaton;
    }

    @Override
    public String ottieniMessaggioPerOrganizzatore() {
        return this.setMessaggio("É stato creato un nuovo Hackaton: " + hackaton.getNome());
    }

    @Override
    public String ottieniMessaggioPerGiudice() {
        return this.setMessaggio("É stato creato un nuovo Hackaton: " + hackaton.getNome());
    }

    @Override
    public String ottieniMessaggioPerMentore() {
        return this.setMessaggio("É stato creato un nuovo Hackaton: " + hackaton.getNome());
    }

    @Override
    public String ottieniMessaggioPerMembroDelloStaff() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Messaggio per membro dello staff non previsto");
    }

    @Override
    public String ottieniMessaggioPerMembroDelTeam() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Messaggio per membro del team non previsto");
    }

    public String ottieniMessaggioPerUtente() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Messaggio per utente non previsto");
    }
}
