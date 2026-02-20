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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Questa classe rappresenta un gestore del caso d'uso Crea Team.
 * Espone le funzioni necessarie ad avviare, gestire e completare la registrazione
 * di un nuovo team all'interno del sistema.
 */
@RestController
public class CreaTeamHandler {
    private TeamController teamController;
    private UtenteController utenteController;

    @Autowired
    public CreaTeamHandler(TeamController teamController, UtenteController utenteController) {
        this.teamController = teamController;
        this.utenteController = utenteController;
    }

    /**
     * Avvia il processo di creazione del team.
     *
     * @param nome Il nome da assegnare al team.
     * @return <code>True</code> se il team viene creato con successo, <code>False</code> altrimenti.
     */
    @PostMapping("/team/crea")
    public ResponseEntity<String> creaTeam(@RequestParam(name = "nome") String nome) {
        if(nome == null || nome.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome del team non valido");

        Utente utenteInSessione = UtenteController.getUtenteInSessione();
        if (utenteInSessione.getTeam() != null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utente già associato ad un team");
        }
        this.teamController.creaTeam(nome);
        return new ResponseEntity<>("Creazione team avviata con successo", HttpStatus.OK);
    }

    /**
     * Questo metodo conferma la creazione del team. Il sistema registra il nuovo team.
     */
    @GetMapping("/team/confermaCreazione")
    public ResponseEntity<String> confermaCreazione() {
        Utente utenteInSessione = UtenteController.getUtenteInSessione();
        this.teamController.registraTeam(utenteInSessione);
        utenteInSessione.setTeam(this.teamController.getTeam());
        this.utenteController.aggiornaUtente(utenteInSessione);
        return new ResponseEntity<>("Team creato con successo", HttpStatus.OK);
    }
}
