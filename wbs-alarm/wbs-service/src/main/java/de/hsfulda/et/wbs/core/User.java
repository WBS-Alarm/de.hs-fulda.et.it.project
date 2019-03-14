package de.hsfulda.et.wbs.core;


import com.fasterxml.jackson.annotation.JsonIgnore;
import de.hsfulda.et.wbs.entity.Benutzer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

import static java.lang.Boolean.TRUE;

public class User implements UserDetails {

    private final Benutzer benutzer;
    private Collection<SimpleGrantedAuthority> authorities;

    private User() {
        benutzer = new Benutzer();
    }

    public User(Benutzer benutzer) {
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

    public Long getId() {
        return benutzer.getId();
    }

    @Override
    public String getUsername() {
        return benutzer.getUsername();
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }
}
