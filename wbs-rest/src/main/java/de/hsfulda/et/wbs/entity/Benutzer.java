package de.hsfulda.et.wbs.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "BENUTZER")
public class Benutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME")
    @Size(max = 60)
    private String username;
    @Size(max = 60)
    private String password;
    @Size(max = 255)
    private String token;
    @Size(max = 254)
    private String mail;
    private Boolean einkaeufer;

    @ManyToOne
    @JoinColumn(name = "TRAEGER_ID")
    private Traeger traeger;

}
