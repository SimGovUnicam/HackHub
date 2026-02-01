package it.unicam.ids.rcs.util;

import it.unicam.ids.rcs.model.*;

/**
 *
 */
public class NotificaModificaHackatonFactory extends NotificaFactory{
    private Hackaton hackaton;

    public NotificaModificaHackatonFactory(Hackaton hackaton) {
        this.hackaton = hackaton;
    }

    @Override
    public Notifica getNotifica(Utente mittente, Utente destinatario) {
        return new NotificaModificaHackaton(mittente, destinatario, this.hackaton);
    }
}
