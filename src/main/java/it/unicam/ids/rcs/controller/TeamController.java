package it.unicam.ids.rcs.controller;

import it.unicam.ids.rcs.model.Team;
import it.unicam.ids.rcs.model.Utente;
import it.unicam.ids.rcs.repository.TeamRepository;

public class TeamController {

    private TeamRepository teamRepository;
    private Team team;

    public TeamController(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public boolean creaTeam(String nome){
        if(this.teamRepository.cercaPerNome(nome) != null)
            return false;
        Team nuovoTeam = new Team(nome);
        this.team = nuovoTeam;
        return true;
    }

    public void registraTeam(Utente fondatore){
        this.team.setFondatore(fondatore);
        this.teamRepository.registraTeam(this.team);
        //TODO
    }
}
