package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.action.transaktion.AddTransaktionAction;
import de.hsfulda.et.wbs.action.transaktion.GetTransaktionListAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.http.haljson.TransaktionListHalJson;
import de.hsfulda.et.wbs.http.resource.dto.TransaktionDtoImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.Relations.REL_TRANSAKTION;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;
import static de.hsfulda.et.wbs.util.HeaderUtil.locationHeader;

/**
 * Über diese Resource können bereits erstellte Transaktionen angezeigt und neue Transaktionen hinzugefügt
 * werden.
 */
@RestController
@RequestMapping(TransaktionListResource.PATH)
public class TransaktionListResource {

    private final GetTransaktionListAction getAction;
    private final AddTransaktionAction postAction;

    public static final String PATH = CONTEXT_ROOT + "/traeger/{traegerId}/transaktion";

    public TransaktionListResource(GetTransaktionListAction getAction, AddTransaktionAction postAction) {
        this.getAction = getAction;
        this.postAction = postAction;
    }

    /**
     * Ermittelt Transaktionen der letzten 5 Tage für den angemeldeten Benutzer.
     *
     * @return gefundenen Träger. Anderfalls 404
     */
    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal WbsUser user, @PathVariable("traegerId") Long traegerId,
            @RequestParam(name = "page") Optional<Integer> offset,
            @RequestParam(name = "size") Optional<Integer> size) {
        int realPage = offset.orElse(0);
        int realSize = size.orElse(20);

        return new HttpEntity<>(
                new TransaktionListHalJson(user, getAction.perform(user, traegerId, realPage, realSize), traegerId,
                        realPage, realSize));
    }

    /**
     * Erstellt eine Transaktion im System.
     *
     * @param transaktion Neue Transaktion.
     * @return Erstellte Transaktion.
     */
    @PostMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('ACCOUNTANT')")
    HttpEntity<HalJsonResource> post(@AuthenticationPrincipal WbsUser user,
            @RequestBody TransaktionDtoImpl transaktion) {
        TransaktionData newTransaktion = postAction.perform(user, transaktion);
        return new ResponseEntity<>(locationHeader(REL_TRANSAKTION, newTransaktion.getId()), HttpStatus.CREATED);
    }
}
