package de.hsfulda.et.wbs.security.service;

import de.hsfulda.et.wbs.core.User;
import de.hsfulda.et.wbs.core.data.GrantedAuthorityData;
import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.security.Password;
import de.hsfulda.et.wbs.security.Roles;
import de.hsfulda.et.wbs.security.repository.GrantedAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

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
        benutzer.setAktiv(true);

        return as(repository.save(benutzer));
    }

    @Override
    public User save(final User user, final String token) {
        Benutzer benutzer = repository.findByUsername(user.getUsername());
        benutzer.setToken(token);
        return as(repository.save(benutzer));
    }

    private User as(Benutzer benutzer) {
        Collection<GrantedAuthorityData> authorities = authorityRepository.findByUserId(benutzer.getId());

        User user = new User(benutzer);
        user.addAuthorities(Roles.getRoles(authorities));
        return user;
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
    public Optional<User> findByUsername(final String username) {
        Benutzer byUsername = repository.findByUsername(username);
        if (byUsername == null) {
            return Optional.empty();
        }
        return Optional.of(as(byUsername));
    }
}
