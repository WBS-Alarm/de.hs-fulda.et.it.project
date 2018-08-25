package de.hsfulda.et.wbs.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
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
        name="KATEGORIEN_GROESSEN",
        joinColumns=@JoinColumn(name="GROESSE_ID", referencedColumnName="ID"),
        inverseJoinColumns=@JoinColumn(name="KATEGORIE_ID", referencedColumnName="ID"))
    private List<Kategorie> kategorien;

    protected Groesse() {}

}
