package de.hsfulda.et.wbs.security;


import com.fasterxml.jackson.annotation.JsonIgnore;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.entity.Benutzer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

import static java.lang.Boolean.TRUE;

public class User implements WbsUser {

    private final BenutzerData benutzer;
    private Collection<SimpleGrantedAuthority> authorities;

    private User() {
        benutzer = new Benutzer();
    }

    public User(BenutzerData benutzer) {
        this.benutzer = benutzer;
    }

    @JsonIgnore
    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = new ArrayList<>();
        }
        return authorities;
    }

    public void addAuthorities(Collection<SimpleGrantedAuthority> authorities) {
        getAuthorities().addAll(authorities);
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return benutzer.getPassword();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return TRUE.equals(benutzer.getAktiv());
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
    public BenutzerData getBenutzer() {
        return benutzer;
    }
}
