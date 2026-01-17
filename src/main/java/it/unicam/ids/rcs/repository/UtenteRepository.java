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

package it.unicam.ids.rcs.repository;

import it.unicam.ids.rcs.model.Utente;
import it.unicam.ids.rcs.util.Hibernate;
import org.hibernate.Session;

/**
 * Questa classe rappresenta l'entità che interagisce con il database, eseguendo operazioni CRUD
 * in base alle richieste inviate dal controller.
 */
public class UtenteRepository {

    /**
     * Questa classe si occupa della ricerca all'interno del database di un utente in base alla mail
     * passata come parametro.
     * @param email L'email dell'utente da cercare.
     * @return l'oggetto <code>Utente</code> trovato oppure <code>null</code> se non esiste.
     */
    public Utente cercaPerEmail(String email) {
        Session session = Hibernate.getSessionFactory().openSession();
        Utente utente = session.createQuery("from Utente where email = :email", Utente.class).uniqueResult();
        session.close();
        return utente;
    }
}
