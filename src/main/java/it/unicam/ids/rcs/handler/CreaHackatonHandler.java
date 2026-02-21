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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package it.unicam.ids.rcs.handler;

import it.unicam.ids.rcs.controller.HackatonController;
import it.unicam.ids.rcs.handler.richiesta.RichiestaCreaHackaton;
import it.unicam.ids.rcs.model.Hackaton;
import it.unicam.ids.rcs.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Questa classe rappresenta un gestore del caso d'uso Crea Hackaton.
 * Espone le funzioni necessarie ad avviare, gestire e completare la registrazione
 * di un nuovo hackaton all'interno del sistema.
 */
@RestController
public class CreaHackatonHandler {

    private HackatonController hackatonController;
    private MessageSource messageSource;

    public CreaHackatonHandler() {
    }

    @Autowired
    public CreaHackatonHandler(HackatonController hackatonController, MessageSource messageSource) {
        this.hackatonController = hackatonController;
        this.messageSource = messageSource;
    }

    public HackatonController getHackatonController() {
        return this.hackatonController;
    }

    private void setHackatonController(HackatonController hackatonController) {
        this.hackatonController = hackatonController;
    }

    /**
     * Avvia il processo di creazione dell'hackaton
     *
     * @param richiesta La richiesta di creazione dell'Hackaton
     * @return <code>True</code> se l'operazione termina con successo, <code>false</code> altrimenti
     */
    @PostMapping("/hackaton/crea")
    public ResponseEntity<String> creaHackaton(@RequestBody RichiestaCreaHackaton richiesta) {
        this.hackatonController.creaHackaton(
                richiesta.getNome(),
                richiesta.getDimensioneMassimaTeam(),
                richiesta.getRegolamento(),
                richiesta.getScadenzaIscrizioni(),
                richiesta.getInizio(),
                richiesta.getFine(),
                richiesta.getLuogo(),
                richiesta.getPremio()
        );
        return new ResponseEntity<>("Creazione hackaton avviata con successo", HttpStatus.OK);
    }

    /**
     * Designa l'utente indicato attraverso l'e-mail come giudice dell'hackaton
     *
     * @param email L'e-mail dell'utente da designare come giudice
     * @return <code>True</code> in caso di successo, <code>false</code> altrimenti
     * (e.g. Non esistono utenti con l'e-mail fornita)
     */
    @GetMapping("/hackaton/crea/giudice")
    public ResponseEntity<String> assegnaGiudice(@RequestParam String email) {
        this.checkCreazioneHackatonAvviata();
        try {
            this.hackatonController.assegnaGiudice(email);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Errore nell'assegnazione del giudice: " + exception.getMessage());
        }
        return new ResponseEntity<>("Giudice assegnato con successo", HttpStatus.OK);
    }

    private void checkCreazioneHackatonAvviata() {
        if (this.getHackatonController().getHackaton() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Creazione hackaton non avviata");
        }
    }

    /**
     * Designa l'utente indicato attraverso l'e-mail come mentore dell'hackaton
     *
     * @param emails L'e-mail dell'utente da designare come mentore
     * @return <code>True</code> in caso di successo, <code>false</code> altrimenti
     * (e.g. Non esistono utenti con l'e-mail fornita)
     */
    @PostMapping("/hackaton/crea/mentori")
    public ResponseEntity<String> aggiungiMentori(@RequestBody List<String> emails) {
        this.checkCreazioneHackatonAvviata();
        try {
            this.hackatonController.aggiungiMentori(emails);
        } catch (IllegalArgumentException e) {
            String message = "Errore durante l'aggiunta dei mentori: " + e.getMessage();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
        return new ResponseEntity<>(this.getRispostaMentoriAssegnati(), HttpStatus.OK);
    }

    private String getRispostaMentoriAssegnati() {
        StringBuilder message = new StringBuilder("Mentori assegnati con successo: \n");
        var mentori = this.getHackatonController().getHackaton().getMentori();
        for (Utente mentore : mentori) {
            message.append(mentore).append("\n");
        }
        return message.toString();
    }

    /**
     * Conferma la creazione dell'hackaton. Il sistema registra l'hackaton
     *
     * @return <code>Hackaton</code> in caso di successo, <code>null</code> altrimenti
     */
    @GetMapping("/hackaton/crea/confermaCreazione")
    public ResponseEntity<Hackaton> confermaCreazione() {
        Hackaton hackaton;
        try {
            hackaton = this.getHackatonController().registraHackaton();
        } catch (Exception exception) {
            String message = "Errore durante la registrazione dell'hackaton: " + exception.getMessage();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
        return new ResponseEntity<>(hackaton, HttpStatus.CREATED);
    }
}