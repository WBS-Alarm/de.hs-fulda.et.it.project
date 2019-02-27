package de.hsfulda.et.wbs.entity;


import javax.persistence.*;

@Entity
@Table(name = "POSITIONEN")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long anzahl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSAKTION_ID")
    private Transaktion transaktion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROESSE_ID")
    private Groesse groesse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KATEGORIE_ID")
    private Kategorie kategorie;

    protected Position() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(Long anzahl) {
        this.anzahl = anzahl;
    }

    public Transaktion getTransaktion() {
        return transaktion;
    }

    public void setTransaktion(Transaktion transaktion) {
        this.transaktion = transaktion;
    }

    public Groesse getGroesse() {
        return groesse;
    }

    public void setGroesse(Groesse groesse) {
        this.groesse = groesse;
    }

    public Kategorie getKategorie() {
        return kategorie;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }
}
