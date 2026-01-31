package it.unicam.ids.rcs.util;

import it.unicam.ids.rcs.model.Hackaton;
import it.unicam.ids.rcs.model.Notifica;
import it.unicam.ids.rcs.model.NotificaCreazioneHackaton;
import it.unicam.ids.rcs.model.Utente;

public abstract class NotificaModificaHackatonFactory extends NotificaFactory{
    private Hackaton  hackaton;
    private Utente utente;
    public NotificaModificaHackatonFactory(Hackaton hackaton, Utente utente){

    }
    @Override
    public Notifica getNotifica() {
        return new NotificaCreazioneHackaton(hackaton, utente);
    }
}
