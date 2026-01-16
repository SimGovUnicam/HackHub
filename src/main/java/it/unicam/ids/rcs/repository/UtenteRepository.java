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
