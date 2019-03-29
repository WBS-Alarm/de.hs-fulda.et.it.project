package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.action.traeger.DeleteTraegerAction;
import de.hsfulda.et.wbs.action.traeger.GetTraegerAction;
import de.hsfulda.et.wbs.action.traeger.UpdateTraegerAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.http.haljson.TraegerHalJson;
import de.hsfulda.et.wbs.http.resource.dto.TraegerDtoImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

/**
 * Diese Resource stellt einen Träger dar. Hier kann ein Träger aufgerufen, bearbeitet und gelöscht werden.
 */
@RestController
@RequestMapping(TraegerResource.PATH)
public class TraegerResource {

    public static final String PATH = CONTEXT_ROOT + "/traeger/{id}";

    private final GetTraegerAction getAction;
    private final UpdateTraegerAction putAction;
    private final DeleteTraegerAction deleteAction;

    public TraegerResource(GetTraegerAction getAction, UpdateTraegerAction putAction, DeleteTraegerAction deleteAction) {
        this.getAction = getAction;
        this.putAction = putAction;
        this.deleteAction = deleteAction;
    }

    /**
     * Ermittelt einen Träger anhand der ID.
     *
     * @param id ID des Trägers aus dem Pfad
     * @return gefundenen Träger. Anderfalls 404
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id) {
        return new HttpEntity<>(new TraegerHalJson(user, getAction.perform(id)));
    }

    /**
     * Bearbeitet einen Träger. Hierbei wird nur der Name geändert.
     *
     * @param id      ID des Trägers aus dem Pfad
     * @param traeger Träger mit neuem Namen
     * @return gespeicherten Träger. Anderfalls 404 oder 409
     */
    @PutMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> put(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id, @RequestBody TraegerDtoImpl traeger) {
        return new HttpEntity<>(new TraegerHalJson(user, putAction.perform(id, traeger)));
    }

    /**
     * Entfernt einen Träger aus dem System. Anders als bei Kategorien wird hier ein ganzer Träger entfernt und nicht
     * deaktiviert. Dies kann nur vom Administrator durchgeführt w erden.
     *
     * @param id ID des Trägers aus dem Pfad
     * @return 200. Andernfalls 404.
     */
    @DeleteMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('ADMIN')")
    HttpEntity<HalJsonResource> delete(@PathVariable("id") Long id) {
        deleteAction.perform(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}