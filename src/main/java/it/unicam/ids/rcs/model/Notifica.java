
package it.unicam.ids.rcs.model;

public class Notifica {
    private Utente mittente;
    private Utente destinatario;
    private String messagiio;
}
public Notifica(Utente mittente, Utente destinatario, String messaggio){
    this.mittente= mittente;
    this.destinatario= destinatario;
    this.messaggio= messaggio;
}
public String ottieniMessaggioPerOrganizzatore;
public String ottieniMessaggioPerGiudice;
public String ottieniMessaggioPerMentore;
public String ottieniMessaggioPerMembroDelloStaff;
public String ottieniMessaggioPerMemebroDelTeam;

public Utente getMittente(){
    return mittente;
}
public Utente getDestinatario(){
    return destinatario;
}
public String getMessaggio(){
    return messaggio;
}