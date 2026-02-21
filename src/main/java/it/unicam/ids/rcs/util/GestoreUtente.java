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
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Questa classe rappresenta un gestore per l'utente che utilizza il sistema
 */
@Service
public class GestoreUtente {
    private static final String UTENTE_EMAIL_LOGGATO = "john.doe@email.email";

    private static final Map<String, Utente> utentiLoggati = new HashMap<>();

    // TODO rimuovere una volta implementati registrazione e login
    static {
        Utente utenteGenerico1 = new Utente();
        utenteGenerico1.setEmail(UTENTE_EMAIL_LOGGATO);
        utenteGenerico1.setNome("John");
        utenteGenerico1.setCognome("Doe");
        utenteGenerico1.setAccessoEffettuato(true);
        GestoreUtente.addUtente(utenteGenerico1);

        Utente utenteGenerico2 = new Utente();
        utenteGenerico2.setEmail("jane.doe@email.email");
        utenteGenerico2.setNome("Jane");
        utenteGenerico2.setCognome("Doe");
        utenteGenerico2.setAccessoEffettuato(true);
        GestoreUtente.addUtente(utenteGenerico2);

        Utente utenteGenerico3 = new Utente();
        utenteGenerico3.setEmail("jack.doe@email.email");
        utenteGenerico3.setNome("Jack");
        utenteGenerico3.setCognome("Doe");
        utenteGenerico3.setAccessoEffettuato(true);
        GestoreUtente.addUtente(utenteGenerico3);

        Utente utenteGenerico4 = new Utente();
        utenteGenerico4.setEmail("joe.doe@email.email");
        utenteGenerico4.setNome("Joe");
        utenteGenerico4.setCognome("Doe");
        utenteGenerico4.setAccessoEffettuato(true);
        GestoreUtente.addUtente(utenteGenerico4);

        Utente utenteGenerico5 = new Utente();
        utenteGenerico5.setEmail("jill.doe@email.email");
        utenteGenerico5.setNome("Jill");
        utenteGenerico5.setCognome("Doe");
        utenteGenerico5.setAccessoEffettuato(true);
        GestoreUtente.addUtente(utenteGenerico5);

        Utente utenteGenerico6 = new Utente();
        utenteGenerico6.setEmail("jasmine.doe@email.email");
        utenteGenerico6.setNome("Jasmine");
        utenteGenerico6.setCognome("Doe");
        utenteGenerico6.setAccessoEffettuato(true);
        GestoreUtente.addUtente(utenteGenerico6);
    }

    public static Map<String, Utente> getUtentiLoggati() {
        return GestoreUtente.utentiLoggati;
    }

    /**
     * TODO rimuovere una volta implementati registrazione e login
     *
     * @param utente L'utente da aggiungere
     */
    private static void addUtente(Utente utente) {
        UtenteRepository repo = new UtenteRepository();
        Utente utenteTrovato = repo.cercaPerEmail(utente.getEmail());
        if (utenteTrovato == null) {
            repo.crea(utente);
            utenteTrovato = utente;
        }
        utentiLoggati.put(utenteTrovato.getEmail(), utenteTrovato);
    }

    /**
     * Restituisce l'utente attualmente autenticato al sistema
     *
     * @return L'istanza di <code>Utente</code> dell'utente attualmente autenticato
     * al sistema
     */
    public static Utente getUtente() {
        return GestoreUtente.utentiLoggati.get(GestoreUtente.UTENTE_EMAIL_LOGGATO);
    }
}