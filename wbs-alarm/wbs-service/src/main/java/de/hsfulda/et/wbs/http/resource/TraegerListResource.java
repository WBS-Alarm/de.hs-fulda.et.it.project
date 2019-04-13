package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.action.traeger.CreateTraegerAction;
import de.hsfulda.et.wbs.action.traeger.GetTraegerListAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.http.haljson.TraegerListHalJson;
import de.hsfulda.et.wbs.http.resource.dto.TraegerDtoImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;
import static de.hsfulda.et.wbs.util.HeaderUtil.locationHeader;

/**
 * Über die Träger Listen Resource können ale Träger aufgelistet werden und neue Träger hinzugefügt werden.
 */
@RestController
@RequestMapping(TraegerListResource.PATH)
public class TraegerListResource {

    public static final String PATH = CONTEXT_ROOT + "/traeger";

    private final GetTraegerListAction getAction;
    private final CreateTraegerAction postAction;

    public TraegerListResource(GetTraegerListAction getAction, CreateTraegerAction postAction) {
        this.getAction = getAction;
        this.postAction = postAction;
    }

    /**
     * Ermittelt alle Träger der Anwendung. Welche Träger es gibt, stellt kein besonderes Problem dar, da die
     * Informationen innerhalb eines Trägers nur über die den berechtigten Benutzer eingesehen werden können.
     *
     * @return alle vorhandenen Träger.
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal WbsUser user) {
        return new HttpEntity<>(new TraegerListHalJson(user, getAction.perform()));
    }

    /**
     * Erstellt einen Träger im System. Hierbei werden noch Standard-Zielorte dazu erstellt, die vom Anwendern nicht
     * entfernt werden können. Nur ein Administrator kann einen Träger im System anlegen.
     *
     * @param traeger Neuer Träger.
     * @return Erstellter Träger.
     */
    @PostMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('ADMIN')")
    HttpEntity<HalJsonResource> post(@AuthenticationPrincipal WbsUser user, @RequestBody TraegerDtoImpl traeger) {
        TraegerData newTraeger = postAction.perform(traeger);
        MultiValueMap<String, String> header = locationHeader(TraegerResource.PATH, newTraeger.getId());
        return new ResponseEntity<>(header, HttpStatus.CREATED);
    }
}