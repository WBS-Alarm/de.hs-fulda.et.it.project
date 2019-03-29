package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.action.kategorie.DeleteKategorieAction;
import de.hsfulda.et.wbs.action.kategorie.GetKategorieAction;
import de.hsfulda.et.wbs.action.kategorie.UpdateKategorieAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.http.haljson.KategorieHalJson;
import de.hsfulda.et.wbs.http.resource.dto.KategorieDtoImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

/**
 * Diese Resource stellt eine Kategorie dar. Hier kann ein Kategorie aufgerufen, bearbeitet und gelöscht (inaktiv
 * gesetzt) werden.
 */
@RestController
@RequestMapping(KategorieResource.PATH)
public class KategorieResource {

    public static final String PATH = CONTEXT_ROOT + "/kategorie/{id}";

    private final GetKategorieAction getAction;
    private final UpdateKategorieAction putAction;
    private final DeleteKategorieAction deleteAction;

    public KategorieResource(GetKategorieAction getAction, UpdateKategorieAction putAction, DeleteKategorieAction deleteAction) {
        this.getAction = getAction;
        this.putAction = putAction;
        this.deleteAction = deleteAction;
    }

    /**
     * Ermittelt einen Kategorie anhand der ID.
     *
     * @param id ID der Kategorie aus dem Pfad
     * @return gefundenen Kategorie. Anderfalls 404
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id) {
        return new HttpEntity<>(new KategorieHalJson(getAction.perform(user, id)));
    }

    /**
     * Ermittelt eine Kategorie anhand der ID.
     *
     * @param id        ID des Kategories aus dem Pfad
     * @param kategorie Kategorie mit neuem Namen
     * @return gespeicherten Kategorie. Anderfalls 404 oder 409
     */
    @PutMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> put(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id, @RequestBody KategorieDtoImpl kategorie) {
        return new HttpEntity<>(new KategorieHalJson(putAction.perform(user, id, kategorie)));
    }

    /**
     * Entfernt einen Kategorie durch inaktiv setzen.
     *
     * @param id ID des Kategories aus dem Pfad
     * @return 200. Andernfalls 404.
     */
    @DeleteMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> delete(@AuthenticationPrincipal WbsUser user, @PathVariable("id") Long id) {
        deleteAction.perform(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}