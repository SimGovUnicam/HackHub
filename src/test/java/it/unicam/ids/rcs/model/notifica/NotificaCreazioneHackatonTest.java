package it.unicam.ids.rcs.model.notifica;

import it.unicam.ids.rcs.model.Hackaton;
import it.unicam.ids.rcs.model.Utente;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class NotificaCreazioneHackatonTest {
    private NotificaCreazioneHackaton notifica;

    @BeforeEach
    void setUp() {
        Utente mittente = new Utente();
        Utente destinatario = new Utente();
        Hackaton hackaton = new Hackaton();
        hackaton.setNome("Hackaton 2026");

        notifica = new NotificaCreazioneHackaton(mittente, destinatario, hackaton);

    }

    @Test
    void testMessaggioPerOrganizzatore() {
        String messaggio = notifica.ottieniMessaggioPerOrganizzatore();
        assertNotNull(notifica.getMessaggio());
        assertNotNull(messaggio);
        assertEquals(notifica.getMessaggio(), messaggio);
    }

    @Test
    void testMessaggioPerGiudice() {
        String messaggio = notifica.ottieniMessaggioPerGiudice();
        assertNotNull(notifica.getMessaggio());
        assertNotNull(messaggio);
        assertEquals(notifica.getMessaggio(), messaggio);
    }

    @Test
    void testMessaggioPerMentore() {
        String messaggio = notifica.ottieniMessaggioPerMentore();
        assertNotNull(notifica.getMessaggio());
        assertNotNull(messaggio);
        assertEquals(notifica.getMessaggio(), messaggio);
    }

    @Test
    void testMessaggioPerMembroDelloStaffNonSupportato() {
        assertThrows(ExecutionControl.NotImplementedException.class, () -> notifica.ottieniMessaggioPerMembroDelloStaff());
    }

    @Test
    void testMessaggioPerMembroDelTeamNonsupportato() {
        assertThrows(ExecutionControl.NotImplementedException.class, () -> notifica.ottieniMessaggioPerMembroDelTeam());

    }

    @Test
    void testMessaggioPerUtenteNonSupportato() {
        assertThrows(ExecutionControl.NotImplementedException.class, () -> notifica.ottieniMessaggioPerUtente());

    }
}