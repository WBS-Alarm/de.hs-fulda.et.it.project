package de.hsfulda.et.wbs.controller.security;

import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.service.UserAuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static de.hsfulda.et.wbs.controller.security.LoginController.LOGIN_PATH;

@RestController
@RequestMapping(LOGIN_PATH)
public final class LoginController {

    public static final String LOGIN_PATH = "/public/users/login";

    private final UserAuthenticationService authentication;

    public LoginController(UserAuthenticationService authentication) {
        this.authentication = authentication;
    }

    @PostMapping()
    String login(@RequestBody final Benutzer user) {
        return authentication
            .login(user.getUsername(), user.getPassword())
            .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }
}