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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package it.unicam.ids.rcs.util.notificatore;

import it.unicam.ids.rcs.model.notifica.Notifica;

/**
 * Questa classe rappresenta il notificatore base.
 * L'obiettivo principale di questa classe è il wrap di un oggetto del suo stesso tipo o di
 * una sua sottoclasse.
 */
public abstract class WrapperNotificatore implements Notificatore{
    private WrapperNotificatore wrappee;

    public WrapperNotificatore(WrapperNotificatore notificatore){
        this.wrappee = notificatore;
    }

    public WrapperNotificatore getWrappee(){
        return this.wrappee;
    }

    private void setWrappee(WrapperNotificatore wrappee){
        this.wrappee = wrappee;
    }

    /**
     * Invoca linviaNotifica sul riferimento attualmente presente dentro
     * l'attributo wrappee.
     * @param notifica La notifica da inviare
     */
    public void inviaNotifica(Notifica notifica) {
        if(this.getWrappee() != null)
            this.getWrappee().inviaNotifica(notifica);
    }
}
