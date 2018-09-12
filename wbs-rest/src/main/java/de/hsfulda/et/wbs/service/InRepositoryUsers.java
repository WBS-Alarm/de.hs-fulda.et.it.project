package de.hsfulda.et.wbs.service;

import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.repo.BenutzerCrudRepository;
import de.hsfulda.et.wbs.security.Password;
import de.hsfulda.et.wbs.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
final class InRepositoryUsers implements UserCrudService {

    @Autowired
    private BenutzerCrudRepository repository;

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

    private static User as(Benutzer benutzer) {
        return new User(benutzer.getUsername(), benutzer.getUsername(), benutzer.getPassword());
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
        return Optional.of(as(repository.findByUsername(username)));
    }
}
