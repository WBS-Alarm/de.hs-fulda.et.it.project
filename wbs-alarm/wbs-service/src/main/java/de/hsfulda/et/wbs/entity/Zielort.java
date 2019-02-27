package de.hsfulda.et.wbs.entity;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "ZIELORTE")
public class Zielort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 60)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAEGER_ID")
    private Traeger traeger;

    @ManyToMany(mappedBy = "zielort", fetch = FetchType.LAZY)
    private List<Bestand> bestaende;

    @ManyToMany(mappedBy = "zielort", fetch = FetchType.LAZY)
    private List<Kontakt> kontakte;

    protected Zielort() {
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

    public Traeger getTraeger() {
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

    public List<Kontakt> getKontakte() {
        return kontakte;
    }

    public void setKontakte(List<Kontakt> kontakte) {
        this.kontakte = kontakte;
    }
}
