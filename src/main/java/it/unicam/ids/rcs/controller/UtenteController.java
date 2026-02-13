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

package it.unicam.ids.rcs.controller;

import it.unicam.ids.rcs.model.Utente;
import it.unicam.ids.rcs.repository.UtenteRepository;
import it.unicam.ids.rcs.util.GestoreUtente;

/**
 * Questa classe si occupa della gestione delle operazioni che riguardano gli utenti.
 */
public class UtenteController {

    private final UtenteRepository utenteRepository;

    public UtenteController() {
        this.utenteRepository = new UtenteRepository();
    }

    public UtenteController(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    /**
     * Restituisce l'utente attualmente loggato nel sistema, se presente
     *
     * @return L'<code>utente</code> attualmente loggato nel sistema se presente,
     * <code>null</code> altrimenti
     */
    public static Utente getUtenteInSessione() {
        return GestoreUtente.getUtente();
    }

    /**
     * Questo metodo esegue la ricerca di un utente attraverso l'email.
     * @param email L'indirizzo email da cercare.
     * @return l'oggetto <code>Utente</code> oppure null.
     */
    public Utente cercaUtente(String email) {
        return this.utenteRepository.cercaPerEmail(email);
    }
}
