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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package it.unicam.ids.rcs.model;

import jakarta.persistence.*;
import org.springframework.boot.jackson.JacksonComponent;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Questa classe rappresenta un team composto da almeno due utenti
 */
@Entity
@JacksonComponent
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @OneToMany(targetEntity = Utente.class, fetch = FetchType.LAZY, mappedBy = "team")
    private List<Utente> membri;
    @OneToOne(targetEntity = Utente.class, fetch = FetchType.LAZY)
    private Utente fondatore;

    public Team() {
        this.membri = new ArrayList<>();
    }

    public Team(String nome) {
        this.nome = nome;
        this.membri = new ArrayList<>();
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Utente> getMembri() {
        return new ArrayList<>(this.membri);
    }

    public void setMembri(List<Utente> membri) {
        this.membri = membri;
    }

    public void setFondatore(Utente fondatore) {
        this.fondatore = fondatore;
    }

    /**
     * Aggiunge un utente a questo team e, contestualmente, imposta questo
     * team come team del nuovo membro
     *
     * @param nuovoMembro L'utente da aggiungere al team
     */
    public void aggiungiMembro(Utente nuovoMembro) {
        this.membri.add(nuovoMembro);
        if (nuovoMembro.getTeam() == null || !nuovoMembro.getTeam().equals(this)) {
            nuovoMembro.setTeam(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Team team)) return false;
        return Objects.equals(id, team.id) && Objects.equals(nome, team.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    public static class Serializer extends ValueSerializer<Team> {

        @Override
        public void serialize(Team team, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
            gen.writeStartObject();
            gen.writeStringProperty("nome", team.getNome());
            gen.writeStartArray("membri");
            for (Utente utente : team.getMembri()) {
                gen.writeObjectRef(utente);
            }
            gen.writeEndArray();
            gen.writeEndObject();
        }
    }
}
