package de.hsfulda.et.wbs.entity;


import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "GROESSEN")
public class Groesse {

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    public Kategorie getKategorie() {
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
        templated.setName(groesse.getName());
        templated.setKategorie(groesse.getKategorie());
        templated.setAktiv(groesse.isAktiv());
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
