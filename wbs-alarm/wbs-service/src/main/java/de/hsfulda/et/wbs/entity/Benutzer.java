package de.hsfulda.et.wbs.entity;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Boolean getEinkaeufer() {
        return einkaeufer;
    }

    public void setEinkaeufer(Boolean einkaeufer) {
        this.einkaeufer = einkaeufer;
    }

    public Traeger getTraeger() {
        return traeger;
    }

    public void setTraeger(Traeger traeger) {
        this.traeger = traeger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Benutzer benutzer = (Benutzer) o;
        return Objects.equals(id, benutzer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
