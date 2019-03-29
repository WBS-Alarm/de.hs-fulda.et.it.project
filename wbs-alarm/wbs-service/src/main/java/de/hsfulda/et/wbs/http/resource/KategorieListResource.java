package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.action.kategorie.CreateKategorieAction;
import de.hsfulda.et.wbs.action.kategorie.GetKategorieListAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.http.haljson.KategorieHalJson;
import de.hsfulda.et.wbs.http.haljson.KategorieListHalJson;
import de.hsfulda.et.wbs.http.resource.dto.KategorieDtoImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

/**
 * Auf der Resource Kategorien können alle Kategorien zu einem Träger abgerufen werden und neue Kategorien erstellt
 * werden.
 */
@RestController
@RequestMapping(KategorieListResource.PATH)
public class KategorieListResource {

    public static final String PATH = CONTEXT_ROOT + "/traeger/{traegerId}/kategorien";

    private final GetKategorieListAction getAction;
    private final CreateKategorieAction postAction;

    public KategorieListResource(GetKategorieListAction getAction, CreateKategorieAction postAction) {
        this.getAction = getAction;
        this.postAction = postAction;
    }


    /**
     * Ermittlelt alle Kategorien zu einem Träger.
     *
     * @param user      Angemeldeter Benutzer.
     * @param traegerId ID des Trägres.
     * @return Liste aller Kategorien zu einem Träger.
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal WbsUser user, @PathVariable("traegerId") Long traegerId) {
        return new HttpEntity<>(new KategorieListHalJson(getAction.perform(user, traegerId)));
    }

    /**
     * Erstellt eine neue Kategorie zu einem Träger.
     *
     * @param user      Angemeldeter Benutzer.
     * @param traegerId ID des Trägers.
     * @param kategorie Neue Kategorie.
     * @return Persistierter Kategorie.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> post(
            @AuthenticationPrincipal WbsUser user,
            @PathVariable("traegerId") Long traegerId,
            @RequestBody KategorieDtoImpl kategorie) {
        return new HttpEntity<>(new KategorieHalJson(postAction.perform(user, traegerId, kategorie)));
    }
}