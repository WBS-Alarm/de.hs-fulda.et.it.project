package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.action.zielort.DeleteZielortAction;
import de.hsfulda.et.wbs.action.zielort.GetZielortAction;
import de.hsfulda.et.wbs.action.zielort.UpdateZielortAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.http.haljson.ZielortHalJson;
import de.hsfulda.et.wbs.http.resource.dto.ZielortDtoImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

/**
 * Diese Resource stellt einen Zielort dar. Hier kann ein Zielort aufgerufen, bearbeitet und gelöscht (inaktiv gesetzt)
 * werden.
 */
@RestController
@RequestMapping(ZielortResource.PATH)
public class ZielortResource {

    public static final String PATH = CONTEXT_ROOT + "/zielort/{id}";

    private final GetZielortAction getAction;
    private final UpdateZielortAction putAction;
    private final DeleteZielortAction deleteAction;

    public ZielortResource(GetZielortAction getAction, UpdateZielortAction putAction, DeleteZielortAction deleteAction) {
        this.getAction = getAction;
        this.putAction = putAction;
        this.deleteAction = deleteAction;
    }

    /**
     * Ermittelt einen Zielort anhand der ID.
     *
     * @param id ID des Zielorts aus dem Pfad
     * @return gefundenen Zielort. Anderfalls 404
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id) {
        return new HttpEntity<>(new ZielortHalJson(user, getAction.perform(user, id)));
    }

    /**
     * Bearbeitet einen Zielort. Hierbei wird nur der Name geändert.
     *
     * @param id      ID des Zielorts aus dem Pfad
     * @param zielort Zielort mit neuem Namen
     * @return gespeicherten Zielort. Anderfalls 404 oder 409
     */
    @PutMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> put(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id, @RequestBody ZielortDtoImpl zielort) {
        return new HttpEntity<>(new ZielortHalJson(user, putAction.perform(user, id, zielort)));
    }

    /**
     * Entfernt einen Zielort durch inaktiv setzen.
     *
     * @param id ID des Zielorts aus dem Pfad
     * @return 200. Andernfalls 404.
     */
    @DeleteMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> delete(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id) {
        deleteAction.perform(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}