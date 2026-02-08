package it.unicam.ids.rcs.model.notifica;

import it.unicam.ids.rcs.model.Hackaton;
import it.unicam.ids.rcs.model.Utente;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotificaCreazioneHackatonTest {
    private NotificaCreazioneHackaton notifica;

    @BeforeEach
    void setUp() {
        Utente mittente = new Utente();
        mittente.setNome("Giorgio");
        Utente destinatario = new Utente();
        destinatario.setNome("Giacomo");
        Hackaton hackaton = new Hackaton();
        hackaton.setNome("Hackaton 2026");

        notifica = new NotificaCreazioneHackaton(mittente, destinatario, hackaton);

    }

    @Test
    void testMessaggioPerOrganizzatore() {
        String messaggio = notifica.ottieniMessaggioPerOrganizzatore();
        assertEquals("É stato creato un nuovo Hackaton: Hackaton 2026", messaggio);
    }

    @Test
    void testMessaggioPerGiudice() {
        String messaggio = notifica.ottieniMessaggioPerGiudice();
        assertEquals("É stato creato un nuovo Hackaton: Hackaton 2026", messaggio);
    }

    @Test
    void testMessaggioPerMentore() {
        String messaggio = notifica.ottieniMessaggioPerMentore();
        assertEquals("É stato creato un nuovo Hackaton: Hackaton 2026", messaggio);
    }
    @Test
    void testMessaggioPerMembroDelloStaffNonSupportato(){
        assertThrows(ExecutionControl.NotImplementedException.class,() -> notifica.ottieniMessaggioPerMembroDelloStaff());
    }
    @Test
    void testMessagioPerMembroDelTeamNonsupportato(){
        assertThrows(ExecutionControl.NotImplementedException.class,() -> notifica.ottieniMessaggioPerMembroDelTeam());

    }
    @Test
    void testMessaggioPerUtenteNonSupportato(){
        assertThrows(ExecutionControl.NotImplementedException.class,() -> notifica.ottieniMessaggioPerUtente());

    }
}