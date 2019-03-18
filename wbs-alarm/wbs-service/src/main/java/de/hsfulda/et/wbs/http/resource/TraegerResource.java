package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.http.haljson.TraegerHalJson;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * Diese Resource stellt einen Träger dar. Hier kann ein Träger aufgerufen, bearbeitet und gelöscht werden.
 */
@RestController
@RequestMapping(TraegerResource.PATH)
public class TraegerResource {

    public static final String PATH = CONTEXT_ROOT + "/traeger/{id}";

    private final TraegerRepository traegerRepository;

    public TraegerResource(TraegerRepository traegerRepository) {
        this.traegerRepository = traegerRepository;
    }

    /**
     * Ermittelt einen Träger anhand der ID.
     *
     * @param id ID des Trägers aus dem Pfad
     * @return gefundenen Träger. Anderfalls 404
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@PathVariable("id") Long id) {
        Optional<Traeger> managed = traegerRepository.findById(id);
        return managed.<HttpEntity<HalJsonResource>>map(traeger -> new HttpEntity<>(new TraegerHalJson(traeger)))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Bearbeitet einen Träger. Hierbei wird nur der Name geändert.
     *
     * @param id ID des Trägers aus dem Pfad
     * @param traeger Träger mit neuem Namen
     * @return gespeicherten Träger. Anderfalls 404 oder 409
     */
    @PutMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> put(@PathVariable("id") Long id, @RequestBody Traeger traeger) {
        if (isEmpty(traeger.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Traeger> managed = traegerRepository.findById(id);
        if (managed.isPresent()) {
            Traeger tr = managed.get();
            tr.setName(traeger.getName());
            Traeger saved = traegerRepository.save(tr);
            return new HttpEntity<>(new TraegerHalJson(saved));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Entfernt einen Träger aus dem System. Anders als bei Kategorien wird hier ein ganzer Träger entfernt und nicht
     * deaktiviert. Dies kann nur vom Administrator durchgeführt w erden.
     *
     * @param id ID des Trägers aus dem Pfad
     * @return 200. Andernfalls 404.
     */
    @DeleteMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('ADMIN')")
    HttpEntity<HalJsonResource> delete(@PathVariable("id") Long id) {
        Optional<Traeger> managed = traegerRepository.findById(id);

        if (managed.isPresent()) {
            traegerRepository.delete(managed.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}