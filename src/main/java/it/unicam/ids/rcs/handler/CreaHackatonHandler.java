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
import it.unicam.ids.rcs.model.Hackaton;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Questa classe rappresenta un gestore del caso d'uso Crea Hackaton.
 * Espone le funzioni necessarie ad avviare, gestire e completare la registrazione
 * di un nuovo hackaton all'interno del sistema.
 */
public class CreaHackatonHandler {

    private HackatonController hackatonController;

    public CreaHackatonHandler() {
    }

    private void setHackatonController(HackatonController hackatonController) {
        this.hackatonController = hackatonController;
    }

    /**
     * Avvia il processo di creazione dell'hackaton
     *
     * @param dimensioneMassimaTeam La dimensione massima di ciascun team
     * @param regolamento           Il regolamento completo dell'hackaton
     * @param scadenzaIscrizioni    La data di scadenza delle iscrizioni
     * @param inizio                Data di inizio dell'hackaton
     * @param fine                  Data di fine dell'hackaton
     * @param luogo                 Il luogo in cui si svolge l'hackaton
     * @param premio                Il premio in denaro per il vincitore dell'hackaton
     * @return <code>True</code> se l'operazione termina con successo, <code>false</code> altrimenti
     */
    public boolean creaHackaton(int dimensioneMassimaTeam, String regolamento, LocalDate scadenzaIscrizioni,
                                LocalDateTime inizio, LocalDateTime fine, String luogo, Double premio) {
        this.setHackatonController(new HackatonController());
        return this.hackatonController.creaHackaton(dimensioneMassimaTeam, regolamento, scadenzaIscrizioni,
                inizio, fine, luogo, premio);
    }

    /**
     * Designa l'utente indicato attraverso l'e-mail come giudice dell'hackaton
     *
     * @param email L'e-mail dell'utente da designare come giudice
     * @return <code>True</code> in caso di successo, <code>false</code> altrimenti
     * (e.g. Non esistono utenti con l'e-mail fornita)
     */
    public boolean assegnaGiudice(String email) {
        return email != null && !email.isEmpty() && this.hackatonController.assegnaGiudice(email);
    }

    /**
     * Designa l'utente indicato attraverso l'e-mail come mentore dell'hackaton
     *
     * @param email L'e-mail dell'utente da designare come mentore
     * @return <code>True</code> in caso di successo, <code>false</code> altrimenti
     * (e.g. Non esistono utenti con l'e-mail fornita)
     */
    public boolean aggiungiMentore(String email) {
        return email != null && !email.isEmpty() && this.hackatonController.aggiungiMentore(email);
    }

    /**
     * Conferma la creazione dell'hackaton. Il sistema registra l'hackaton
     *
     * @return <code>Hackaton</code> in caso di successo, <code>null</code> altrimenti
     */
    public Hackaton confermaCreazione() {
        return this.hackatonController.registraHackaton();
    }
}
