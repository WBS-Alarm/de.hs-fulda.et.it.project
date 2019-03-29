package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.action.zielort.LockZielortAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.http.haljson.ZielortHalJson;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

/**
 * Sperrt einen Zielort für weitere Bestandsaufnahmen
 */
@RestController
@RequestMapping(ZielortLockResource.PATH)
public class ZielortLockResource {

    public static final String PATH = CONTEXT_ROOT + "/zielort/{id}/lock";

    private final LockZielortAction postAction;

    public ZielortLockResource(LockZielortAction postAction) {
        this.postAction = postAction;
    }

    /**
     * Sperrt einen Zielort für die weitere Bestandsaufnahme
     *
     * @param id ID des Zielorts aus dem Pfad
     * @return gefundenen Zielort. Anderfalls 404
     */
    @PostMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> post(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id) {
        return new HttpEntity<>(new ZielortHalJson(user, postAction.perform(user, id)));
    }

}