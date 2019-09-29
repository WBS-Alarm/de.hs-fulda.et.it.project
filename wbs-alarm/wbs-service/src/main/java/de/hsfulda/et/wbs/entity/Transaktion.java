package de.hsfulda.et.wbs.entity;


import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.core.data.PositionData;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.core.data.ZielortData;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "TRANSAKTIONEN")
public class Transaktion implements TransaktionData {

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

    @OneToMany(mappedBy = "transaktion", fetch = FetchType.LAZY)
    private List<Position> positionen;

    protected Transaktion() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    @Override
    public BenutzerData getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }

    @Override
    public ZielortData getVon() {
        return von;
    }

    public void setVon(Zielort von) {
        this.von = von;
    }

    @Override
    public ZielortData getNach() {
        return nach;
    }

    public void setNach(Zielort nach) {
        this.nach = nach;
    }

    @Override
    public List<PositionData> getPositionen() {
        if (positionen == null) {
            positionen = new ArrayList<>();
        }
        return positionen.stream().map(p -> (PositionData) p).collect(Collectors.toList());
    }

    public void setPositionen(List<Position> positionen) {
        this.positionen = positionen;
    }
}
