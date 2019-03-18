package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.User;
import de.hsfulda.et.wbs.entity.Kategorie;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.http.haljson.KategorieHalJson;
import de.hsfulda.et.wbs.http.haljson.KategorieListHalJson;
import de.hsfulda.et.wbs.repository.KategorieRepository;
import de.hsfulda.et.wbs.repository.TraegerRepository;
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
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * Auf der Resource Kategorien können alle Kategorien zu einem Träger abgerufen werden und neue Kategorien erstellt werden.
 */
@RestController
@RequestMapping(KategorieListResource.PATH)
public class KategorieListResource {

    public static final String PATH = CONTEXT_ROOT + "/traeger/{traegerId}/kategorien";

    private final TraegerRepository traegerRepository;
    private final KategorieRepository kategorieRepository;
    private final AccessService accessService;

    public KategorieListResource(
            TraegerRepository traegerRepository,
            KategorieRepository kategorieRepository,
            AccessService accessService) {

        this.traegerRepository = traegerRepository;
        this.kategorieRepository = kategorieRepository;
        this.accessService = accessService;
    }

    /**
     * Ermittlelt alle Kategorien zu einem Träger.
     *
     * @param user      Angemeldeter Benutzer.
     * @param traegerId ID des Trägres.
     * @return Liste aller Kategorien zu einem Träger.
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal User user, @PathVariable("traegerId") Long traegerId) {
        return accessService.hasAccessOnTraeger(user, traegerId, () -> {
            //TODO: Paginierung?

            List<Kategorie> all = kategorieRepository.findAllByTraegerId(traegerId);
            return new HttpEntity<>(new KategorieListHalJson(all));
        });
    }

    /**
     * Erstellt eine neue Kategorie zu einem Träger.
     *
     * @param user      Angemeldeter Benutzer.
     * @param traegerId ID des Trägers.
     * @param kategorie Neue Kategorie.
     * @return Persistierter Kategorie.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> post(
            @AuthenticationPrincipal User user,
            @PathVariable("traegerId") Long traegerId,
            @RequestBody Kategorie kategorie) {
        return accessService.hasAccessOnTraeger(user, traegerId, () -> {

            if (isEmpty(kategorie.getName())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Optional<Traeger> traeger = traegerRepository.findById(traegerId);

            Kategorie saved =
                    Kategorie.builder()
                            .name(kategorie.getName())
                            .aktiv(true)
                            .build();

            Traeger tr = traeger.get();
            tr.addKategorie(saved);
            kategorieRepository.save(saved);

            return new HttpEntity<>(new KategorieHalJson(saved));
        });
    }
}