package de.hsfulda.et.wbs.core.data;

public interface BenutzerData {

    Long getId();
    String getUsername();
    String getPassword();
    String getToken();
    String getMail();
    Boolean getEinkaeufer();
    TraegerData getTraeger();
    Boolean getAktiv();
}
