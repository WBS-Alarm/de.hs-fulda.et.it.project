package de.hsfulda.et.wbs.http.resource.dto;

import de.hsfulda.et.wbs.core.data.BestandCreateDto;

public class BestandCreateDtoImpl implements BestandCreateDto {

    private Long anzahl;
    private Long groesseId;

    @Override
    public Long getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(Long anzahl) {
        this.anzahl = anzahl;
    }

    @Override
    public Long getGroesseId() {
        return groesseId;
    }

    public void setGroesseId(Long groesseId) {
        this.groesseId = groesseId;
    }

}
