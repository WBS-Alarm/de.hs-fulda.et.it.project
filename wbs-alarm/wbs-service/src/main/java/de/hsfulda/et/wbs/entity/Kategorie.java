package de.hsfulda.et.wbs.entity;


import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.core.data.KategorieData;
import de.hsfulda.et.wbs.core.data.TraegerData;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "KATEGORIEN")
public class Kategorie implements KategorieData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 40)
    private String name;

    private boolean aktiv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAEGER_ID")
    private Traeger traeger;

    @OneToMany(mappedBy = "kategorie", fetch = FetchType.LAZY)
    private List<Groesse> groessen;

    protected Kategorie() {
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
    public TraegerData getTraeger() {
        return traeger;
    }

    public void setTraeger(Traeger traeger) {
        this.traeger = traeger;
    }

    @Override
    public List<GroesseData> getGroessen() {
        if (groessen == null) {
            groessen = new ArrayList<>();
        }
        return groessen.stream().map(g -> (GroesseData) g).collect(Collectors.toList());
    }

    public void setGroessen(List<Groesse> groessen) {
        this.groessen = groessen;
    }

    public void addGroesse(Groesse groesse) {
        if (groessen == null) {
            groessen = new ArrayList<>();
        }
        if (!groessen.contains(groesse)) {
            groesse.setKategorie(this);
            groessen.add(groesse);
        }

    }


    public static KategorieBuilder builder() {
        return new KategorieBuilder();
    }

    static Kategorie makeTemplate(Kategorie kategorie) {
        Kategorie templated = new Kategorie();
        templated.name = kategorie.name;
        templated.traeger = kategorie.traeger;
        templated.aktiv = kategorie.aktiv;
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
