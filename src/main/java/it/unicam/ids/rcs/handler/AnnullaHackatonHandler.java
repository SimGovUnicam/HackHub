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

import it.unicam.ids.rcs.controller.HackatonController;
import it.unicam.ids.rcs.model.Hackaton;
import it.unicam.ids.rcs.repository.HackatonRepository;

import java.util.List;

/**
 * Questa classe rappresenta un gestore del caso d'uso Annulla Hackaton.
 * Espone le funzionalità per
 */
public class AnnullaHackatonHandler {

    private HackatonController  hackatonController;

    public AnnullaHackatonHandler() {}

    /**
     * Avvia il processo di annullamento dell'hackaton
     * @return una lista di <code>Hackaton</code> annullabili.
     */
    public List<Hackaton> annullaHackaton(){
        HackatonRepository hackatonRepository = new HackatonRepository();
        this.setHackatonController(new HackatonController(hackatonRepository));
        List<Hackaton> listaHackatonAnnullabili = this.hackatonController.getListaHackatonAnnullabili();
        if(listaHackatonAnnullabili.isEmpty())
            return null;
        return listaHackatonAnnullabili;
    }

    private void setHackatonController(HackatonController hackatonController) {
        this.hackatonController = hackatonController;
    }

    /**
     * Questo metodo si occupa di avviare la procedura di ricerca di un hackaton
     * all'interno del sistema di memorizzazione.
     * @param nomeHackaton Il nome dell'hackaton da utilizzare per la selezione
     * @return l'<code>Hackaton</code> ottenuto dalla ricerca, <code>null</code> altrimenti.
     */
    public Hackaton selezionaHackaton(String nomeHackaton){
        return this.hackatonController.selezionaHackaton(nomeHackaton);
    }

    /**
     * Conferma l'annullamento dell'hackaton. Il sistema registra il nuovo stato dell'hackaton
     * @return <code>True</code> se l'hackaton è stato annullato correttamente, <code>False</code> altrimenti.
     */
    public boolean confermaAnnullamento(){
        return this.hackatonController.confermaAnnullamento() != null;
    }

}
