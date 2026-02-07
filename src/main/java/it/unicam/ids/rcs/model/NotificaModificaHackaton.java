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

/**Questa classe estende la classe Notifica,
 * invia una notifica a Organizzatore,Giudice,Mentore,Membro del team,
 * a seguito delle modifiche effettuate dall'organizzatore all'Hackaton
 */
public class NotificaModificaHackaton extends Notifica {
    private Hackaton hackaton;

    public NotificaModificaHackaton(Utente mittente, Utente destinatario, Hackaton hackaton) {
        super(mittente, destinatario);
        this.hackaton = hackaton;
    }

    @Override
    public String ottieniMessaggioPerOrganizzatore() {
        return this.setMessaggio("L'hackaton " + hackaton.getNome() + " è stato modificato.");
    }

    @Override
    public String ottieniMessaggioPerGiudice() {
        return this.setMessaggio("L'hackaton " + hackaton.getNome() + " è stato modificato.");
    }

    @Override
    public String ottieniMessaggioPerMentore() {
        return this.setMessaggio("L'hackaton " + hackaton.getNome() + " è stato modificato.");
    }

    @Override
    public String ottieniMessaggioPerMembroDelloStaff() {
        return "";
    }

    @Override
    public String ottieniMessaggioPerMembroDelTeam() {
        return this.setMessaggio("L'hackaton " + hackaton.getNome() + " è stato modificato.");
    }

    public String ottieniMessaggioPerUtente() {
        return "";

    }

}
