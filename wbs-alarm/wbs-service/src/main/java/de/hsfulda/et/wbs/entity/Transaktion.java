package de.hsfulda.et.wbs.entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSAKTIONEN")
public class Transaktion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATE")
    private LocalDateTime datum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BENUTZER_ID")
    private Benutzer benutzer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VON_ZIELORT_ID")
    private Zielort von;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NACH_ZIELORT_ID")
    private Zielort nach;

    protected Transaktion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }

    public Zielort getVon() {
        return von;
    }

    public void setVon(Zielort von) {
        this.von = von;
    }

    public Zielort getNach() {
        return nach;
    }

    public void setNach(Zielort nach) {
        this.nach = nach;
    }
}
