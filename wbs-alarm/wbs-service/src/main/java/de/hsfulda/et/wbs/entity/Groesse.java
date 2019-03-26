package de.hsfulda.et.wbs.entity;


import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.core.data.KategorieData;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "GROESSEN")
public class Groesse implements GroesseData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 20)
    private String name;
    private boolean aktiv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KATEGORIE_ID")
    private Kategorie kategorie;

    protected Groesse() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    @Override
    public KategorieData getKategorie() {
        return kategorie;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }

    public static GroesseBuilder builder() {
        return new GroesseBuilder();
    }

    static Groesse makeTemplate(Groesse groesse) {
        Groesse templated = new Groesse();
        templated.name = groesse.name;
        templated.kategorie = groesse.kategorie;
        templated.aktiv = groesse.aktiv;
        return templated;
    }

    public static class GroesseBuilder {

        private final Groesse template;


        private GroesseBuilder() {
            template = new Groesse();
        }

        public GroesseBuilder name(String name) {
            template.setName(name);
            return this;
        }

        public GroesseBuilder aktiv(boolean aktiv) {
            template.setAktiv(aktiv);
            return this;
        }

        public Groesse build() {
            return makeTemplate(template);
        }
    }
}
