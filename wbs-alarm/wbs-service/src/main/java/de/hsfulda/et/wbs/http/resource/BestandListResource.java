package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.action.bestand.CreateBestandAction;
import de.hsfulda.et.wbs.action.bestand.GetBestandListAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.http.haljson.BestandHalJson;
import de.hsfulda.et.wbs.http.haljson.BestandListHalJson;
import de.hsfulda.et.wbs.http.resource.dto.BestandCreateDtoImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

/**
 * Auf der Resource Bestände können alle Bestand zu einem Zielort aufgelistet werden.
 */
@RestController
@RequestMapping(BestandListResource.PATH)
public class BestandListResource {

    public static final String PATH = CONTEXT_ROOT + "/zielort/{zielortId}/bestand";

    private final GetBestandListAction getAction;
    private final CreateBestandAction postAction;

    public BestandListResource(GetBestandListAction getAction, CreateBestandAction postAction) {
        this.getAction = getAction;
        this.postAction = postAction;
    }

    /**
     * Ermittlelt alle Bestände zu einem Zielort.
     *
     * @param user Angemeldeter Benutzer.
     * @param zielortId ID des Zielorts.
     * @return Liste aller Bestände zu einem zu einem Zielort.
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal WbsUser user, @PathVariable("zielortId") Long zielortId) {
        return new HttpEntity<>(new BestandListHalJson(getAction.perform(user, zielortId)));
    }

    /**
     * Erstellt einen neuen Bestand zu einem Zielort.
     *
     * @param user Angemeldeter Benutzer.
     * @param zielortId ID des Trägers.
     * @param bestand Neuer Bestand.
     * @return Persistierter Zielort.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> post(
        @AuthenticationPrincipal WbsUser user,
        @PathVariable("zielortId") Long zielortId,
        @RequestBody BestandCreateDtoImpl bestand) {

        return new HttpEntity<>(new BestandHalJson(postAction.perform(user, zielortId, bestand)));
    }
}