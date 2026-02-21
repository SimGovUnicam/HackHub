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

package it.unicam.ids.rcs.handler.richiesta;

import org.springframework.boot.jackson.JacksonComponent;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.function.Function;

@JacksonComponent
public class RichiestaCreaHackaton {
    private String nome;
    private int dimensioneMassimaTeam;
    private String regolamento;
    private LocalDate scadenzaIscrizioni;
    private LocalDateTime inizio;
    private LocalDateTime fine;
    private String luogo;
    private Double premio;

    public RichiestaCreaHackaton() {
    }

    public RichiestaCreaHackaton(String nome, int dimensioneMassimaTeam, String regolamento, LocalDate scadenzaIscrizioni, LocalDateTime inizio, LocalDateTime fine, String luogo, Double premio) {
        this.nome = nome;
        this.dimensioneMassimaTeam = dimensioneMassimaTeam;
        this.regolamento = regolamento;
        this.scadenzaIscrizioni = scadenzaIscrizioni;
        this.inizio = inizio;
        this.fine = fine;
        this.luogo = luogo;
        this.premio = premio;
    }

    public String getNome() {
        return this.nome;
    }

    public int getDimensioneMassimaTeam() {
        return this.dimensioneMassimaTeam;
    }

    public String getRegolamento() {
        return this.regolamento;
    }

    public LocalDate getScadenzaIscrizioni() {
        return this.scadenzaIscrizioni;
    }

    public LocalDateTime getInizio() {
        return this.inizio;
    }

    public LocalDateTime getFine() {
        return this.fine;
    }

    public String getLuogo() {
        return this.luogo;
    }

    public Double getPremio() {
        return this.premio;
    }

    @Override
    public String toString() {
        return "RichiestaCreaHackaton {" +
                "nome='" + nome + '\'' +
                ", dimensioneMassimaTeam=" + dimensioneMassimaTeam +
                ", regolamento='" + regolamento + '\'' +
                ", scadenzaIscrizioni=" + scadenzaIscrizioni +
                ", inizio=" + inizio +
                ", fine=" + fine +
                ", luogo='" + luogo + '\'' +
                ", premio=" + premio +
                '}';
    }

    public static class Deserializer extends ValueDeserializer<RichiestaCreaHackaton> {
        /**
         * Dato un albero JSON, controlla se la proprietà fornita esiste al suo
         * interno, se sì ne legge il valore
         *
         * @param tree      L'albero JSON
         * @param proprieta Il nome della proprietà
         * @param lettore   La funzione che permettere di leggere il dato dal nodo,
         *                  se presente
         * @param <R>       Il tipo di dato restituito
         * @return Il valore della proprietà indicata all'interno dell'albero JSON
         */
        private static <R> R controllaELeggi(JsonNode tree, String proprieta, Function<JsonNode, R> lettore) {
            Optional<JsonNode> NodeOptional = tree.optional(proprieta);
            if (NodeOptional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campo " + proprieta + " non presente");
            }
            if (NodeOptional.get().isNull() || (NodeOptional.get().isString() && NodeOptional.get().asString().isBlank())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campo " + proprieta + " non valorizzato");
            }
            JsonNode nodo = NodeOptional.get();
            return lettore.apply(nodo);
        }

        @Override
        public RichiestaCreaHackaton deserialize(JsonParser parser, DeserializationContext context) throws JacksonException {
            JsonNode tree = parser.readValueAsTree();
            String nome = controllaELeggi(tree, "nome", JsonNode::asString);
            int dimensioneMassimaTeam = controllaELeggi(tree, "dimensioneMassimaTeam", JsonNode::asInt);
            String regolamento = controllaELeggi(tree, "regolamento", JsonNode::asString);
            String scadenzaIscrizioniString = controllaELeggi(tree, "scadenzaIscrizioni", JsonNode::asString);
            String inizioString = controllaELeggi(tree, "inizio", JsonNode::asString);
            String fineString = controllaELeggi(tree, "fine", JsonNode::asString);
            String luogo = controllaELeggi(tree, "luogo", JsonNode::asString);
            double premio = controllaELeggi(tree, "premio", JsonNode::asDouble);

            try {
                LocalDate scadenzaIscrizioni = LocalDate.parse(scadenzaIscrizioniString, DateTimeFormatter.ISO_DATE);
                LocalDateTime inizio = LocalDateTime.parse(inizioString, DateTimeFormatter.ISO_DATE_TIME);
                LocalDateTime fine = LocalDateTime.parse(fineString, DateTimeFormatter.ISO_DATE_TIME);
                return new RichiestaCreaHackaton(nome, dimensioneMassimaTeam, regolamento, scadenzaIscrizioni, inizio, fine, luogo, premio);
            } catch (DateTimeParseException exception) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato data errato: " + exception.getMessage());
            }
        }

    }
}