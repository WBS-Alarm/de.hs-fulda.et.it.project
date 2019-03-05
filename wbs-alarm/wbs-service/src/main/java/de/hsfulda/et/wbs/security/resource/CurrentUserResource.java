package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.security.haljson.UserResource;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.security.User;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;
import static de.hsfulda.et.wbs.security.resource.CurrentUserResource.PATH;

@RestController
@RequestMapping(PATH)
public class CurrentUserResource {

    public static final String PATH = "/users/current";

    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@AuthenticationPrincipal final User user) {
        return new HttpEntity<>(new UserResource(user));
    }
}