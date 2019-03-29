package de.hsfulda.et.wbs.http.resource.dto;

import de.hsfulda.et.wbs.core.data.TraegerDto;

public class TraegerDtoImpl implements TraegerDto {

    private String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
