package it.unicam.ids.rcs.util;

import it.unicam.ids.rcs.model.Hackaton;
import it.unicam.ids.rcs.model.Notifica;
import it.unicam.ids.rcs.model.NotificaCreazioneHackaton;
import it.unicam.ids.rcs.model.Utente;

public class NotificaCreazioneHackatonFactory extends NotificaFactory {
    private Hackaton hackaton;

    public NotificaCreazioneHackatonFactory(Hackaton hackaton) {
        this.hackaton = hackaton;
    }

    @Override
    public Notifica getNotifica(Utente mittente, Utente destinatario) {
        return new NotificaCreazioneHackaton(mittente, destinatario, this.hackaton);
    }
}
