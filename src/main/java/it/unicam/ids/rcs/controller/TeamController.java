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

package it.unicam.ids.rcs.controller;

import it.unicam.ids.rcs.model.Team;
import it.unicam.ids.rcs.model.Utente;
import it.unicam.ids.rcs.repository.TeamRepository;

/**
 * Questa classe si occupa della gestione delle operazioni che riguardano i Team
 */
public class TeamController {

    private TeamRepository teamRepository;
    private Team team;

    public TeamController(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    /**
     * Questo metodo si occupa della creazione di un nuovo team.
     * @param nome Il nome da assegnare al nuovo team.
     * @return <code>True</code> se il nuovo team viene creato con successo, <code>False</code> altrimenti.
     */
    public boolean creaTeam(String nome){
        if(this.teamRepository.cercaPerNome(nome) != null)
            return false;
        Team nuovoTeam = new Team(nome);
        this.team = nuovoTeam;
        return true;
    }

    /**
     * Registra il nuovo team ed imposta l'utente che l'ha creato come fondatore.
     * @param fondatore L'utente che ha creato il team.
     */
    public void registraTeam(Utente fondatore){
        this.team.setFondatore(fondatore);
        this.teamRepository.registraTeam(this.team);
    }
}
