package de.hsfulda.et.wbs.entity;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "KATEGORIEN")
public class Kategorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 40)
    private String name;

    private boolean aktiv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAEGER_ID")
    private Traeger traeger;

    @ManyToMany(mappedBy = "kategorien", fetch = FetchType.LAZY)
    private List<Groesse> groessen;

    protected Kategorie() {
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

    public Traeger getTraeger() {
        return traeger;
    }

    public void setTraeger(Traeger traeger) {
        this.traeger = traeger;
    }

    public List<Groesse> getGroessen() {
        return groessen;
    }

    public void setGroessen(List<Groesse> groessen) {
        this.groessen = groessen;
    }


    public static KategorieBuilder builder() {
        return new KategorieBuilder();
    }

    static Kategorie makeTemplate(Kategorie zielort) {
        Kategorie templated = new Kategorie();
        templated.setName(zielort.getName());
        templated.setTraeger(zielort.getTraeger());
        templated.setAktiv(zielort.isAktiv());
        return templated;
    }

    public static class KategorieBuilder {

        private final Kategorie template;


        private KategorieBuilder() {
            template = new Kategorie();
        }

        public KategorieBuilder name(String name) {
            template.setName(name);
            return this;
        }

        public KategorieBuilder aktiv(boolean aktiv) {
            template.setAktiv(aktiv);
            return this;
        }

        public Kategorie build() {
            return makeTemplate(template);
        }
    }
}
