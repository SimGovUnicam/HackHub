package it.unicam.ids.rcs.controller;

import it.unicam.ids.rcs.model.Utente;
import it.unicam.ids.rcs.repository.UtenteRepository;

/**
 * Questa classe si occupa della gestione delle operazioni che riguardano gli utenti.
 */
public class UtenteController {

    private final UtenteRepository utenteRepository;

    public UtenteController() {
        this.utenteRepository = new UtenteRepository();
    }

    /**
     * Questo metodo esegue la ricerca di un utente attraverso l'email.
     * @param email L'indirizzo email da cercare.
     * @return l'oggetto <code>Utente</code> oppure null.
     */
    public Utente cercaUtente(String email) {
        return this.utenteRepository.cercaPerEmail(email);
    }
}
