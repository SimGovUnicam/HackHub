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

import it.unicam.ids.rcs.util.notificatore.ModalitaNotifica;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Questa classe rappresenta un utente del sistema
 */
@Entity
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nome;
    private String cognome;
    @ManyToOne(targetEntity = Team.class)
    @JoinColumn()
    private Team team;
    /**
     * <code>True</code> se l'utente è loggato, <code>false</code> altrimenti
     */
    private boolean accessoEffettuato;

    /**
     * Contiene tutte le modalità con cui l'utente vuole essere notificato
     * e.g. Email,SMS
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "utente_modalita_notifica",
            joinColumns = @JoinColumn(name = "utente_id")
    )
    @Column(name = "modalita")
    private Set<ModalitaNotifica> modalitaNotifica = new HashSet<>();

    public Utente() {
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Team getTeam() {
        return team;
    }

    /**
     * Imposta il team di questo utente e, contestualmente, imposta questo utente
     * come membro del team
     * @param team Il team di cui questo membro fa parte
     */
    public void setTeam(Team team) {
        this.team = team;
        if (team.getMembri() != null && !team.getMembri().contains(this)) { // Per evitare riferimenti circolari
            team.aggiungiMembro(this);
        }
    }

    /**
     * Determina se l'utente ha effettuato l'accesso
     *
     * @return <code>True</code> se l'utente è loggato, <code>false</code> altrimenti}
     */
    public boolean isAccessoEffettuato() {
        return this.accessoEffettuato;
    }

    public void setAccessoEffettuato(boolean accessoEffettuato) {
        this.accessoEffettuato = accessoEffettuato;
    }

    public Set<ModalitaNotifica> getModalitaNotifica() {
        return new HashSet<>(this.modalitaNotifica);
    }

    private void setModalitaNotifica(Set<ModalitaNotifica> modalitaNotifica) {
        this.modalitaNotifica = new HashSet<>(modalitaNotifica);
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Utente utente)) return false;

        return Objects.equals(id, utente.id) && getEmail().equals(utente.getEmail());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + getEmail().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Utente {" +
                "email = '" + email + "'" +
                '}';
    }
}
