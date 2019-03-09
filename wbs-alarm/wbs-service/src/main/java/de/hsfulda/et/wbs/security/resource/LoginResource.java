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

/**
 * Über die Login Resource werden die Credentials angenommen und validiert. Bei erfolgreicher Authentifizierung wird dem
 * Anwender ein Token erstellt.
 */
@RestController
@RequestMapping(LoginResource.PATH)
public class LoginResource {

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