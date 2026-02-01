
package it.unicam.ids.rcs.model;

public abstract class Notifica {
    private Utente mittente;
    private Utente destinatario;
    private String messaggio;

    protected Notifica(Utente mittente, Utente destinatario) {
        this.mittente = mittente;
        this.destinatario = destinatario;
    }

    protected Notifica() {
    }

    public abstract String ottieniMessaggioPerOrganizzatore();

    public abstract String ottieniMessaggioPerGiudice();

    public abstract String ottieniMessaggioPerMentore();

    public abstract String ottieniMessaggioPerMembroDelloStaff();

    public abstract String ottieniMessaggioPerMembroDelTeam();
}


