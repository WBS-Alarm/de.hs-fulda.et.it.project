package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.action.transaktion.AddEinkaufAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.core.dto.PositionDto;
import de.hsfulda.et.wbs.http.resource.dto.PositionDtoImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;
import static de.hsfulda.et.wbs.util.HeaderUtil.locationHeader;

/**
 * Diese Hilfsressource ermöglicht einen verkürzten Eeinkauf. Dabei werden nur die Positionen angegeben, die ins
 * Lager aufgenommen werden sollen.
 */
@RestController
@RequestMapping(EinkaufResource.PATH)
public class EinkaufResource {

    private final AddEinkaufAction postAction;

    public static final String PATH = CONTEXT_ROOT + "/traeger/{traegerId}/einkauf";

    public EinkaufResource(AddEinkaufAction postAction) {
        this.postAction = postAction;
    }

    /**
     * Erstellt eine Transaktion von Wareneingang zu Lager im System.
     *
     * @param positionen Positionen, die vom Wareneinang ins Lager verbucht werden.
     * @return Erstellte Transaktion.
     */
    @PostMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('ACCOUNTANT')")
    HttpEntity<HalJsonResource> post(@AuthenticationPrincipal WbsUser user, @PathVariable("traegerId") Long traegerId,
            @RequestBody List<PositionDtoImpl> positionen) {
        List<PositionDto> dtos = positionen.stream()
                .map(p -> (PositionDto) p)
                .collect(Collectors.toList());

        TransaktionData newTransaktion = postAction.perform(user, traegerId, dtos);
        MultiValueMap<String, String> header = locationHeader(TransaktionResource.PATH, newTransaktion.getId());
        return new ResponseEntity<>(header, HttpStatus.CREATED);
    }
}
