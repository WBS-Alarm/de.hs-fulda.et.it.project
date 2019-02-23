package de.hsfulda.et.wbs.controller;

import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.service.UserAuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/users")
final class LoginController {

    private final UserAuthenticationService authentication;

    public LoginController(UserAuthenticationService authentication) {
        this.authentication = authentication;
    }

    @PostMapping("/login")
    String login(@RequestBody final Benutzer user) {
        return authentication
                .login(user.getUsername(), user.getPassword())
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }
}