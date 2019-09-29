package de.hsfulda.et.wbs.security.service;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BenutzerCreateDto;
import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.core.data.GrantedAuthorityData;
import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.security.Password;
import de.hsfulda.et.wbs.security.Roles;
import de.hsfulda.et.wbs.security.User;
import de.hsfulda.et.wbs.security.repository.GrantedAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
final class InRepositoryUsers implements UserCrudService {

    private final BenutzerRepository repository;
    private final GrantedAuthorityRepository authorityRepository;

    @Autowired
    public InRepositoryUsers(BenutzerRepository repository, GrantedAuthorityRepository authorityRepository) {
        this.repository = repository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public BenutzerData register(final BenutzerCreateDto user) {
        Benutzer benutzer = new Benutzer();
        benutzer.setUsername(user.getUsername());
        benutzer.setPassword(Password.hashPassword(user.getPassword()));
        benutzer.setAktiv(true);

        return repository.save(benutzer);
    }

    private WbsUser as(BenutzerData benutzer) {
        Collection<GrantedAuthorityData> authorities = authorityRepository.findByUserId(benutzer.getId());

        User user = new User(benutzer);
        user.addAuthorities(Roles.getRoles(authorities));
        return user;
    }

    @Override
    public Optional<WbsUser> findByUsername(final String username) {
        BenutzerData byUsername = repository.findByUsername(username);
        if (byUsername == null) {
            return Optional.empty();
        }
        return Optional.of(as(byUsername));
    }
}
