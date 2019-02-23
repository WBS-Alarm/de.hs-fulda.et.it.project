package de.hsfulda.et.wbs.entity;


import javax.persistence.*;

@Entity
@Table(name = "BESTAENDE")
public class Bestand {

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

    public Zielort getZielort() {
        return zielort;
    }

    public void setZielort(Zielort zielort) {
        this.zielort = zielort;
    }
}
