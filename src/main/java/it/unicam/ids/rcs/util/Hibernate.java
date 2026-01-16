package it.unicam.ids.rcs.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Questa classe si occupa della gestione della session factory
 * tramite il design pattern Singleton.
 */
public class Hibernate {

    private static final SessionFactory sessionFactory = build();

    private static SessionFactory build() {
        try{
            return new Configuration().configure().buildSessionFactory();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
