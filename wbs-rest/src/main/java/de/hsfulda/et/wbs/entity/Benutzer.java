package de.hsfulda.et.wbs.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;
@Getter
@Setter
@Entity
@Table(name = "BENUTZER")
public class Benutzer  {

    @Id
    private BigInteger id;
    @Column(name = "NAME")
    private String username;
    private String password;
    private String mail;
    private Boolean einkaeufer;

    @ManyToOne
    @JoinColumn(name = "TRAEGER_ID")
    private Traeger traeger;

}
