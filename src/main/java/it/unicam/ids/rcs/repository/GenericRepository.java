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

import it.unicam.ids.rcs.util.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Questa classe rappresenta una repository generica che implementa le operazioni
 * principali attraverso Hibernate
 *
 * @param <E> Il tipo dell'entità gestita da questa repository
 */
public class GenericRepository<E> {
    /**
     * Registra la nuova entità nel database
     *
     * @param entita L'entità da registrare
     */
    public void crea(E entita) {
        Session session = Hibernate.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entita);
        transaction.commit();
        session.close();
    }

    /**
     * Aggiorna l'entità nel database
     */
    public E aggiorna(E entita) {
        Session session = Hibernate.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        E entitaAggiornata = session.merge(entita);
        transaction.commit();
        session.close();
        return entitaAggiornata;
    }
}
