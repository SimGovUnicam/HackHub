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

package it.unicam.ids.rcs.repository;

import it.unicam.ids.rcs.model.Team;
import it.unicam.ids.rcs.util.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

/**
 * Questa classe rappresenta l'entità che interagisce con il database, eseguendo operazioni CRUD
 * in base alle richieste inviate dal controller.
 */
@Component
public class TeamRepository extends GenericRepository<Team> {

    public TeamRepository() {
    }

    /**
     * Questo metodo si occupa di scrivere e salvare il team all'interno del database.
     *
     * @param team
     */
    public void registraTeam(Team team) {
        super.crea(team);
    }

    /**
     * Esegue la ricerca del team nel database attraverso il nome.
     *
     * @param nome
     * @return il <code>Team</code> registrato nel database con quel nome, <code>null</code> altrimenti.
     */
    public Team cercaPerNome(String nome) {
        Session session = Hibernate.getSessionFactory().openSession();
        Team team = session.createQuery("from Team where nome = :nome", Team.class)
                .setParameter("nome", nome)
                .uniqueResult();
        session.close();
        return team;
    }
}
