package de.hsfulda.et.wbs.entity;


import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TRAEGER")
public class Traeger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 60)
    private String name;

    @OneToMany(mappedBy = "traeger", fetch = FetchType.LAZY)
    @Where(clause = "aktiv = true")
    private List<Zielort> zielorte;

    @OneToMany(mappedBy = "traeger", fetch = FetchType.LAZY)
    private List<Kategorie> kategorien;

    @OneToMany(mappedBy = "traeger", fetch = FetchType.LAZY)
    @Where(clause = "aktiv = true")
    private List<Benutzer> benutzer;

    public Traeger() {
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

    public List<Zielort> getZielorte() {
        if (zielorte == null) {
            zielorte = new ArrayList<>();
        }
        return zielorte;
    }

    public void setZielorte(List<Zielort> zielorte) {
        this.zielorte = zielorte;
    }

    public void addZielort(Zielort z) {
        if (zielorte == null) {
            zielorte = new ArrayList<>();
        }
        if (!zielorte.contains(z)) {
            z.setTraeger(this);
            zielorte.add(z);
        }
    }

    public List<Kategorie> getKategorien() {
        return kategorien;
    }

    public void setKategorien(List<Kategorie> kategorien) {
        this.kategorien = kategorien;
    }

    public List<Benutzer> getBenutzer() {
        if (benutzer == null) {
            benutzer = new ArrayList<>();
        }
        return benutzer;
    }

    public void setBenutzer(List<Benutzer> benutzer) {
        this.benutzer = benutzer;
    }

    public void addBenutzer(Benutzer b) {
        if (benutzer == null) {
            benutzer = new ArrayList<>();
        }
        if (!benutzer.contains(b)) {
            b.setTraeger(this);
            benutzer.add(b);
        }
    }

    public static TraegerBuilder builder() {
        return new TraegerBuilder();
    }

    static Traeger makeTemplate(Traeger traeger) {
        Traeger templated = new Traeger();
        templated.setName(traeger.getName());
        return templated;
    }

    public static class TraegerBuilder {

        private final Traeger template;


        private TraegerBuilder() {
            template = new Traeger();
        }

        public TraegerBuilder name(String name) {
            template.setName(name);
            return this;
        }

        public Traeger build() {
            return makeTemplate(template);
        }

    }
}
