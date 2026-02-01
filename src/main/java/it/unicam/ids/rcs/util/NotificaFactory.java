package it.unicam.ids.rcs.util;

import it.unicam.ids.rcs.model.Notifica;
import it.unicam.ids.rcs.model.Utente;

public abstract class NotificaFactory {
    public abstract Notifica getNotifica(Utente  mittente, Utente destinatario);
}

