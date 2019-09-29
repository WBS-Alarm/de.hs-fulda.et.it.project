package de.hsfulda.et.wbs.security.resource.dto;

import de.hsfulda.et.wbs.core.dto.BenutzerCreateDto;

public class BenutzerCreateDtoImpl implements BenutzerCreateDto {

    private String username;
    private String password;
    private String mail;
    private Boolean einkaeufer;

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
