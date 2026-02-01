package it.unicam.ids.rcs.handler;

import it.unicam.ids.rcs.controller.HackatonController;
import it.unicam.ids.rcs.controller.UtenteController;
import it.unicam.ids.rcs.model.Hackaton;
import it.unicam.ids.rcs.model.Utente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 */
public class ModificaHackatonHandler {

    private HackatonController hackatonController;

    public ModificaHackatonHandler(){
    }

    private void setHackatonController(HackatonController hackatonController) {
        this.hackatonController = hackatonController;
    }

    /**
     *
     * @return
     */
    public List<Hackaton> modificaHackaton(){
        Utente organizzatoreHackaton = UtenteController.getUtenteInSessione();
        this.setHackatonController(new HackatonController());
        List<Hackaton> hackatonModificabili = this.hackatonController.getListaHackatonModificabili(organizzatoreHackaton);
        if(hackatonModificabili.isEmpty())
            return null;
        return hackatonModificabili;
    }

    /**
     *
     * @param nomeHackaton
     * @return
     */
    public Hackaton selezionaHackaton(String nomeHackaton){
        return this.hackatonController.selezionaHackaton(nomeHackaton);
    }

    /**
     *
     * @param nome
     * @param dimensioneMassimaTeam
     * @param regolamento
     * @param scadenzaIscrizioni
     * @param inizio
     * @param fine
     * @param luogo
     * @param premio
     * @param emailGiudice
     * @param emailMentori
     * @return
     */
    public Hackaton confermaModifica(String nome, int dimensioneMassimaTeam, String regolamento, LocalDate scadenzaIscrizioni,
                                     LocalDateTime inizio, LocalDateTime fine, String luogo, double premio, String emailGiudice, List<String> emailMentori){
        return this.hackatonController.confermaModifica(nome,dimensioneMassimaTeam, regolamento, scadenzaIscrizioni,inizio,fine,luogo,premio,emailGiudice,emailMentori);
    }
}
