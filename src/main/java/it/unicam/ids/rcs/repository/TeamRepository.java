package it.unicam.ids.rcs.repository;

import it.unicam.ids.rcs.model.Team;
import it.unicam.ids.rcs.util.Hibernate;
import org.hibernate.Session;

public class TeamRepository {

    public TeamRepository() {
    }

    /**
     *
     * @param nome
     * @return
     */
    public Team cercaPerNome(String nome){
        Session session = Hibernate.getSessionFactory().openSession();
        Team team = session.createQuery("from Team where nome = :nome",Team.class)
                .setParameter("nome",nome)
                .uniqueResult();
        session.close();
        return team;
    }

    public void registraTeam(Team team){
        //TODO
    }
}
