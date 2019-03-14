package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.User;
import de.hsfulda.et.wbs.entity.Zielort;
import de.hsfulda.et.wbs.http.haljson.ZielortHalJson;
import de.hsfulda.et.wbs.repository.ZielortRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * Diese Resource stellt einen Träger dar. Hier kann ein Zielort aufgerufen, bearbeitet und gelöscht (inaktiv gesetzt)
 * werden.
 */
@RestController
@RequestMapping(ZielortResource.PATH)
public class ZielortResource {

    public static final String PATH = "/zielort/{id}";

    private final ZielortRepository zielortRepository;
    private final AccessService accessService;

    public ZielortResource(ZielortRepository zielortRepository, AccessService accessService) {
        this.zielortRepository = zielortRepository;
        this.accessService = accessService;
    }

    /**
     * Ermittelt einen Zielort anhand der ID.
     *
     * @param id ID des Zielorts aus dem Pfad
     * @return gefundenen ZIelort. Anderfalls 404
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        return accessService.hasAccessOnZielort(user, id, () -> {
            Optional<Zielort> managed = zielortRepository.findByIdAndAktivIsTrue(id);
            return managed.<HttpEntity<HalJsonResource>>map(zielort -> new HttpEntity<>(new ZielortHalJson(zielort)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        });
    }

    /**
     * Bearbeitet einen Zielort. Hierbei wird nur der Name geändert.
     *
     * @param id ID des Zielorts aus dem Pfad
     * @param traeger Zielort mit neuem Namen
     * @return gespeicherten Zielort. Anderfalls 404 oder 409
     */
    @PutMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> put(@AuthenticationPrincipal User user, @PathVariable("id") Long id, @RequestBody Zielort traeger) {
        return accessService.hasAccessOnZielort(user, id, () -> {
            if (isEmpty(traeger.getName())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Optional<Zielort> managed = zielortRepository.findById(id);
            if (managed.isPresent()) {
                Zielort zo = managed.get();
                if (zo.isAuto()) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                zo.setName(traeger.getName());
                Zielort saved = zielortRepository.save(zo);
                return new HttpEntity<>(new ZielortHalJson(saved));
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    /**
     * Entfernt einen Zielort durch inaktiv setzen.
     *
     * @param id ID des Zielorts aus dem Pfad
     * @return 200. Andernfalls 404.
     */
    @DeleteMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> delete(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        return accessService.hasAccessOnZielort(user, id, () -> {
            Optional<Zielort> managed = zielortRepository.findById(id);

            if (managed.isPresent()) {
                Zielort zo = managed.get();
                zo.setAktiv(false);
                zielortRepository.save(zo);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }
}