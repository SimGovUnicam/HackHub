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
import it.unicam.ids.rcs.model.invito.Invito;
import jdk.jshell.spi.ExecutionControl;

public class NotificaInvito extends Notifica {
    private Invito invito;

    public NotificaInvito(Utente mittente, Utente desinatario, Invito invito) {
        super(mittente, desinatario);
        this.invito = invito;
    }

    private Invito getInvito() {
        return this.invito;
    }

    @Override
    public String ottieniMessaggioPerOrganizzatore() throws ExecutionControl.NotImplementedException {
        return this.setMessaggio(this.getInvito().ottieniMessaggioPerOrganizzatore());
    }

    @Override
    public String ottieniMessaggioPerGiudice() throws ExecutionControl.NotImplementedException {
        return this.setMessaggio(this.getInvito().ottieniMessaggioPerGiudice());
    }

    @Override
    public String ottieniMessaggioPerMentore() throws ExecutionControl.NotImplementedException {
        return this.setMessaggio(this.getInvito().ottieniMessaggioPerMentore());
    }

    @Override
    public String ottieniMessaggioPerMembroDelloStaff() throws ExecutionControl.NotImplementedException {
        return this.setMessaggio(this.getInvito().ottieniMessaggioPerMembroDelloStaff());
    }

    @Override
    public String ottieniMessaggioPerMembroDelTeam() throws ExecutionControl.NotImplementedException {
        return this.setMessaggio(this.getInvito().ottieniMessaggioPerMembroDelTeam());
    }

    @Override
    public String ottieniMessaggioPerUtente() throws ExecutionControl.NotImplementedException {
        return this.setMessaggio(this.getInvito().ottieniMessaggioPerUtente());
    }
}