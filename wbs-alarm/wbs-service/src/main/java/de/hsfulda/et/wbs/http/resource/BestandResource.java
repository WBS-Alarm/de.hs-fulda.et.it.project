package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.action.bestand.DeleteBestandAction;
import de.hsfulda.et.wbs.action.bestand.GetBestandAction;
import de.hsfulda.et.wbs.action.bestand.UpdateBestandAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.http.haljson.BestandHalJson;
import de.hsfulda.et.wbs.http.resource.dto.BestandDtoImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

/**
 * Diese Resource stellt einen Bestand dar. Hier kann ein Bestand aufgerufen, bearbeitet und gelöscht (inaktiv gesetzt)
 * werden.
 */
@RestController
@RequestMapping(BestandResource.PATH)
public class BestandResource {

    public static final String PATH = CONTEXT_ROOT + "/bestand/{id}";

    private final GetBestandAction getAction;
    private final UpdateBestandAction putAction;
    private final DeleteBestandAction deleteAction;

    public BestandResource(GetBestandAction getAction, UpdateBestandAction putAction, DeleteBestandAction deleteAction) {
        this.getAction = getAction;
        this.putAction = putAction;
        this.deleteAction = deleteAction;
    }

    /**
     * Ermittelt einen Bestand anhand der ID.
     *
     * @param id ID des Bestands aus dem Pfad
     * @return gefundenen ZIelort. Anderfalls 404
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id) {
        return new HttpEntity<>(new BestandHalJson(getAction.perform(user, id)));
    }

    /**
     * Bearbeitet einen Bestand. Hierbei wird nur die Anzahl geändert.
     *
     * @param id ID des Bestands aus dem Pfad
     * @param bestand Bestand mit neuer Anzahl
     * @return gespeicherten Bestand. Anderfalls 404 oder 409
     */
    @PutMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> put(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id, @RequestBody BestandDtoImpl bestand) {
        return new HttpEntity<>(new BestandHalJson(putAction.perform(user, id, bestand)));
    }

    /**
     * Entfernt einen Bestand durch inaktiv setzen.
     *
     * @param id ID des Bestands aus dem Pfad
     * @return 200. Andernfalls 404.
     */
    @DeleteMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> delete(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id) {
        deleteAction.perform(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}