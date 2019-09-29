package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.action.transaktion.GetTransaktionAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.http.haljson.TransaktionHalJson;
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
 * Diese Resource stellt eine Transaktion dar. Diese kann nach Erstellung nur lesend angezeigt werden.
 */
@RestController
@RequestMapping(TransaktionResource.PATH)
public class TransaktionResource {

    public static final String PATH = CONTEXT_ROOT + "/transaktion/{id}";

    private final GetTransaktionAction getAction;

    public TransaktionResource(GetTransaktionAction getAction) {
        this.getAction = getAction;
    }

    /**
     * Ermittelt eine Transaktion anhand der ID.
     *
     * @param id ID der Transaktion aus dem Pfad
     * @return gefundene Transaktion. Anderfalls 404
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id) {
        return new HttpEntity<>(new TransaktionHalJson(user, getAction.perform(user, id)));
    }
}