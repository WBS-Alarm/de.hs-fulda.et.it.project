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
        return positionen.stream()
                .map(p -> (PositionData) p)
                .collect(Collectors.toList());
    }

    public void addPosition(Position p) {
        if (positionen == null) {
            positionen = new ArrayList<>();
        }
        if (!positionen.contains(p)) {
            p.setTransaktion(this);
            positionen.add(p);
        }
    }

    public void setPositionen(List<Position> positionen) {
        this.positionen = positionen;
    }

    public static TransaktionBuilder builder() {
        return new TransaktionBuilder();
    }

    static Transaktion makeTemplate(Transaktion transaktion) {
        Transaktion templated = new Transaktion();
        templated.datum = transaktion.datum;
        templated.benutzer = transaktion.benutzer;
        templated.von = transaktion.von;
        templated.nach = transaktion.nach;
        templated.positionen = transaktion.positionen;
        return templated;
    }

    public static class TransaktionBuilder {

        private final Transaktion template;

        private TransaktionBuilder() {
            template = new Transaktion();
        }

        public TransaktionBuilder setDatum(LocalDateTime datum) {
            template.setDatum(datum);
            return this;
        }

        public TransaktionBuilder setBenutzer(Benutzer benutzer) {
            template.setBenutzer(benutzer);
            return this;
        }

        public TransaktionBuilder setVon(Zielort von) {
            template.setVon(von);
            return this;
        }

        public TransaktionBuilder setNach(Zielort nach) {
            template.setNach(nach);
            return this;
        }

        public TransaktionBuilder addPosition(Position position) {
            template.addPosition(position);
            return this;
        }

        public Transaktion build() {
            return makeTemplate(template);
        }
    }

}
