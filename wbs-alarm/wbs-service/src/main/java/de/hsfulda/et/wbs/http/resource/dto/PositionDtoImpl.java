package de.hsfulda.et.wbs.http.resource.dto;

import de.hsfulda.et.wbs.core.dto.PositionDto;

public class PositionDtoImpl implements PositionDto {

    private Long anzahl;
    private Long groesse;

    @Override
    public Long getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(Long anzahl) {
        this.anzahl = anzahl;
    }

    @Override
    public Long getGroesse() {
        return groesse;
    }

    public void setGroesse(Long groesse) {
        this.groesse = groesse;
    }
}
