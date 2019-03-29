package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.action.DeleteGrantedAuthorityAction;
import de.hsfulda.et.wbs.action.GrantAuthorityAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

/**
 * Über diese Resource werden die Rechte an einen Benutzer vergeben.
 */
@RestController
@RequestMapping(GrantAuthorityResource.PATH)
public class GrantAuthorityResource {

    public static final String PATH = CONTEXT_ROOT + "/authority/{authorityId}/grant/{benutzerId}";

    private final GrantAuthorityAction postAction;
    private final DeleteGrantedAuthorityAction deleteAction;

    public GrantAuthorityResource(GrantAuthorityAction postAction, DeleteGrantedAuthorityAction deleteAction) {
        this.postAction = postAction;
        this.deleteAction = deleteAction;
    }

    @PostMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> post(
            @PathVariable("authorityId") Long authorityId,
            @PathVariable("benutzerId") Long benutzerId) {

        postAction.perform(authorityId, benutzerId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> delete(
            @PathVariable("authorityId") Long authorityId,
            @PathVariable("benutzerId") Long benutzerId) {

        deleteAction.perform(authorityId, benutzerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}