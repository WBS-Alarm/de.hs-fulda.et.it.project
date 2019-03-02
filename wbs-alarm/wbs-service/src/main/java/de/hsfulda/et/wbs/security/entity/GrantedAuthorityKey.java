package de.hsfulda.et.wbs.security.entity;


import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class GrantedAuthorityKey implements Serializable {

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "AUTHORITY_ID")
    private Long authorityId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }
}
