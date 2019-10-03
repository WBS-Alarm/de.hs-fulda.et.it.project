package de.hsfulda.et.wbs.entity;

import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.core.data.ZielortData;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "ZIELORTE")
public class Zielort implements ZielortData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 60)
    private String name;

    private boolean auto;

    private boolean aktiv;

    private boolean erfasst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAEGER_ID")
    private Traeger traeger;

    @ManyToMany(mappedBy = "zielort", fetch = FetchType.LAZY)
    private List<Bestand> bestaende;

    @ManyToMany(mappedBy = "zielort", fetch = FetchType.LAZY)
    private List<Kontakt> kontakte;

    protected Zielort() {
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
    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    @Override
    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    @Override
    public boolean isErfasst() {
        return erfasst;
    }

    public void setErfasst(boolean erfasst) {
        this.erfasst = erfasst;
    }

    @Override
    public TraegerData getTraeger() {
        return traeger;
    }

    public void setTraeger(Traeger traeger) {
        this.traeger = traeger;
    }

    public List<Bestand> getBestaende() {
        return bestaende;
    }

    public void setBestaende(List<Bestand> bestaende) {
        this.bestaende = bestaende;
    }

    public void addBestand(Bestand bestand) {
        if (bestaende == null) {
            bestaende = new ArrayList<>();
        }
        if (!bestaende.contains(bestand)) {
            bestand.setZielort(this);
            bestaende.add(bestand);
        }
    }

    public List<Kontakt> getKontakte() {
        return kontakte;
    }

    public void setKontakte(List<Kontakt> kontakte) {
        this.kontakte = kontakte;
    }

    public static ZielortBuilder builder() {
        return new ZielortBuilder();
    }

    static Zielort makeTemplate(Zielort zielort) {
        Zielort templated = new Zielort();
        templated.name = zielort.name;
        templated.traeger = zielort.traeger;
        templated.aktiv = zielort.aktiv;
        return templated;
    }

    public static class ZielortBuilder {

        private final Zielort template;

        private ZielortBuilder() {
            template = new Zielort();
        }

        public ZielortBuilder name(String name) {
            template.setName(name);
            return this;
        }

        public ZielortBuilder aktiv(boolean aktiv) {
            template.setAktiv(aktiv);
            return this;
        }

        public ZielortBuilder auto(boolean auto) {
            template.setAuto(auto);
            return this;
        }

        public Zielort build() {
            return makeTemplate(template);
        }
    }

    public static List<Zielort> getStandardForNewTraeger() {
        return Arrays.asList(builder().name("Wäscherei")
                .aktiv(true)
                .auto(true)
                .build(), builder().name("Wareneingang")
                .aktiv(true)
                .auto(true)
                .build(), builder().name("Lager")
                .aktiv(true)
                .auto(true)
                .build(), builder().name("Aussonderung")
                .aktiv(true)
                .auto(true)
                .build());
    }
}
