package de.hsfulda.et.wbs.action.benutzer.impl;

import de.hsfulda.et.wbs.action.benutzer.CreateBenutzerAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BenutzerCreateDto;
import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.core.exception.UserAlreadyExistsException;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import de.hsfulda.et.wbs.security.service.UserCrudService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;

@Transactional
@Component
public class CreateBenutzerActionImpl implements CreateBenutzerAction {

    private final BenutzerRepository repo;
    private final UserCrudService users;
    private final TraegerRepository traegerRepo;

    public CreateBenutzerActionImpl(BenutzerRepository repo, UserCrudService users, TraegerRepository traegerRepo) {
        this.users = users;
        this.traegerRepo = traegerRepo;
        this.repo = repo;
    }

    @Override
    public BenutzerData perform(Long traegerId, BenutzerCreateDto benutzer) {
        if (isEmpty(benutzer.getUsername()) || isEmpty(benutzer.getPassword())) {
            throw new IllegalArgumentException("Benutzername und Password müssen angegeben werden.");
        }

        Optional<Traeger> traeger = traegerRepo.findById(traegerId);
        if (!traeger.isPresent()) {
            throw new ResourceNotFoundException("Träger mit ID {0} nicht gefunden.", traegerId);
        }

        Optional<WbsUser> found = users.findByUsername(benutzer.getUsername());
        if (found.isPresent()) {
            throw new UserAlreadyExistsException("Benutzer mit dem Namen {0} existiert bereits", benutzer.getUsername());
        }

        BenutzerData user = users.register(benutzer);
        setBenutzerOnTraeger(user, traeger.get());
        return user;
    }

    private void setBenutzerOnTraeger(BenutzerData user, Traeger traeger) {
        traeger.addBenutzer(repo.findByUsername(user.getUsername()));
        this.traegerRepo.save(traeger);
    }
}
