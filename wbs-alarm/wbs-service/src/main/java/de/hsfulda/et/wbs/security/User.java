package de.hsfulda.et.wbs.security;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

public class User implements UserDetails {

    private String id;
    private String username;
    private String password;

    private User() {
    }

    @JsonCreator
    public User(@JsonProperty("id") final String id,
                @JsonProperty("username") final String username,
                @JsonProperty("password") final String password) {
        super();
        this.id = requireNonNull(id);
        this.username = requireNonNull(username);
        this.password = requireNonNull(password);
    }

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
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
        return true;
    }

    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    void setPassword(String password) {
        this.password = password;
    }

    static User makeTemplate(User user) {
        return new User(user.getId(), user.getUsername(), user.getPassword());
    }

    public static class UserBuilder {

        private final User template;

        private UserBuilder() {
            template = new User();
        }

        public UserBuilder id(String id) {
            template.setId(id);
            return this;
        }

        public UserBuilder username(String username) {
            template.setUsername(username);
            return this;
        }

        public UserBuilder password(String password) {
            template.setPassword(password);
            return this;
        }

        public User build() {
            return makeTemplate(template);
        }
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }
}
