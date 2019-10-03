package de.hsfulda.et.wbs.security.entity;

import de.hsfulda.et.wbs.core.data.AuthorityData;
import de.hsfulda.et.wbs.core.data.GrantedAuthorityData;

import javax.persistence.*;

@Entity
@Table(name = "GRANTED_AUTHORITY")
public class GrantedAuthority implements GrantedAuthorityData {

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

    @Override
    public Long getUserId() {
        return pk.getUserId();
    }

    public void setUserId(Long userId) {
        if (pk == null) {
            pk = new GrantedAuthorityKey();
        }
        pk.setUserId(userId);
    }

    @Override
    public Long getAuthorityId() {
        return pk.getAuthorityId();
    }

    public void setAuthorityId(Long authorityId) {
        if (pk == null) {
            pk = new GrantedAuthorityKey();
        }
        pk.setAuthorityId(authorityId);
    }

    @Override
    public AuthorityData getGroup() {
        return group;
    }

    public void setGroup(Authority group) {
        this.group = group;
    }
}
