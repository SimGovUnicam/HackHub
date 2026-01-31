package it.unicam.ids.rcs.model;

public class NotificaModificaHackaton extends Notifica {
    private Hackaton hackaton;

    public NotificaModificaHackaton(Utente utente, Hackaton hackaton) {
        super(utente, null, "");
        this.hackaton = hackaton;

    }

    @Override
    public String ottieniMessaggioPerOrganizzatore() {
        return "L'hackaton " + hackaton.getNome() + " è stato modificato.";
    }

    @Override
    public String ottieniMessaggioPerGiudice() {
        return "L'hackaton " + hackaton.getNome() + " è stato modificato.";
    }

    @Override
    public String ottieniMessaggioPerMentore() {
        return "L'hackaton " + hackaton.getNome() + " è stato modificato.";
    }

    @Override
    public String ottieniMessaggioPerMembroDelloStaff() {
        return "L'hackaton " + hackaton.getNome() + " è stato modificato.";
    }

    @Override
    public String ottieniMessaggioPerMembroDelTeam() {
        return "L'hackaton " + hackaton.getNome() + " è stato modificato.";
    }
}
