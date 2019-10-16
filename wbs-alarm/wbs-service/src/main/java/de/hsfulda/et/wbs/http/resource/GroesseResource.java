package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.action.groesse.DeleteGroesseAction;
import de.hsfulda.et.wbs.action.groesse.GetGroesseAction;
import de.hsfulda.et.wbs.action.groesse.UpdateGroesseAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.http.haljson.GroesseHalJson;
import de.hsfulda.et.wbs.http.resource.dto.GroesseDtoImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

/**
 * Diese Resource stellt einen Größe dar. Hier kann eine Größe aufgerufen, bearbeitet und gelöscht (inaktiv gesetzt)
 * werden.
 */
@RestController
@RequestMapping(GroesseResource.PATH)
public class GroesseResource {

    public static final String PATH = CONTEXT_ROOT + "/groesse/{id}";

    private final GetGroesseAction getAction;
    private final UpdateGroesseAction putAction;
    private final DeleteGroesseAction deleteAction;

    public GroesseResource(GetGroesseAction getAction, UpdateGroesseAction putAction,
            DeleteGroesseAction deleteAction) {
        this.getAction = getAction;
        this.putAction = putAction;
        this.deleteAction = deleteAction;
    }

    /**
     * Ermittelt einen Groesse anhand der ID.
     *
     * @param id ID der Groesse aus dem Pfad
     * @return gefundenen Groesse. Anderfalls 404
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id) {
        return new HttpEntity<>(new GroesseHalJson(user, getAction.perform(user, id)));
    }

    /**
     * Ermittelt eine Groesse anhand der ID.
     *
     * @param id ID des Groesses aus dem Pfad
     * @param groesse Groesse mit neuem Namen
     * @return gespeicherten Groesse. Anderfalls 404 oder 409
     */
    @PutMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> put(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id,
            @RequestBody GroesseDtoImpl groesse) {
        return new HttpEntity<>(new GroesseHalJson(user, putAction.perform(user, id, groesse)));
    }

    /**
     * Entfernt einen Groesse durch inaktiv setzen.
     *
     * @param id ID des Groesses aus dem Pfad
     * @return 200. Andernfalls 404.
     */
    @DeleteMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> delete(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id) {
        deleteAction.perform(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}