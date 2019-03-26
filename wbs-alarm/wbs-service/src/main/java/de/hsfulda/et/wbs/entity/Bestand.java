package de.hsfulda.et.wbs.entity;


import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.core.data.KategorieData;
import de.hsfulda.et.wbs.core.data.ZielortData;

import javax.persistence.*;

@Entity
@Table(name = "BESTAENDE")
public class Bestand implements BestandData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long anzahl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROESSE_ID")
    private Groesse groesse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KATEGORIE_ID")
    private Kategorie kategorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZIELORT_ID")
    private Zielort zielort;

    protected Bestand() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(Long anzahl) {
        this.anzahl = anzahl;
    }

    @Override
    public GroesseData getGroesse() {
        return groesse;
    }

    public void setGroesse(Groesse groesse) {
        this.groesse = groesse;
    }

    @Override
    public KategorieData getKategorie() {
        return kategorie;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }

    @Override
    public ZielortData getZielort() {
        return zielort;
    }

    public void setZielort(Zielort zielort) {
        this.zielort = zielort;
    }
}
