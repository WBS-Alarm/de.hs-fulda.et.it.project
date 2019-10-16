package de.hsfulda.et.wbs.security.entity;

import de.hsfulda.et.wbs.core.data.AuthorityData;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "AUTHORITY")
public class Authority implements AuthorityData {

    @Id
    private Long id;

    @Size(max = 10)
    private String code;

    @Size(max = 40)
    private String bezeichnung;

    private boolean aktiv;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }
}
