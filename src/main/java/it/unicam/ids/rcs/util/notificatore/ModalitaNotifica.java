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

/**
 * Questa enumerazione elenca le diverse modalità di notifica
 * tramite cui il sistema può notificare un utente.
 */
public enum ModalitaNotifica {
    EMAIL,
    SMS;

    /**
     * Questo metodo si occupa di restituire il rispettivo notificatore in base
     * alla modalità di notifica.
     * @param successivo Il notificatore di cui fare il wrap
     * @return un nuovo oggetto <code>WrapperNotificatore</code>.
     */
    public WrapperNotificatore getNotificatore(WrapperNotificatore successivo) {
        return switch (this){
            case EMAIL -> new NotificatoreEmail(successivo);
            case SMS -> new NotificatoreSMS(successivo);
        };
    }
}
