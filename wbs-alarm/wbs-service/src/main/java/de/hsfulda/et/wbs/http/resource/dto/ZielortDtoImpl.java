package de.hsfulda.et.wbs.http.resource.dto;

import de.hsfulda.et.wbs.core.dto.ZielortDto;

public class ZielortDtoImpl implements ZielortDto {

    private String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
