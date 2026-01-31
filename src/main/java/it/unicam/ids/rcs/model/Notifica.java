
package it.unicam.ids.rcs.model;

public abstract class Notifica {
    private Utente mittente;
    private Utente destinatario;
    private String messaggio;

    public Notifica(Utente mittente, Utente destinatario, String messaggio) {
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.messaggio = messaggio;
    }

    protected Notifica() {
    }

    public abstract String ottieniMessaggioPerOrganizzatore();

    public abstract String ottieniMessaggioPerGiudice();

    public abstract String ottieniMessaggioPerMentore();

    public abstract String ottieniMessaggioPerMembroDelloStaff();

    public abstract String ottieniMessaggioPerMembroDelTeam();
}


