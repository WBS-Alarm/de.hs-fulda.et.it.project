package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.action.kategorie.CreateKategorieAction;
import de.hsfulda.et.wbs.action.kategorie.GetKategorieListAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.KategorieData;
import de.hsfulda.et.wbs.http.haljson.KategorieListHalJson;
import de.hsfulda.et.wbs.http.resource.dto.KategorieDtoImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.Relations.REL_KATEGORIE;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;
import static de.hsfulda.et.wbs.util.HeaderUtil.locationHeader;

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
     * @param user Angemeldeter Benutzer.
     * @param traegerId ID des Trägres.
     * @return Liste aller Kategorien zu einem Träger.
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal WbsUser user, @PathVariable("traegerId") Long traegerId) {
        return new HttpEntity<>(new KategorieListHalJson(user, getAction.perform(user, traegerId), traegerId));
    }

    /**
     * Erstellt eine neue Kategorie zu einem Träger.
     *
     * @param user Angemeldeter Benutzer.
     * @param traegerId ID des Trägers.
     * @param kategorie Neue Kategorie.
     * @return Persistierter Kategorie.
     */
    @PostMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> post(@AuthenticationPrincipal WbsUser user, @PathVariable("traegerId") Long traegerId,
            @RequestBody KategorieDtoImpl kategorie) {
        KategorieData newKategorie = postAction.perform(user, traegerId, kategorie);
        return new ResponseEntity<>(locationHeader(REL_KATEGORIE, newKategorie.getId()), HttpStatus.CREATED);
    }
}