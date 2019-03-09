package de.hsfulda.et.wbs.security.resource;

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

import static org.springframework.util.StringUtils.isEmpty;

/**
 * In dieser Resource werden Benutzer zu einem Träger registriert. Dies geschieht nur über den Träger Manager.
 */
@RestController
@RequestMapping(UserRegisterResource.PATH)
public class UserRegisterResource {

    public static final String PATH = "/users/register/{traegerId}";

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
     * @param user Angemeldeter Benutzer.
     * @return Status 201.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    ResponseEntity<Void> post(@PathVariable("traegerId") Long traegerId, @RequestBody final Benutzer user) {
        if (isEmpty(user.getUsername()) || isEmpty(user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Traeger> traeger = traegerRepo.findById(traegerId);
        if (!traeger.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<User> found = users.findByUsername(user.getUsername());
        if (found.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        users.register(
            User.builder()
                .id(user.getUsername())
                .username(user.getUsername())
                .password(user.getPassword())
                .aktiv(true)
                .build()
        );

        setBenutzerOnTraeger(user, traeger.get());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private void setBenutzerOnTraeger(Benutzer user, Traeger traeger) {
        traeger.addBenutzer(benutzerRepo.findByUsername(user.getUsername()));
        this.traegerRepo.save(traeger);
    }
}