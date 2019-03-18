package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.User;
import de.hsfulda.et.wbs.entity.Kategorie;
import de.hsfulda.et.wbs.http.haljson.KategorieHalJson;
import de.hsfulda.et.wbs.repository.KategorieRepository;
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
 * Diese Resource stellt einen Träger dar. Hier kann ein Kategorie aufgerufen, bearbeitet und gelöscht (inaktiv gesetzt)
 * werden.
 */
@RestController
@RequestMapping(KategorieResource.PATH)
public class KategorieResource {

    public static final String PATH = "/kategorie/{id}";

    private final KategorieRepository kategorieRepository;
    private final AccessService accessService;

    public KategorieResource(KategorieRepository kategorieRepository, AccessService accessService) {
        this.kategorieRepository = kategorieRepository;
        this.accessService = accessService;
    }

    /**
     * Ermittelt einen Kategorie anhand der ID.
     *
     * @param id ID des Zielorts aus dem Pfad
     * @return gefundenen ZIelort. Anderfalls 404
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        return accessService.hasAccessOnZielort(user, id, () -> {
            Optional<Kategorie> managed = kategorieRepository.findByIdAndAktivIsTrue(id);
            return managed.<HttpEntity<HalJsonResource>>map(kategorie -> new HttpEntity<>(new KategorieHalJson(kategorie)))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        });
    }

    /**
     * Ermittelt eine Kategorie anhand der ID.
     *
     * @param id ID des Kategories aus dem Pfad
     * @param traeger Kategorie mit neuem Namen
     * @return gespeicherten Kategorie. Anderfalls 404 oder 409
     */
    @PutMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> put(@AuthenticationPrincipal User user, @PathVariable("id") Long id, @RequestBody Kategorie traeger) {
        return accessService.hasAccessOnKategorie(user, id, () -> {
            if (isEmpty(traeger.getName())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Optional<Kategorie> managed = kategorieRepository.findById(id);
            if (managed.isPresent()) {
                Kategorie zo = managed.get();

                zo.setName(traeger.getName());
                Kategorie saved = kategorieRepository.save(zo);
                return new HttpEntity<>(new KategorieHalJson(saved));
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    /**
     * Entfernt einen Kategorie durch inaktiv setzen.
     *
     * @param id ID des Kategories aus dem Pfad
     * @return 200. Andernfalls 404.
     */
    @DeleteMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> delete(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        return accessService.hasAccessOnKategorie(user, id, () -> {
            Optional<Kategorie> managed = kategorieRepository.findById(id);

            if (managed.isPresent()) {
                Kategorie zo = managed.get();
                zo.setAktiv(false);
                kategorieRepository.save(zo);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }
}