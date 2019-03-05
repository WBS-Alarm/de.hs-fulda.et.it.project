package de.hsfulda.et.wbs.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "TRAEGER")
public class Traeger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 60)
    private String name;

    @OneToMany(mappedBy = "traeger", fetch = FetchType.LAZY)
    private List<Zielort> zielorte;

    @OneToMany(mappedBy = "traeger", fetch = FetchType.LAZY)
    private List<Kategorie> kategorien;

    @OneToMany(mappedBy = "traeger", fetch = FetchType.LAZY)
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
        return zielorte;
    }

    public void setZielorte(List<Zielort> zielorte) {
        this.zielorte = zielorte;
    }

    public List<Kategorie> getKategorien() {
        return kategorien;
    }

    public void setKategorien(List<Kategorie> kategorien) {
        this.kategorien = kategorien;
    }

    public List<Benutzer> getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(List<Benutzer> benutzer) {
        this.benutzer = benutzer;
    }

    public void addBenutzer(Benutzer b) {
        if (getBenutzer() == null) {
            benutzer = new ArrayList<>();
        }
        if(!benutzer.contains(b)) {
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
