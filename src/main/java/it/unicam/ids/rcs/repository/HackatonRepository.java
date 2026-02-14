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

import it.unicam.ids.rcs.model.Hackaton;
import it.unicam.ids.rcs.model.Utente;
import it.unicam.ids.rcs.util.Hibernate;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

/**
 * Questa classe rappresenta l'entità che interagisce con il database, eseguendo operazioni CRUD
 * in base alle richieste inviate dal controller.
 */
public class HackatonRepository extends GenericRepository<Hackaton> {

    public HackatonRepository() {
    }

    /**
     * Questo metodo registra l'hackaton all'interno del database.
     *
     * @param hackaton L'hackaton da registrare sul database.
     */
    public void registraHackaton(Hackaton hackaton) {
        super.crea(hackaton);
    }

    /**
     * Aggiorna i dati dell'hackaton fornito
     *
     * @param hackaton L'hackaton con i dati aggiornati
     * @return L'hackaton aggiornato
     */
    public Hackaton aggiornaHackaton(Hackaton hackaton) {
        return super.aggiorna(hackaton);
    }

    /**
     * Cerca e restituisce l'hackaton con il nome indicato, se presente
     *
     * @param nomeHackaton Il nome dell'hackaton da cercare
     * @return L'<code>hackaton</code> con il nome indicato, se presente
     */
    public Hackaton cercaPerNome(String nomeHackaton) {
        Session session = Hibernate.getSessionFactory().openSession();
        Hackaton hackaton = session.createQuery("from Hackaton where nome = :nomeHackaton", Hackaton.class)
                .setParameter("nomeHackaton", nomeHackaton)
                .uniqueResult();
        session.close();
        return hackaton;
    }

    /**
     * Si occupa di recuperare tutti gli hackaton creati da uno specifico utente,
     * in cui le iscrizioni sono ancora aperte.
     *
     * @param organizzatoreHackaton L'utente che ha organizzato gli hackaton
     * @return una lista di <code>Hackaton</code>
     */
    public List<Hackaton> getHackatonsConIscrizioniAperte(Utente organizzatoreHackaton) {
        LocalDate oggi = LocalDate.now();
        Session session = Hibernate.getSessionFactory().openSession();
        String query = "from Hackaton" +
                " where organizzatore = :organizzatoreHackaton" +
                " and scadenzaIscrizioni >= :oggi" +
                " and annullato == false";
        List<Hackaton> hackatons = session.createQuery(query, Hackaton.class)
                .setParameter("organizzatoreHackaton", organizzatoreHackaton)
                .setParameter("oggi", oggi)
                .getResultList();
        session.close();
        return hackatons;
    }
}
