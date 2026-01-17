package it.unicam.ids.rcs.controller;

import it.unicam.ids.rcs.model.Hackaton;
import it.unicam.ids.rcs.repository.HackatonRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HackatonController {
    private Hackaton hackaton;
    private HackatonRepository hackatonRepository;
    private UtenteController utenteController;

    public HackatonController() {}

    public boolean creaHackaton(int dimensioneMassimaTeam, String regolamento, LocalDate scadenzaIscrizioni,
                        LocalDateTime inizio, LocalDateTime fine, String luogo, Double premio){
        //TODO
        return true;
    }

    public boolean assegnaGiudice(String email){
        //TODO
        return true;
    }

    public boolean aggiungiMentore(String email){
        //TODO
        return true;
    }

    public Hackaton registraHackaton(String emailCreatore){
        //TODO
        return null;
    }
}
