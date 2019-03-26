package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.ResourceNotFoundException;
import de.hsfulda.et.wbs.core.User;
import de.hsfulda.et.wbs.core.data.GrantedAuthorityData;
import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.http.haljson.BenutzerHalJson;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.security.repository.GrantedAuthorityRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

/**
 * Diese Resource stellt einen Benutzer dar. Hier kann ein Benutzer aufgerufen, bearbeitet und gelöscht (inaktiv
 * gesetzt) werden.
 */
@RestController
@RequestMapping(BenutzerResource.PATH)
public class BenutzerResource {

    public static final String PATH = CONTEXT_ROOT + "/benutzer/{id}";

    private final BenutzerRepository benutzerRepository;
    private final GrantedAuthorityRepository grantedAuthorityRepository;
    private final AccessService accessService;

    public BenutzerResource(
        BenutzerRepository benutzerRepository,
        GrantedAuthorityRepository grantedAuthorityRepository,
        AccessService accessService) {
        this.benutzerRepository = benutzerRepository;
        this.grantedAuthorityRepository = grantedAuthorityRepository;
        this.accessService = accessService;
    }

    /**
     * Ermittelt einen Benutzer anhand der ID.
     *
     * @param user angemeldeter Benutzer
     * @param id ID des Benutzers aus dem Pfad
     * @return gefundenen Träger. Anderfalls 404
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        return accessService.hasAccessOnBenutzer(user, id, () -> {
            Optional<Benutzer> benutzer = benutzerRepository.findById(id);

            if (!benutzer.isPresent()) {
                throw new ResourceNotFoundException();
            }

            Benutzer b = benutzer.get();
            List<GrantedAuthorityData> granted = grantedAuthorityRepository.findByUserId(b.getId());
            return new HttpEntity<>(BenutzerHalJson.ofGrantedAuthorities(b, granted));
        });
    }

    /**
     * Ändert die Werte von einem Benutzer. Hierbei wird die alte Repräsentation vom Benutzer geladen und nur Werte
     * überschrieben die auch geändert werden dürfen. Password, Token und Benutzername bleiben von Änderungen
     * unbetroffen.
     *
     * @param user angemeldeter Benutzer
     * @param id ID des Benutzers aus dem Pfad
     * @param benutzer geänderte Werte des Benutzers
     * @return Aktualisierter Benutzer.
     */
    @PutMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> put(
        @AuthenticationPrincipal User user,
        @PathVariable("id") Long id,
        @RequestBody Benutzer benutzer) {
        return accessService.hasAccessOnBenutzer(user, id, () -> {
            Optional<Benutzer> b = benutzerRepository.findById(id);

            if (!b.isPresent()) {
                throw new ResourceNotFoundException();
            }

            Benutzer loaded = b.get();
            loaded.setEinkaeufer(benutzer.getEinkaeufer());
            loaded.setMail(benutzer.getMail());
            benutzerRepository.save(loaded);
            return new HttpEntity<>(BenutzerHalJson.of(loaded));
        });
    }

    /**
     * Benutzer werden nicht gelöscht, sondern nur deaktiviert.
     *
     * @param user angemeldeter Benutzer
     * @param id ID des Benutzers aus dem Pfad
     * @return Rückmeldung über den Erfolg durch den HttpStatus.
     */
    @DeleteMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> delete(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        return accessService.hasAccessOnBenutzer(user, id, () -> {
            Optional<Benutzer> b = benutzerRepository.findById(id);

            if (!b.isPresent()) {
                throw new ResourceNotFoundException();
            }

            Benutzer loaded = b.get();
            loaded.setAktiv(false);
            benutzerRepository.save(loaded);
            return new ResponseEntity<>(HttpStatus.OK);
        });
    }
}