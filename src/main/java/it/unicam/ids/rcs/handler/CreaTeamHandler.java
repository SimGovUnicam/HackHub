package it.unicam.ids.rcs.handler;

import it.unicam.ids.rcs.controller.TeamController;
import it.unicam.ids.rcs.controller.UtenteController;
import it.unicam.ids.rcs.model.Utente;
import it.unicam.ids.rcs.repository.TeamRepository;

/**
 *
 */
public class CreaTeamHandler {
    private TeamController teamController;

    public CreaTeamHandler() {
    }

    private void setTeamController(TeamController teamController) {
        this.teamController = teamController;
    }

    public boolean creaTeam(String nome){
        TeamRepository teamRepository = new TeamRepository();
        this.setTeamController(new TeamController(teamRepository));
        this.teamController.creaTeam(nome);
        return false;
        //TODO
    }

    public void confermaCreazione(){
        Utente utenteInSessione = UtenteController.getUtenteInSessione();
        this.teamController.registraTeam(utenteInSessione);
        //TODO
    }
}
