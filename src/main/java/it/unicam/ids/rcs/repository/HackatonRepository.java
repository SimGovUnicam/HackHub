package it.unicam.ids.rcs.repository;

import it.unicam.ids.rcs.model.Hackaton;
import it.unicam.ids.rcs.util.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Questa classe rappresenta l'entità che interagisce con il database, eseguendo operazioni CRUD
 * in base alle richieste inviate dal controller.
 */
public class HackatonRepository {

    public HackatonRepository() {}

    /**
     * Questo metodo registra l'hackaton all'interno del database.
     * @param hackaton L'hackaton da registrare sul database.
     */
    public void registraHackaton(Hackaton hackaton) {
        Session session = Hibernate.getSessionFactory().openSession();
        session.beginTransaction();
        Transaction transaction = session.beginTransaction();
        session.persist(hackaton);
        transaction.commit();
        session.close();
    }
}
