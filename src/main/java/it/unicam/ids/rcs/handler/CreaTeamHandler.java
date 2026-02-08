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

package it.unicam.ids.rcs.handler;

import it.unicam.ids.rcs.controller.TeamController;
import it.unicam.ids.rcs.controller.UtenteController;
import it.unicam.ids.rcs.model.Utente;
import it.unicam.ids.rcs.repository.TeamRepository;

/**
 * Questa classe rappresenta un gestore del caso d'uso Crea Team.
 * Espone le funzioni necessarie ad avviare, gestire e completare la registrazione
 * di un nuovo team all'interno del sistema.
 */
public class CreaTeamHandler {
    private TeamController teamController;

    public CreaTeamHandler() {
    }

    private void setTeamController(TeamController teamController) {
        this.teamController = teamController;
    }

    /**
     * Avvia il processo di creazione del team.
     * @param nome Il nome da assegnare al team.
     * @return <code>True</code> se il team viene creato con successo, <code>False</code> altrimenti.
     */
    public boolean creaTeam(String nome){
        TeamRepository teamRepository = new TeamRepository();
        this.setTeamController(new TeamController(teamRepository));
        this.teamController.creaTeam(nome);
        return false;
    }

    /**
     * Questo metodo conferma la creazione del team. Il sistema registra il nuovo team.
     */
    public void confermaCreazione(){
        Utente utenteInSessione = UtenteController.getUtenteInSessione();
        this.teamController.registraTeam(utenteInSessione);
    }
}
