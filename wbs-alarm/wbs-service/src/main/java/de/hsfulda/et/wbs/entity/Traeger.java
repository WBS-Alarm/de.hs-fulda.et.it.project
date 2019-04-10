package de.hsfulda.et.wbs.entity;


import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.core.data.KategorieData;
import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.core.data.ZielortData;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "TRAEGER")
public class Traeger implements TraegerData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 60)
    private String name;

    @OneToMany(mappedBy = "traeger", fetch = FetchType.LAZY)
    @Where(clause = "aktiv = true")
    private List<Zielort> zielorte;

    @OneToMany(mappedBy = "traeger", fetch = FetchType.LAZY)
    @Where(clause = "aktiv = true")
    private List<Kategorie> kategorien;

    @OneToMany(mappedBy = "traeger", fetch = FetchType.LAZY)
    @Where(clause = "aktiv = true")
    private List<Benutzer> benutzer;

    public Traeger() {
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
    public List<ZielortData> getZielorte() {
        if (zielorte == null) {
            zielorte = new ArrayList<>();
        }
        return zielorte.stream().map(z -> (ZielortData) z).collect(Collectors.toList());
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

    @Override
    public List<KategorieData> getKategorien() {
        if (kategorien == null) {
            kategorien = new ArrayList<>();
        }
        return kategorien.stream().map(k -> (KategorieData) k).collect(Collectors.toList());
    }

    public void setKategorien(List<Kategorie> kategorien) {
        this.kategorien = kategorien;
    }

    public void addKategorie(Kategorie z) {
        if (kategorien == null) {
            kategorien = new ArrayList<>();
        }
        if (!kategorien.contains(z)) {
            z.setTraeger(this);
            kategorien.add(z);
        }
    }

    @Override
    public List<BenutzerData> getBenutzer() {
        if (benutzer == null) {
            benutzer = new ArrayList<>();
        }
        return benutzer.stream().map(b -> (BenutzerData) b).collect(Collectors.toList());
    }

    public void setBenutzer(List<Benutzer> benutzer) {
        this.benutzer = benutzer;
    }

    public void addBenutzer(BenutzerData b) {
        Benutzer cb = (Benutzer) b;
        if (benutzer == null) {
            benutzer = new ArrayList<>();
        }
        if (!benutzer.contains(b)) {
            cb.setTraeger(this);
            benutzer.add(cb);
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
