package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.security.service.UserAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static de.hsfulda.et.wbs.security.resource.LoginResource.PATH;

@RestController
@RequestMapping(PATH)
public final class LoginResource {

    public static final String PATH = "/public/users/login";

    private final UserAuthenticationService authentication;

    public LoginResource(UserAuthenticationService authentication) {
        this.authentication = authentication;
    }

    @PostMapping
    ResponseEntity<String> post(@RequestBody final Benutzer user) {
        Optional<String> login = authentication
            .login(user.getUsername(), user.getPassword());
        return login.map(token -> new ResponseEntity<>(token, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>("invalid post and/or password", HttpStatus.FORBIDDEN));
    }
}