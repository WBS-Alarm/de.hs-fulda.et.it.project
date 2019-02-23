package de.hsfulda.et.wbs.entity;


import javax.persistence.*;
import javax.validation.constraints.Size;
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
    private List<Zielort> zielorte;

    @OneToMany(mappedBy = "traeger", fetch = FetchType.LAZY)
    private List<Kategorie> kategorien;

    @OneToMany(mappedBy = "traeger", fetch = FetchType.LAZY)
    private List<Benutzer> benutzer;

    protected Traeger() {
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
}
