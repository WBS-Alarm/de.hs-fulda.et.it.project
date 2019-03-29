package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.action.GetAuthorityListAction;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.security.haljson.AuthoritiesHalJson;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

/**
 * Über diese Resource werden die Rechte ermittelt die einem Benutzer vergeben werden können.
 */
@RestController
@RequestMapping(AuthorityResource.PATH)
public class AuthorityResource {

    public static final String PATH = CONTEXT_ROOT + "/authorities";

    private final GetAuthorityListAction getActon;

    public AuthorityResource(GetAuthorityListAction getActon) {
        this.getActon = getActon;
    }

    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal WbsUser user) {
        return new HttpEntity<>(new AuthoritiesHalJson(user, getActon.perform()));
    }
}