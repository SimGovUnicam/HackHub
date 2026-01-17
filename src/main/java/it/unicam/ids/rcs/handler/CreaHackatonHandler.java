package it.unicam.ids.rcs.handler;

import it.unicam.ids.rcs.controller.HackatonController;
import it.unicam.ids.rcs.model.Hackaton;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 */
public class CreaHackatonHandler {

    private HackatonController hackatonController;

    public CreaHackatonHandler() {}

    private void setHackatonController(HackatonController hackatonController) {
        this.hackatonController = hackatonController;
    }

    /**
     *
     * @param dimensioneMassimaTeam
     * @param regolamento
     * @param scadenzaIscrizioni
     * @param inizio
     * @param fine
     * @param luogo
     * @param premio
     * @return
     */
    public boolean creaHackaton(int dimensioneMassimaTeam, String regolamento, LocalDate scadenzaIscrizioni,
                             LocalDateTime inizio, LocalDateTime fine, String luogo, Double premio)
    {
        this.setHackatonController(new HackatonController());
        return this.hackatonController.creaHackaton(dimensioneMassimaTeam, regolamento, scadenzaIscrizioni,
            inizio, fine, luogo, premio);
    }

    /**
     *
     * @param email
     * @return
     */
    public boolean assegnaGiudice(String email){
        return email != null && !email.isEmpty() && this.hackatonController.assegnaGiudice(email);
    }

    /**
     *
     * @param email
     * @return
     */
    public boolean aggiungiMentore(String email){
        return email != null && !email.isEmpty() && this.hackatonController.aggiungiMentore(email);
    }

    /**
     *
     * @param emailCreatore
     * @return
     */
    public Hackaton confermaCreazione(String emailCreatore){
        return this.hackatonController.registraHackaton(emailCreatore);
    }
}
