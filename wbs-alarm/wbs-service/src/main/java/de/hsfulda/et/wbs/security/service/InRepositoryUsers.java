package de.hsfulda.et.wbs.security.service;

import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.security.Password;
import de.hsfulda.et.wbs.security.User;
import de.hsfulda.et.wbs.security.entity.GrantedAuthority;
import de.hsfulda.et.wbs.security.repository.GrantedAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
final class InRepositoryUsers implements UserCrudService {

    @Autowired
    private BenutzerRepository repository;

    @Autowired
    private GrantedAuthorityRepository authorityRepository;

    @Override
    public User register(final User user) {
        Benutzer benutzer = new Benutzer();
        benutzer.setUsername(user.getUsername());
        benutzer.setPassword(Password.hashPassword(user.getPassword()));

        return as(repository.save(benutzer));
    }

    @Override
    public User save(final User user, final String token) {
        Benutzer benutzer = repository.findByUsername(user.getUsername());
        benutzer.setToken(token);
        return as(repository.save(benutzer));
    }

    private User as(Benutzer benutzer) {
        Collection<GrantedAuthority> authorities = authorityRepository.findByUserId(benutzer.getId());
        return User.builder()
            .id(benutzer.getUsername())
            .username(benutzer.getUsername())
            .password(benutzer.getPassword())
            .authorities(authorities.stream().map(GrantedAuthority::asAuthority).collect(Collectors.toList()))
            .build();
    }

    @Override
    public Optional<String> getToken(User user) {
        return Optional.ofNullable(repository.findByUsername(user.getUsername()).getToken());
    }

    @Override
    public void deleteToken(User user) {
        save(user, null);
    }

    @Override
    public Optional<User> find(final String username) {
        return Optional.of(as(repository.findByUsername(username)));
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        Benutzer byUsername = repository.findByUsername(username);
        if (byUsername == null) {
            return Optional.empty();
        }
        return Optional.of(as(byUsername));
    }
}
