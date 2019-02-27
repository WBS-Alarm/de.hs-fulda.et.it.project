package de.hsfulda.et.wbs.entity;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "GROESSEN")
public class Groesse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 20)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "KATEGORIEN_GROESSEN",
            joinColumns = @JoinColumn(name = "GROESSE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "KATEGORIE_ID", referencedColumnName = "ID"))
    private List<Kategorie> kategorien;

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

    public List<Kategorie> getKategorien() {
        return kategorien;
    }

    public void setKategorien(List<Kategorie> kategorien) {
        this.kategorien = kategorien;
    }
}
