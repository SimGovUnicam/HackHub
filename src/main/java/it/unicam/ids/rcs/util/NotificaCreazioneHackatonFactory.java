package it.unicam.ids.rcs.util;

import it.unicam.ids.rcs.model.Hackaton;
import it.unicam.ids.rcs.model.Notifica;
import it.unicam.ids.rcs.model.NotificaCreazioneHackaton;
import it.unicam.ids.rcs.model.Utente;

public abstract class NotificaCreazioneHackatonFactory extends NotificaFactory {
    private Hackaton hackaton;
    private Utente utente;

    public NotificaCreazioneHackatonFactory(Hackaton hackaton, Utente utente) {
        this.hackaton = hackaton;
        this.utente = utente;

    }

    public Notifica getNotifica() {
        return new NotificaCreazioneHackaton(hackaton, utente);
    }
}
