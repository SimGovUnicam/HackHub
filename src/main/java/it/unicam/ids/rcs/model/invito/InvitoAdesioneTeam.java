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

import it.unicam.ids.rcs.model.Team;
import it.unicam.ids.rcs.model.Utente;

/**
 * Questa classe rappresenta un invito di adesione a un team. Il mittente ha
 * invitato l'invitato a far parte del suo team
 */
public class InvitoAdesioneTeam extends Invito {
    public InvitoAdesioneTeam(Utente mittente, Utente invitato) {
        super(mittente, invitato);
    }

    @Override
    protected void elaboraAccettazione() {
        Team teamMittente = this.getMittente().getTeam();
        teamMittente.aggiungiMembro(this.getInvitato());
    }

    @Override
    protected void elaboraDeclinazione() {
        // Invito declinato, nessuna operazione necessaria
    }

    @Override
    public String ottieniMessaggioPerOrganizzatore() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Messaggio per Organizzatore non previsto");
    }

    @Override
    public String ottieniMessaggioPerGiudice() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Messaggio per Giudice non previsto");
    }

    @Override
    public String ottieniMessaggioPerMentore() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Messaggio per Mentore non previsto");
    }

    @Override
    public String ottieniMessaggioPerMembroDelloStaff() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Messaggio per membro dello staff non previsto");
    }

    @Override
    public String ottieniMessaggioPerMembroDelTeam() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Messaggio per membro dello team non previsto");
    }

    @Override
    public String ottieniMessaggioPerUtente() {
        return this.getMittente().getNome() + "ti ha invitato ad unirti al team" + this.getMittente().getTeam().getNome();
    }
}
