package de.hsfulda.et.wbs.entity;

import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.core.data.PositionData;

import javax.persistence.*;

@Entity
@Table(name = "POSITIONEN")
public class Position implements PositionData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long anzahl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSAKTION_ID")
    private Transaktion transaktion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROESSE_ID")
    private Groesse groesse;

    protected Position() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(Long anzahl) {
        this.anzahl = anzahl;
    }

    public Transaktion getTransaktion() {
        return transaktion;
    }

    public void setTransaktion(Transaktion transaktion) {
        this.transaktion = transaktion;
    }

    @Override
    public GroesseData getGroesse() {
        return groesse;
    }

    public void setGroesse(Groesse groesse) {
        this.groesse = groesse;
    }

    public static PositionBuilder builder() {
        return new PositionBuilder();
    }

    static Position makeTemplate(Position position) {
        Position templated = new Position();
        templated.anzahl = position.anzahl;
        templated.groesse = position.groesse;
        return templated;
    }

    public static class PositionBuilder {

        private final Position template;

        private PositionBuilder() {
            template = new Position();
        }

        public PositionBuilder setAnzahl(Long anzahl) {
            template.setAnzahl(anzahl);
            return this;
        }

        public PositionBuilder setGroesse(Groesse groesse) {
            template.setGroesse(groesse);
            return this;
        }

        public Position build() {
            return makeTemplate(template);
        }
    }
}
