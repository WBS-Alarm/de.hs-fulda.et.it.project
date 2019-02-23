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
}
