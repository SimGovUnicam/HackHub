/*
 * MIT License
 *
 * Copyright (c) 2026 Riccardo Farina, Cristian Bellesi, Simone Governatori
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

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Questa classe rappresenta un hackaton, ovvero un evento competitivo di progettazione
 * e codifica di un sistema informatico
 */
@Entity
public class Hackaton {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private int dimensioneMassimaTeam;
    private String regolamento;
    private LocalDate scadenzaIscrizioni;
    private LocalDateTime inizio;
    private LocalDateTime fine;
    private String luogo;
    private double premio;
    @OneToOne(targetEntity = Utente.class, fetch = FetchType.EAGER)
    private Utente organizzatore;
    @OneToOne(targetEntity = Utente.class, fetch = FetchType.EAGER)
    private Utente giudice;
    @OneToMany(targetEntity = Utente.class, fetch = FetchType.LAZY)
    private List<Utente> mentori;
    @OneToMany(targetEntity = Team.class, fetch = FetchType.LAZY)
    private List<Team> iscritti;
    @OneToOne(targetEntity = Team.class, fetch = FetchType.EAGER)
    private Team vincitore;
    /**
     * <code>True</code> se l'hackaton è annullato, <code>false</code> altrimenti
     */
    private boolean annullato;

    public Hackaton() {
        this.mentori = new ArrayList<>();
        this.iscritti = new ArrayList<>();
    }

    public Hackaton(String nome, int dimensioneMassimaTeam, String regolamento, LocalDate scadenzaIscrizioni, LocalDateTime inizio,
                    LocalDateTime fine, String luogo, double premio, Utente giudice, List<Utente> mentori) {
        this.nome = nome;
        this.dimensioneMassimaTeam = dimensioneMassimaTeam;
        this.regolamento = regolamento;
        this.scadenzaIscrizioni = scadenzaIscrizioni;
        this.inizio = inizio;
        this.fine = fine;
        this.luogo = luogo;
        this.premio = premio;
        this.giudice = giudice;
        this.mentori = mentori;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        if (!this.getMentori().contains(mentore)) {
            this.getMentori().add(mentore);
        }
    }

    public boolean rimuoviMentore(Utente utente) {
        return this.getMentori().remove(utente);
    }

    public List<Team> getIscritti() {
        return this.iscritti;
    }

    public void setIscritti(List<Team> iscritti) {
        this.iscritti = iscritti;
    }

    public Team getVincitore() {
        return this.vincitore;
    }

    public void setVincitore(Team vincitore) {
        this.vincitore = vincitore;
    }

    public boolean isAnnullato() {
        return this.annullato;
    }

    public void setAnnullato(boolean annullato) {
        this.annullato = annullato;
    }

    /**
     * Determina se l'utente fornito è l'organizzatore di questo hackaton
     *
     * @param utente L'utente da controllare
     * @return <code>True</code> se l'utente è l'organizzatore di questo hackaton
     */
    public boolean isOrganizzatore(Utente utente) {
        return utente.equals(this.getOrganizzatore());
    }

    /**
     * Determina se l'utente fornito è l'giudice di questo hackaton
     *
     * @param utente L'utente da controllare
     * @return <code>True</code> se l'utente è l'giudice di questo hackaton
     */
    public boolean isGiudice(Utente utente) {
        return utente.equals(this.getGiudice());
    }

    /**
     * Determina se l'utente fornito è un mentore di questo hackaton
     *
     * @param utente L'utente da controllare
     * @return <code>True</code> se l'utente è un mentore di questo hackaton
     */
    public boolean isMentore(Utente utente) {
        return this.getMentori().contains(utente);
    }

    /**
     * Determina se l'utente fornito è un mentore di questo hackaton
     *
     * @param utente L'utente da controllare
     * @return <code>True</code> se l'utente è un mentore di questo hackaton
     */
    public boolean isMembroDelloStaff(Utente utente) {
        return this.isOrganizzatore(utente) ||
                this.isGiudice(utente) ||
                this.isMentore(utente);
    }

    /**
     * Determina se l'utente fornito è un mentore di questo hackaton
     *
     * @param utente L'utente da controllare
     * @return <code>True</code> se l'utente è un mentore di questo hackaton
     */
    public boolean isMembroDelTeamIscritto(Utente utente) {
        List<Team> teams = this.getIscritti();
        for (Team team : teams) {
            if (team.getMembri().contains(utente)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Hackaton hackaton)) return false;

        return Objects.equals(id, hackaton.id) && getNome().equals(hackaton.getNome());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + getNome().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Hackaton {" +
                "nome = '" + nome + "'" +
                '}';
    }

    /**
     * Metodo di utilità per copiare questo oggetto in una nuova istanza, identica
     *
     * @return Un'istanza di Hackaton identica a questa
     */
    public Hackaton copia() {
        Hackaton copia = new Hackaton();
        copia.setNome(this.getNome());
        copia.setOrganizzatore(this.getOrganizzatore());
        copia.setGiudice(this.getGiudice());
        copia.setMentori(this.getMentori());
        copia.setIscritti(this.getIscritti());
        copia.setDimensioneMassimaTeam(this.getDimensioneMassimaTeam());
        copia.setScadenzaIscrizioni(this.getScadenzaIscrizioni());
        copia.setInizio(this.getInizio());
        copia.setFine(this.getFine());
        copia.setLuogo(this.getLuogo());
        copia.setPremio(this.getPremio());
        return copia;
    }
}
