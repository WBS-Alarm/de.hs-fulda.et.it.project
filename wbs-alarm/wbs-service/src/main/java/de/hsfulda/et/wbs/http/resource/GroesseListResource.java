package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.action.groesse.CreateGroesseAction;
import de.hsfulda.et.wbs.action.groesse.GetGroesseListAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.http.haljson.GroesseListHalJson;
import de.hsfulda.et.wbs.http.resource.dto.GroesseDtoImpl;
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
 * Auf der Resource Größen können alle Groessen zu einem Träger abgerufen werden und neue Groessen erstellt werden.
 */
@RestController
@RequestMapping(GroesseListResource.PATH)
public class GroesseListResource {

    public static final String PATH = CONTEXT_ROOT + "/kategorie/{kategorieId}/groessen";

    private final GetGroesseListAction getAction;
    private final CreateGroesseAction postAction;

    public GroesseListResource(GetGroesseListAction getAction, CreateGroesseAction postAction) {
        this.getAction = getAction;
        this.postAction = postAction;
    }

    /**
     * Ermittlelt alle Groessen zu einem Träger.
     *
     * @param user        Angemeldeter Benutzer.
     * @param kategorieId ID des Trägres.
     * @return Liste aller Groessen zu einem Träger.
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal WbsUser user, @PathVariable("kategorieId") Long kategorieId) {
        return new HttpEntity<>(new GroesseListHalJson(user, getAction.perform(user, kategorieId)));
    }

    /**
     * Erstellt eine neue Groesse zu einem Träger.
     *
     * @param user        Angemeldeter Benutzer.
     * @param kategorieId ID des Trägers.
     * @param groesse     Neue Groesse.
     * @return Persistierter Groesse.
     */
    @PostMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> post(
            @AuthenticationPrincipal WbsUser user,
            @PathVariable("kategorieId") Long kategorieId,
            @RequestBody GroesseDtoImpl groesse) {
        GroesseData newGroesse = postAction.perform(user, kategorieId, groesse);
        MultiValueMap<String, String> header = locationHeader(GroesseResource.PATH, newGroesse.getId());
        return new ResponseEntity<>(header, HttpStatus.CREATED);
    }
}