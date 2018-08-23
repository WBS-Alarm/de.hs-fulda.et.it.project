package de.hsfulda.et.wbs.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "BESTAENDE")
public class Bestand {

    @Id
    private BigInteger id;
    private BigInteger anzahl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROESSE_ID")
    private Groesse groesse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KATEGORIE_ID")
    private Kategorie kategorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZIELORT_ID")
    private Zielort zielort;

    protected Bestand() {}

}
