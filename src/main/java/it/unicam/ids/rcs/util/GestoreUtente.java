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

package it.unicam.ids.rcs.util;

import it.unicam.ids.rcs.model.Utente;
import it.unicam.ids.rcs.repository.UtenteRepository;

/**
 * Questa classe rappresenta un gestore per l'utente che utilizza il sistema
 */
// TODO rendere Singleton
public class GestoreUtente {
    private static Utente utenteLoggato;

    // TODO rimuovere una volta implementati registrazione() e login()
    static {
        Utente utenteGenerico = new Utente();
        utenteGenerico.setEmail("email.email@email.email");
        utenteGenerico.setNome("john");
        utenteGenerico.setCognome("doe");
        utenteGenerico.setAccessoEffettuato(true);

        UtenteRepository repo = new UtenteRepository();
        Utente utente = repo.cercaPerEmail(utenteGenerico.getEmail());
        if (utente == null) {
            repo.crea(utenteGenerico);
        } else {
            utente.setAccessoEffettuato(true);
        }
        GestoreUtente.utenteLoggato = utente != null ? utente : utenteGenerico;
    }

    /**
     * Restituisce l'utente attualmente autenticato al sistema
     *
     * @return L'istanza di <code>Utente</code> dell'utente attualmente autenticato
     * al sistema
     */
    public static Utente getUtente() {
        return GestoreUtente.utenteLoggato;
    }
}
