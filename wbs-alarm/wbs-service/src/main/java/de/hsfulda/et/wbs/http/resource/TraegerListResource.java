package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.entity.Zielort;
import de.hsfulda.et.wbs.http.haljson.TraegerHalJson;
import de.hsfulda.et.wbs.http.haljson.TraegerListHalJson;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * Über die Träger Listen Resource können ale Träger aufgelistet werden und neue Träger hinzugefügt werden.
 */
@RestController
@RequestMapping(TraegerListResource.PATH)
public class TraegerListResource {

    public static final String PATH = CONTEXT_ROOT + "/traeger";

    private final TraegerRepository traegerRepository;

    public TraegerListResource(TraegerRepository traegerRepository) {
        this.traegerRepository = traegerRepository;
    }

    /**
     * Ermittelt alle Träger der Anwendung. Welche Träger es gibt, stellt kein besonderes Problem dar, da die
     * Informationen innerhalb eines Trägers nur über die den berechtigten Benutzer eingesehen werden können.
     *
     * @return alle vorhandenen Träger.
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get() {
        //TODO: Paginierung?

        Iterable<Traeger> all = traegerRepository.findAll();
        return new HttpEntity<>(new TraegerListHalJson(all));
    }

    /**
     * Erstellt einen Träger im System. Hierbei werden noch Standard-Zielorte dazu erstellt, die vom Anwendern nicht
     * entfernt werden können. Nur ein Administrator kann einen Träger im System anlegen.
     *
     * @param traeger Neuer Träger.
     * @return Erstellter Träger.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('ADMIN')")
    HttpEntity<HalJsonResource> post(@RequestBody Traeger traeger) {
        if (isEmpty(traeger.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Traeger tr = Traeger.builder().
            name(traeger.getName())
            .build();

        Zielort.getStandardForNewTraeger().forEach(tr::addZielort);
        Traeger saved = traegerRepository.save(tr);

        return new HttpEntity<>(new TraegerHalJson(saved));
    }
}