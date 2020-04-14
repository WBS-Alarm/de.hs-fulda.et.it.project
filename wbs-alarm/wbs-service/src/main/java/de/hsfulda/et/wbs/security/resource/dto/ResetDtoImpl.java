package de.hsfulda.et.wbs.security.resource.dto;

import de.hsfulda.et.wbs.core.dto.ResetDto;

public class ResetDtoImpl implements ResetDto {

    private String token;
    private String password;

    @Override
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
