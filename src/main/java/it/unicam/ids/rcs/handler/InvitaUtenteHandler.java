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

import it.unicam.ids.rcs.controller.InvitoController;
import it.unicam.ids.rcs.controller.UtenteController;
import it.unicam.ids.rcs.model.invito.Invito;
import it.unicam.ids.rcs.repository.InvitoRepository;
import it.unicam.ids.rcs.repository.UtenteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class InvitaUtenteHandler {

    private InvitoController invitoController;

    public InvitaUtenteHandler() {}

    protected void setInvitoController(InvitoController invitoController) {
        this.invitoController = invitoController;
    }

    /**
     * Avvia il processo di invito di un utente ad unirsi ad un team.
     * @param emailUtenteDaInvitare L'email dell'utente da invitare.
     * @return l'oggetto <code>Invito</code> che è stato creato
     */
    @PostMapping("/team/invita")
    public ResponseEntity<Invito> invitaUtente(@RequestParam(name = "email") String emailUtenteDaInvitare){
        UtenteRepository utenteRepository = new UtenteRepository();
        UtenteController utenteController = new UtenteController(utenteRepository);
        InvitoRepository invitoRepository = new InvitoRepository();
        this.setInvitoController(new InvitoController(invitoRepository,utenteController));
        Invito invito = this.invitoController.creaInvitoAdesioneTeam(emailUtenteDaInvitare);
        return new ResponseEntity<>(invito, HttpStatus.OK);
    }

    /**
     *
     */
    @GetMapping("/team/confermaInvito")
    public ResponseEntity<String> confermaInvito(){
        this.invitoController.confermaInvito();
        return new ResponseEntity<>("Invito creato con successo",HttpStatus.OK);
    }
}
