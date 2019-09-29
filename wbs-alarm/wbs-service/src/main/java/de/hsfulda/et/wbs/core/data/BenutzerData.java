package de.hsfulda.et.wbs.core.data;

import de.hsfulda.et.wbs.core.dto.BenutzerDto;

public interface BenutzerData extends BenutzerDto {

    Long getId();
    String getUsername();
    String getPassword();
    TraegerData getTraeger();
    Boolean getAktiv();
}
