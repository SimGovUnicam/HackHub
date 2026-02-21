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

import it.unicam.ids.rcs.model.Utente;
import it.unicam.ids.rcs.model.invito.Invito;
import it.unicam.ids.rcs.model.invito.InvitoAdesioneTeam;
import it.unicam.ids.rcs.model.notifica.Notifica;
import it.unicam.ids.rcs.repository.InvitoRepository;
import it.unicam.ids.rcs.util.notifica.GestoreNotifiche;
import it.unicam.ids.rcs.util.notifica.NotificaFactory;
import it.unicam.ids.rcs.util.notifica.NotificaInvitoFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Questa classe si occupa della gestione delle operazioni che riguardano gli inviti
 */
public class InvitoController {
    private final InvitoRepository invitoRepository;
    private final UtenteController utenteController;
    private Invito invito;

    public InvitoController(InvitoRepository invitoRepository, UtenteController utenteController) {
        this.invitoRepository = invitoRepository;
        this.utenteController = utenteController;
    }

    public Invito getInvito() {
        return this.invito;
    }

    private void setInvito(Invito invito) {
        this.invito = invito;
    }

    private InvitoRepository getInvitoRepository() {
        return this.invitoRepository;
    }

    private UtenteController getUtenteController() {
        return this.utenteController;
    }

    /**
     * Crea un nuovo invito di adesione al proprio team per invitare l'utente
     * relativo all'e-mail fornita
     *
     * @param emailUtenteDaInvitare L'e-mail dell'utente che si vuole invitare
     * @return L'invito se questo viene creato, null altrimenti
     */
    public Invito creaInvitoAdesioneTeam(String emailUtenteDaInvitare) {
        Utente utenteDaInvitare = this.getUtenteController().cercaUtente(emailUtenteDaInvitare);
        if (utenteDaInvitare == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Utente non trovato");

        if(utenteDaInvitare.getTeam() != null)
            throw new ResponseStatusException(HttpStatus.CONFLICT,"L'utente fa già parte di un team");

        Utente mittenteInvito = UtenteController.getUtenteInSessione();
        this.setInvito(new InvitoAdesioneTeam(mittenteInvito, utenteDaInvitare));
        return this.getInvito();
    }

    /**
     * Conferma la creazione dell'invito, registrandolo nel sistema e inviando
     * la relativa notifica all'invitato
     */
    public void confermaInvito() {
        this.getInvitoRepository().registraInvito(this.getInvito());
        this.inviaNotificaInvito();
    }

    /**
     * Invia una notifica al destinatario dell'invito
     */
    private void inviaNotificaInvito() {
        Invito invito = this.getInvito();
        NotificaFactory factory = new NotificaInvitoFactory(invito);
        Notifica notifica = factory.getNotifica(invito.getMittente(), invito.getInvitato());
        notifica.ottieniMessaggioPerUtente();

        var gestoreNotifiche = new GestoreNotifiche();
        gestoreNotifiche.inviaNotifica(notifica);
    }
}
