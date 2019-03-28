package de.hsfulda.et.wbs.core.data;

public interface BenutzerData extends BenutzerDto {

    Long getId();
    String getUsername();
    String getPassword();
    String getToken();
    TraegerData getTraeger();
    Boolean getAktiv();
}
