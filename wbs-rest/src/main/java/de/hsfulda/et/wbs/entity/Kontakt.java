package de.hsfulda.et.wbs.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Getter
@Setter
@Entity
@Table(name = "KONTAKTE")
public class Kontakt {

    @Id
    private BigInteger id;
    private String name;
    private String mail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZIELORT_ID")
    private Zielort zielort;

    protected Kontakt() {}

}
