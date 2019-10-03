package de.hsfulda.et.wbs.entity;

import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.core.data.TraegerData;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "BENUTZER")
public class Benutzer implements BenutzerData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME")
    @Size(max = 60)
    private String username;
    @Size(max = 60)
    private String password;
    @Size(max = 254)
    private String mail;
    private Boolean einkaeufer;
    private Boolean aktiv;

    @ManyToOne
    @JoinColumn(name = "TRAEGER_ID")
    private Traeger traeger;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public TraegerData getTraeger() {
        return traeger;
    }

    public void setTraeger(Traeger traeger) {
        this.traeger = traeger;
    }

    @Override
    public Boolean getAktiv() {
        return aktiv;
    }

    public void setAktiv(Boolean aktiv) {
        this.aktiv = aktiv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Benutzer benutzer = (Benutzer) o;
        return Objects.equals(id, benutzer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
