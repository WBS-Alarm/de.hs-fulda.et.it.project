package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.http.haljson.TraegerHalJson;
import de.hsfulda.et.wbs.http.haljson.TraegerListHalJson;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;
import static de.hsfulda.et.wbs.http.resource.TraegerListResource.PATH;
import static org.springframework.util.ObjectUtils.isEmpty;

@RestController
@RequestMapping(PATH)
public final class TraegerListResource {

    public static final String PATH = "/traeger";

    private final TraegerRepository traegerRepository;

    public TraegerListResource(TraegerRepository traegerRepository) {
        this.traegerRepository = traegerRepository;
    }

    @GetMapping(produces = HAL_JSON)
    HttpEntity<HalJsonResource> get() {
        //TODO: Paginierung?

        Iterable<Traeger> all = traegerRepository.findAll();
        return new HttpEntity<>(new TraegerListHalJson(all));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = HAL_JSON)
    HttpEntity<HalJsonResource> post(@RequestBody Traeger traeger) {
        if (isEmpty(traeger.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Traeger saved = traegerRepository.save(
            Traeger.builder().
                name(traeger.getName())
                .build());

        return new HttpEntity<>(new TraegerHalJson(saved));
    }
}