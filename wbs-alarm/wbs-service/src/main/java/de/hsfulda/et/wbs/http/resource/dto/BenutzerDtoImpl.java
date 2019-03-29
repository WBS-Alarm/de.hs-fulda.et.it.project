package de.hsfulda.et.wbs.http.resource.dto;

import de.hsfulda.et.wbs.core.data.BenutzerDto;

public class BenutzerDtoImpl implements BenutzerDto {

    private String mail;
    private Boolean einkaeufer;

    @Override
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public Boolean getEinkaeufer() {
        return einkaeufer;
    }

    public void setEinkaeufer(Boolean einkaeufer) {
        this.einkaeufer = einkaeufer;
    }
}
