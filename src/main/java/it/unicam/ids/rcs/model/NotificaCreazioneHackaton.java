package it.unicam.ids.rcs.model;

public class NotificaCreazioneHackaton extends Notifica {
    private Hackaton hackaton;

    public NotificaCreazioneHackaton(Hackaton hackaton, Utente utente) {
        super(utente, null, "");
        this.hackaton = hackaton;

    }

    @Override
    public String ottieniMessaggioPerOrganizzatore() {
        return "É stato creato un nuovo Hackaton: " + hackaton.getNome();
    }

    @Override
    public String ottieniMessaggioPerGiudice() {
        return "É stato creato un nuovo Hackaton: " + hackaton.getNome();
    }

    @Override
    public String ottieniMessaggioPerMentore() {
        return "É stato creato un nuovo Hackaton: " + hackaton.getNome();
    }

    @Override
    public String ottieniMessaggioPerMembroDelloStaff() {
        return "É stato creato un nuovo Hackaton: " + hackaton.getNome();
    }

    @Override
    public String ottieniMessaggioPerMembroDelTeam() {
        return "É stato creato un nuovo Hackaton: " + hackaton.getNome();
    }
}
