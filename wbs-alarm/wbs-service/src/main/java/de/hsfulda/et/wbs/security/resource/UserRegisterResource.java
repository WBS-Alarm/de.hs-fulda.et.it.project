package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.core.ResourceNotFoundException;
import de.hsfulda.et.wbs.core.UserAlreadyExistsException;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import de.hsfulda.et.wbs.security.User;
import de.hsfulda.et.wbs.security.service.UserCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * In dieser Resource werden Benutzer zu einem Träger registriert. Dies geschieht nur über den Träger Manager.
 */
@RestController
@RequestMapping(UserRegisterResource.PATH)
public class UserRegisterResource {

    public static final String PATH = CONTEXT_ROOT + "/users/register/{traegerId}";

    private final UserCrudService users;
    private final TraegerRepository traegerRepo;
    private final BenutzerRepository benutzerRepo;

    UserRegisterResource(UserCrudService users, TraegerRepository traegerRepo, BenutzerRepository benutzerRepo) {
        this.users = users;
        this.traegerRepo = traegerRepo;
        this.benutzerRepo = benutzerRepo;
    }

    /**
     * Erst werden die Angaben zum Benutzer gerprüft, ob Name und Password angegeben wurden. Danach wird geprüft, ob der
     * Träger existiert und ob es bereits einen Benutzer mit dem Username bereits vergeben ist.
     *
     * @param traegerId ID des Trägers zu dem der Benutzer angelegt werden soll.
     * @param benutzer Angemeldeter Benutzer.
     * @return Status 201.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    ResponseEntity<Void> post(@PathVariable("traegerId") Long traegerId, @RequestBody final Benutzer benutzer) {
        if (isEmpty(benutzer.getUsername()) || isEmpty(benutzer.getPassword())) {
            throw new IllegalArgumentException("Benutername und Password müssen angegeben werden.");
        }

        Optional<Traeger> traeger = traegerRepo.findById(traegerId);
        if (!traeger.isPresent()) {
            throw new ResourceNotFoundException();
        }

        Optional<WbsUser> found = users.findByUsername(benutzer.getUsername());
        if (found.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        users.register(new User(benutzer));
        //TODO: Header Location setzen

        setBenutzerOnTraeger(benutzer, traeger.get());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private void setBenutzerOnTraeger(Benutzer user, Traeger traeger) {
        traeger.addBenutzer(benutzerRepo.findByUsername(user.getUsername()));
        this.traegerRepo.save(traeger);
    }
}