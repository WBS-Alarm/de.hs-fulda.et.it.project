package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.action.zielort.CreateZielortAction;
import de.hsfulda.et.wbs.action.zielort.GetZielortListAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.ZielortData;
import de.hsfulda.et.wbs.http.haljson.ZielortListHalJson;
import de.hsfulda.et.wbs.http.resource.dto.ZielortDtoImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;
import static de.hsfulda.et.wbs.util.HeaderUtil.locationHeader;

/**
 * Auf der Resource Zielorte können alle Zielorte zu einem Träger abgerufen werden und neue Zielorte erstellt werden.
 */
@RestController
@RequestMapping(ZielortListResource.PATH)
public class ZielortListResource {

    public static final String PATH = CONTEXT_ROOT + "/traeger/{traegerId}/zielorte";

    private final GetZielortListAction getAction;
    private final CreateZielortAction postAction;

    public ZielortListResource(GetZielortListAction getAction, CreateZielortAction postAction) {
        this.getAction = getAction;
        this.postAction = postAction;
    }

    /**
     * Ermittlelt alle Zielorte zu einem Träger.
     *
     * @param user      Angemeldeter Benutzer.
     * @param traegerId ID des Trägres.
     * @return Liste aller Zielorte zu einem Träger.
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal WbsUser user, @PathVariable("traegerId") Long traegerId) {
        return new HttpEntity<>(new ZielortListHalJson(user, getAction.perform(user, traegerId)));
    }

    /**
     * Erstellt einen neuen Zielort zu einem Träger.
     *
     * @param user      Angemeldeter Benutzer.
     * @param traegerId ID des Trägers.
     * @param zielort   Neuer Zielort.
     * @return Persistierter Zielort.
     */
    @PostMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> post(
            @AuthenticationPrincipal WbsUser user,
            @PathVariable("traegerId") Long traegerId,
            @RequestBody ZielortDtoImpl zielort) {

        ZielortData newZielort = postAction.perform(user, traegerId, zielort);
        MultiValueMap<String, String> header = locationHeader(ZielortResource.PATH, newZielort.getId());
        return new ResponseEntity<>(header, HttpStatus.CREATED);
    }
}