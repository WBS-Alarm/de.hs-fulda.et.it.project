package de.hsfulda.et.wbs.security.entity;

import de.hsfulda.et.wbs.core.data.TokenConfigData;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TOKENCONFIG")
public class TokenConfig implements TokenConfigData {

    @Id
    @Size(max = 15)
    private String issuer;

    private int expiration;

    private int clock;

    @Size(max = 30)
    private String secret;

    @Override
    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    @Override
    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    @Override
    public int getClock() {
        return clock;
    }

    public void setClock(int clock) {
        this.clock = clock;
    }

    @Override
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
