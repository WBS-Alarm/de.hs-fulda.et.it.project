package de.hsfulda.et.wbs.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "TRAEGER")
public class Traeger {

    @Id
    private BigInteger id;
    private String name;

    @OneToMany(mappedBy = "traeger", fetch = FetchType.LAZY)
    private List<Zielort> zielorte;

    @OneToMany(mappedBy = "traeger", fetch = FetchType.LAZY)
    private List<Kategorie> kategorien;

    @ManyToMany(mappedBy = "traeger", fetch = FetchType.LAZY)
    private List<Benutzer> benutzer;

    protected Traeger() {
    }

}
