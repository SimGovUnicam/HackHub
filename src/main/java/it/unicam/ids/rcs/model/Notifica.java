
package it.unicam.ids.rcs.model;

public abstract class Notifica
{
    private Utente mittente;
    private Utente destinatario;
    private String messaggio;

    public Notifica(Utente mittente, Utente destinatario, String messaggio)
    {
        this.mittente= mittente;
        this.destinatario= destinatario;
        this.messaggio= messaggio;
    }

    public abstract String ottieniMessaggioPerOrganizzatore();
    public abstract String ottieniMessaggioPerGiudice();
    public abstract String ottieniMessaggioPerMentore();
    public abstract String ottieniMessaggioPerMembroDelloStaff();
    public abstract String ottieniMessaggioPerMemebroDelTeam();

    public Utente getMittente()
    {
     return this.mittente=mittente;
    }
    public Utente getDestinatario()
    {
        return destinatario;
    }
    public String getMessaggio()
    {
        return messaggio;
    }

}



