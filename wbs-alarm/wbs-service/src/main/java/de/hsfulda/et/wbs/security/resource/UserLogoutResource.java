package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.core.User;
import de.hsfulda.et.wbs.security.service.UserAuthenticationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Über dne Logout wird das Token eines angemeldeten Benutzers gelöscht und somit invalidiert.
 */
@RestController
@RequestMapping(UserLogoutResource.PATH)
public class UserLogoutResource {

    public static final String PATH = "/users/logout";

    private final UserAuthenticationService authentication;

    UserLogoutResource(UserAuthenticationService authentication) {
        this.authentication = authentication;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL')")
    boolean get(@AuthenticationPrincipal final User user) {
        authentication.logout(user);
        return true;
    }
}