package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.entity.Zielort;
import de.hsfulda.et.wbs.http.haljson.ZielortHalJson;
import de.hsfulda.et.wbs.http.haljson.ZielortListHalJson;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import de.hsfulda.et.wbs.repository.ZielortRepository;
import de.hsfulda.et.wbs.security.User;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;
import static de.hsfulda.et.wbs.http.resource.ZielortListResource.PATH;
import static org.springframework.util.ObjectUtils.isEmpty;

@RestController
@RequestMapping(PATH)
public class ZielortListResource {

    public static final String PATH = "/traeger/{traegerId}/zielorte";

    private final TraegerRepository traegerRepository;
    private final ZielortRepository zielortRepository;
    private final AccessService accessService;

    public ZielortListResource(
        TraegerRepository traegerRepository,
        ZielortRepository zielortRepository,
        AccessService accessService) {

        this.traegerRepository = traegerRepository;
        this.zielortRepository = zielortRepository;
        this.accessService = accessService;
    }


    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal User user, @PathVariable("traegerId") Long traegerId) {
        return accessService.hasAccessOnTraeger(user, traegerId, () -> {
            //TODO: Paginierung?

            Iterable<Zielort> all = zielortRepository.findAll();
            return new HttpEntity<>(new ZielortListHalJson(all));
        });
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> post(
        @AuthenticationPrincipal User user,
        @PathVariable("traegerId") Long traegerId,
        @RequestBody Zielort zielort) {
        return accessService.hasAccessOnTraeger(user, traegerId, () -> {

            if (isEmpty(zielort.getName())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Optional<Traeger> traeger = traegerRepository.findById(traegerId);

            Zielort saved =
                Zielort.builder()
                    .name(zielort.getName())
                    .aktiv(true)
                    .build();

            Traeger tr = traeger.get();
            tr.addZielort(saved);
            zielortRepository.save(saved);

            return new HttpEntity<>(new ZielortHalJson(saved));
        });
    }
}