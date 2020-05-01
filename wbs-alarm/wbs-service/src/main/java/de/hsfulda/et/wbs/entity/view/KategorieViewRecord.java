package de.hsfulda.et.wbs.entity.view;

import de.hsfulda.et.wbs.core.data.KategorieViewData;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("select vb.* from V_KATEGORIEN vb")
public class KategorieViewRecord implements KategorieViewData {

    @Id
    @Column(name = "ID")
    private String ID;
    @Column(name = "traeger_id", updatable = false, insertable = false, nullable = false)
    private Long traegerId;
    @Column(name = "traeger", updatable = false, insertable = false, nullable = false)
    private String traeger;
    @Column(name = "kategorie", updatable = false, insertable = false, nullable = false)
    private String kategorie;
    @Column(name = "anzahl", updatable = false, insertable = false, nullable = false)
    private Long anzahl;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Long getTraegerId() {
        return traegerId;
    }

    public void setTraegerId(Long traegerId) {
        this.traegerId = traegerId;
    }

    @Override
    public String getTraeger() {
        return traeger;
    }

    public void setTraeger(String traeger) {
        this.traeger = traeger;
    }

    @Override
    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    @Override
    public Long getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(Long anzahl) {
        this.anzahl = anzahl;
    }
}
