package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.action.benutzer.GetBenutzerListAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.http.haljson.BenutzerListHalJson;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

/**
 * Auf der Resource Benutzer können alle Benutzer zu einem Träger abgerufen werden.
 */
@RestController
@RequestMapping(BenutzerListResource.PATH)
public class BenutzerListResource {

    public static final String PATH = CONTEXT_ROOT + "/traeger/{traegerId}/benutzer";

    private final GetBenutzerListAction getAction;

    public BenutzerListResource(GetBenutzerListAction getAction) {
        this.getAction = getAction;
    }

    /**
     * Ermittlelt alle Benutzer zu einem Träger.
     *
     * @param user Angemeldeter Benutzer.
     * @param traegerId ID des Trägres.
     * @return Liste aller Benutzer zu einem Träger.
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal WbsUser user, @PathVariable("traegerId") Long traegerId) {
        return new HttpEntity<>(new BenutzerListHalJson(user, getAction.perform(user, traegerId)));
    }
}