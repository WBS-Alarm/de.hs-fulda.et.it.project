package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.User;
import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.entity.Groesse;
import de.hsfulda.et.wbs.entity.Kategorie;
import de.hsfulda.et.wbs.http.haljson.GroesseHalJson;
import de.hsfulda.et.wbs.http.haljson.GroesseListHalJson;
import de.hsfulda.et.wbs.repository.GroesseRepository;
import de.hsfulda.et.wbs.repository.KategorieRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * Auf der Resource Größen können alle Groessen zu einem Träger abgerufen werden und neue Groessen erstellt werden.
 */
@RestController
@RequestMapping(GroesseListResource.PATH)
public class GroesseListResource {

    public static final String PATH = CONTEXT_ROOT + "/kategorie/{kategorieId}/groessen";

    private final KategorieRepository kategorieRepository;
    private final GroesseRepository groesseRepository;
    private final AccessService accessService;

    public GroesseListResource(
        KategorieRepository kategorieRepository,
        GroesseRepository groesseRepository,
        AccessService accessService) {

        this.kategorieRepository = kategorieRepository;
        this.groesseRepository = groesseRepository;
        this.accessService = accessService;
    }

    /**
     * Ermittlelt alle Groessen zu einem Träger.
     *
     * @param user Angemeldeter Benutzer.
     * @param kategorieId ID des Trägres.
     * @return Liste aller Groessen zu einem Träger.
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal User user, @PathVariable("kategorieId") Long kategorieId) {
        return accessService.hasAccessOnKategorie(user, kategorieId, () -> {
            List<GroesseData> all = groesseRepository.findAllByKategorieId(kategorieId);
            return new HttpEntity<>(new GroesseListHalJson(all));
        });
    }

    /**
     * Erstellt eine neue Groesse zu einem Träger.
     *
     * @param user Angemeldeter Benutzer.
     * @param kategorieId ID des Trägers.
     * @param groesse Neue Groesse.
     * @return Persistierter Groesse.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> post(
        @AuthenticationPrincipal User user,
        @PathVariable("kategorieId") Long kategorieId,
        @RequestBody Groesse groesse) {
        return accessService.hasAccessOnKategorie(user, kategorieId, () -> {

            if (isEmpty(groesse.getName())) {
                throw new IllegalArgumentException("Name der Größe muss angegeben werden.");
            }

            Optional<Kategorie> kategorie = kategorieRepository.findById(kategorieId);

            Groesse saved =
                Groesse.builder()
                    .name(groesse.getName())
                    .aktiv(true)
                    .build();

            Kategorie tr = kategorie.get();
            tr.addGroesse(saved);
            groesseRepository.save(saved);

            return new HttpEntity<>(new GroesseHalJson(saved));
        });
    }
}