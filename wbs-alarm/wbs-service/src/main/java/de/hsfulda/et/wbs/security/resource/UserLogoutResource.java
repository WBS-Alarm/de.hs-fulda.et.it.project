package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.security.User;
import de.hsfulda.et.wbs.security.service.UserAuthenticationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static de.hsfulda.et.wbs.security.resource.UserLogoutResource.PATH;

@RestController
@RequestMapping(PATH)
public class UserLogoutResource {

    public static final String PATH = "/users/logout";

    private final UserAuthenticationService authentication;

    UserLogoutResource(UserAuthenticationService authentication) {
        this.authentication = authentication;
    }

    @GetMapping
    boolean get(@AuthenticationPrincipal final User user) {
        authentication.logout(user);
        return true;
    }
}