/*
 * MIT License
 *
 * Copyright (c) 2026 Simone Governatori
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package it.unicam.ids.rcs.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Questa classe rappresenta un hackaton, ovvero un evento competitivo di progettazione
 * e codifica di un sistema informatico
 */
public class Hackaton {
    private int dimensioneMassimaTeam;
    private String regolamento;
    private LocalDate scadenzaIscrizioni;
    private LocalDateTime inizio;
    private LocalDateTime fine;
    private String luogo;
    private double premio;
    private Utente organizzatore;
    private Utente giudice;
    private List<Utente> mentori;
    private List<Team> partecipanti;
    private Team vincitore;

    public Hackaton() {
    }

    /**
     * Determina se le informazioni relative all'hackaton sono valide
     *
     * @param dimensioneMassimaTeam La dimensione massima di ciascun team
     * @param scadenzaIscrizioni    La data di scadenza delle iscrizioni. Deve seguire
     *                              la data di creazione di almeno 7 giorni.
     *                              Deve precedere la data di inizio
     * @param inizio                Data di inizio dell'hackaton. Deve seguire la data di scadenza
     *                              delle iscrizioni e precedere la data di fine
     * @param fine                  Data di fine dell'hackaton. Deve seguire la data di inizio
     * @param premio                Eventuale premio in denaro
     * @return <code>True</code> se i dati sono validi, <code>false</code> altrimenti
     */
    public static boolean validaInfo(
            int dimensioneMassimaTeam,
            LocalDate scadenzaIscrizioni,
            LocalDateTime inizio,
            LocalDateTime fine,
            double premio
    ) {
        LocalDate oggi = LocalDate.now();
        return dimensioneMassimaTeam > 0 &&
                scadenzaIscrizioni.isAfter(oggi.plusDays(7)) &&
                inizio.isAfter(scadenzaIscrizioni.atStartOfDay()) &&
                inizio.isBefore(fine) &&
                premio >= 0.0;
    }

    public int getDimensioneMassimaTeam() {
        return this.dimensioneMassimaTeam;
    }

    public void setDimensioneMassimaTeam(int dimensioneMassimaTeam) {
        this.dimensioneMassimaTeam = dimensioneMassimaTeam;
    }

    public String getRegolamento() {
        return this.regolamento;
    }

    public void setRegolamento(String regolamento) {
        this.regolamento = regolamento;
    }

    public LocalDate getScadenzaIscrizioni() {
        return this.scadenzaIscrizioni;
    }

    public void setScadenzaIscrizioni(LocalDate scadenzaIscrizioni) {
        this.scadenzaIscrizioni = scadenzaIscrizioni;
    }

    public LocalDateTime getInizio() {
        return this.inizio;
    }

    public void setInizio(LocalDateTime inizio) {
        this.inizio = inizio;
    }

    public LocalDateTime getFine() {
        return this.fine;
    }

    public void setFine(LocalDateTime fine) {
        this.fine = fine;
    }

    public String getLuogo() {
        return this.luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public double getPremio() {
        return this.premio;
    }

    public void setPremio(double premio) {
        this.premio = premio;
    }

    public Utente getOrganizzatore() {
        return this.organizzatore;
    }

    public void setOrganizzatore(Utente organizzatore) {
        this.organizzatore = organizzatore;
    }

    public Utente getGiudice() {
        return this.giudice;
    }

    public void setGiudice(Utente giudice) {
        this.giudice = giudice;
    }

    public List<Utente> getMentori() {
        return this.mentori;
    }

    public void setMentori(List<Utente> mentori) {
        this.mentori = mentori;
    }

    public void aggiungiMentore(Utente mentore) {
        this.getMentori().add(mentore);
    }

    public List<Team> getPartecipanti() {
        return this.partecipanti;
    }

    public void setPartecipanti(List<Team> partecipanti) {
        this.partecipanti = partecipanti;
    }

    public Team getVincitore() {
        return this.vincitore;
    }

    public void setVincitore(Team vincitore) {
        this.vincitore = vincitore;
    }
}
