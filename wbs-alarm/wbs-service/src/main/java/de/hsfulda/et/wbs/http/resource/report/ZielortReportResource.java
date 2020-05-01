package de.hsfulda.et.wbs.http.resource.report;

import de.hsfulda.et.wbs.action.report.GetZielortViewAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.http.haljson.report.ZielortReportHalJson;
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
 * Auf der Resource Zielorte können alle Zielorte zu einem Träger abgerufen werden und neue Zielorte erstellt werden.
 */
@RestController
@RequestMapping(ZielortReportResource.PATH)
public class ZielortReportResource {

    public static final String PATH = CONTEXT_ROOT + "/report/{traegerId}/zielort";

    private final GetZielortViewAction getAction;

    public ZielortReportResource(GetZielortViewAction getAction) {
        this.getAction = getAction;
    }

    /**
     * Ermittlelt Zielorte, Kategorien und die Summe der Bestände, unabhängig der Kleidergröße, zu einem Träger.
     *
     * @param user Angemeldeter Benutzer.
     * @param traegerId ID des Trägres.
     * @return Liste aller Zielorte zu einem Träger.
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal WbsUser user, @PathVariable("traegerId") Long traegerId) {
        return new HttpEntity<>(new ZielortReportHalJson(getAction.perform(user, traegerId), traegerId));
    }
}