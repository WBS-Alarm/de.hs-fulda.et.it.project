package de.hsfulda.et.wbs.core.data;

import de.hsfulda.et.wbs.core.dto.BenutzerDto;

import java.time.LocalDateTime;

public interface BenutzerData extends BenutzerDto {

    Long getId();

    String getUsername();

    String getPassword();

    TraegerData getTraeger();

    Boolean getAktiv();

    String getToken();

    LocalDateTime getValid();
}
