package de.hsfulda.et.wbs.controller.security;

import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.service.UserAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
    ResponseEntity<String> login(@RequestBody final Benutzer user) {
        Optional<String> login = authentication
            .login(user.getUsername(), user.getPassword());
        if (login.isPresent()) {
            return new ResponseEntity<>(login.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("invalid login and/or password", HttpStatus.FORBIDDEN);
    }
}