package de.hsfulda.et.wbs.http.resource.dto;

import de.hsfulda.et.wbs.core.data.BestandDto;

public class BestandDtoImpl implements BestandDto {

    private Long anzahl;

    @Override
    public Long getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(Long anzahl) {
        this.anzahl = anzahl;
    }

}
