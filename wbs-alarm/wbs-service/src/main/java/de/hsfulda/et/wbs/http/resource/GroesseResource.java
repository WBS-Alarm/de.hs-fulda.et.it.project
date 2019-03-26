package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.ResourceNotFoundException;
import de.hsfulda.et.wbs.core.User;
import de.hsfulda.et.wbs.entity.Groesse;
import de.hsfulda.et.wbs.http.haljson.GroesseHalJson;
import de.hsfulda.et.wbs.repository.GroesseRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * Diese Resource stellt einen Größe dar. Hier kann eine Größe aufgerufen, bearbeitet und gelöscht (inaktiv gesetzt)
 * werden.
 */
@RestController
@RequestMapping(GroesseResource.PATH)
public class GroesseResource {

    public static final String PATH = CONTEXT_ROOT + "/groesse/{id}";

    private final GroesseRepository groesseRepository;
    private final AccessService accessService;

    public GroesseResource(GroesseRepository groesseRepository, AccessService accessService) {
        this.groesseRepository = groesseRepository;
        this.accessService = accessService;
    }

    /**
     * Ermittelt einen Groesse anhand der ID.
     *
     * @param id ID des Zielorts aus dem Pfad
     * @return gefundenen ZIelort. Anderfalls 404
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        return accessService.hasAccessOnZielort(user, id, () -> {
            Optional<Groesse> managed = groesseRepository.findByIdAndAktivIsTrue(id);
            return managed.<HttpEntity<HalJsonResource>>map(groesse -> new HttpEntity<>(new GroesseHalJson(groesse)))
                .orElseThrow(ResourceNotFoundException::new);
        });
    }

    /**
     * Ermittelt eine Groesse anhand der ID.
     *
     * @param id ID des Groesses aus dem Pfad
     * @param traeger Groesse mit neuem Namen
     * @return gespeicherten Groesse. Anderfalls 404 oder 409
     */
    @PutMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> put(@AuthenticationPrincipal User user, @PathVariable("id") Long id, @RequestBody Groesse traeger) {
        return accessService.hasAccessOnGroesse(user, id, () -> {
            if (isEmpty(traeger.getName())) {
                throw new IllegalArgumentException("Name des Trägers muss angegeben werden");
            }

            Optional<Groesse> managed = groesseRepository.findById(id);

            if (!managed.isPresent()) {
                throw new ResourceNotFoundException();
            }

            Groesse zo = managed.get();

            zo.setName(traeger.getName());
            Groesse saved = groesseRepository.save(zo);
            return new HttpEntity<>(new GroesseHalJson(saved));
        });
    }

    /**
     * Entfernt einen Groesse durch inaktiv setzen.
     *
     * @param id ID des Groesses aus dem Pfad
     * @return 200. Andernfalls 404.
     */
    @DeleteMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> delete(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        return accessService.hasAccessOnGroesse(user, id, () -> {
            Optional<Groesse> managed = groesseRepository.findById(id);

            if (!managed.isPresent()) {
                throw new ResourceNotFoundException();
            }

            Groesse zo = managed.get();
            zo.setAktiv(false);
            groesseRepository.save(zo);
            return new ResponseEntity<>(HttpStatus.OK);
        });
    }
}