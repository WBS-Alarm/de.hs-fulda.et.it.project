package de.hsfulda.et.wbs.action.benutzer.impl;

import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.core.data.BenutzerGrantedAuthData;
import de.hsfulda.et.wbs.core.data.GrantedAuthorityData;
import de.hsfulda.et.wbs.core.data.TraegerData;

import java.time.LocalDateTime;
import java.util.List;

public class BenutzerGrantedAuthDataImpl implements BenutzerGrantedAuthData {

    private final BenutzerData benutzer;
    private final List<GrantedAuthorityData> authorities;

    public BenutzerGrantedAuthDataImpl(BenutzerData benutzer, List<GrantedAuthorityData> authorities) {
        this.benutzer = benutzer;
        this.authorities = authorities;
    }

    @Override
    public List<GrantedAuthorityData> getGrantedAuthorities() {
        return authorities;
    }

    @Override
    public String getMail() {
        return benutzer.getMail();
    }

    @Override
    public Boolean getEinkaeufer() {
        return benutzer.getEinkaeufer();
    }

    @Override
    public Long getId() {
        return benutzer.getId();
    }

    @Override
    public String getUsername() {
        return benutzer.getUsername();
    }

    @Override
    public String getPassword() {
        return benutzer.getPassword();
    }

    @Override
    public TraegerData getTraeger() {
        return benutzer.getTraeger();
    }

    @Override
    public Boolean getAktiv() {
        return benutzer.getAktiv();
    }

    @Override
    public String getToken() {
        return benutzer.getToken();
    }

    @Override
    public LocalDateTime getValid() {
        return benutzer.getValid();
    }
}
