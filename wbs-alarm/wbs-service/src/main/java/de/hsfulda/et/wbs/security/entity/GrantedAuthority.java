package de.hsfulda.et.wbs.security.entity;


import javax.persistence.*;

@Entity
@Table(name = "GRANTED_AUTHORITY")
public class GrantedAuthority {

    @EmbeddedId
    private GrantedAuthorityKey pk;

    @ManyToOne
    @JoinColumn(name = "AUTHORITY_ID", insertable = false, updatable = false, nullable = false)
    private Authority group;

    public GrantedAuthorityKey getPk() {
        return pk;
    }

    public void setPk(GrantedAuthorityKey pk) {
        this.pk = pk;
    }

    public Long getUserId() {
        return pk.getUserId();
    }

    public void setUserId(Long userId) {
        pk.setUserId(userId);
    }

    public Long getAuthorityId() {
        return pk.getAuthorityId();
    }

    public void setAuthorityId(Long authorityId) {
        pk.setAuthorityId(authorityId);
    }

    public Authority getGroup() {
        return group;
    }

    public void setGroup(Authority group) {
        this.group = group;
    }
}
