package de.hsfulda.et.wbs.http.resource.dto;

import de.hsfulda.et.wbs.core.dto.GroesseDto;

public class GroesseDtoImpl implements GroesseDto {

    private String name;
    private int bestandsgrenze;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getBestandsgrenze() {
        return bestandsgrenze;
    }

    public void setBestandsgrenze(int bestandsgrenze) {
        this.bestandsgrenze = bestandsgrenze;
    }
}
