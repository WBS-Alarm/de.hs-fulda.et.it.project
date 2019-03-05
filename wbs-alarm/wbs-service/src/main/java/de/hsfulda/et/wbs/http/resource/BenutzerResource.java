package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.http.haljson.BenutzerHalJson;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import de.hsfulda.et.wbs.security.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Optional;

import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;
import static de.hsfulda.et.wbs.http.resource.BenutzerResource.PATH;

/**
 * Diese Resource stellt einen Träger dar. Hier kann ein Träger aufgerufen, bearbeitet und gelöscht werden.
 */
@RestController
@RequestMapping(PATH)
public class BenutzerResource {

    public static final String PATH = "/benutzer/{id}";

    private final BenutzerRepository benutzerRepository;
    private final TraegerRepository traegerRepository;

    public BenutzerResource(BenutzerRepository benutzerRepository, TraegerRepository traegerRepository) {
        this.benutzerRepository = benutzerRepository;
        this.traegerRepository = traegerRepository;
    }

    /**
     * Ermittelt einen Benutzer anhand der ID.
     *
     * @param user angemeldeter Benutzer
     * @param id   ID des Benutzers aus dem Pfad
     * @return gefundenen Träger. Anderfalls 404
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        Optional<Traeger> managed = traegerRepository.findTraegerByUsernameAndBenutezrId(user.getUsername(), id);
        if (managed.isPresent()) {
            Optional<Benutzer> benutzer = benutzerRepository.findById(id);
            if (benutzer.isPresent()) {
                return new HttpEntity<>(new BenutzerHalJson(benutzer.get()));
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> put(@PathVariable("id") Long id, @RequestBody Traeger traeger) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> delete(@PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}