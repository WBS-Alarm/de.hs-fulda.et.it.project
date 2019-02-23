package de.hsfulda.et.wbs.controller;

import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.security.User;
import de.hsfulda.et.wbs.service.UserAuthenticationService;
import de.hsfulda.et.wbs.service.UserCrudService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
final class SecuredUsersController {

    private final UserAuthenticationService authentication;
    private final UserCrudService users;

    SecuredUsersController(UserAuthenticationService authentication, UserCrudService users) {
        this.authentication = authentication;
        this.users = users;
    }

    @PostMapping("/register")
    void register(@RequestBody final Benutzer user) {
        users
                .register(
                        User
                                .builder()
                                .id(user.getUsername())
                                .username(user.getUsername())
                                .password(user.getPassword())
                                .build()
                );
    }

    @GetMapping("/current")
    User getCurrent(@AuthenticationPrincipal final User user) {
        return user;
    }

    @GetMapping("/logout")
    boolean logout(@AuthenticationPrincipal final User user) {
        authentication.logout(user);
        return true;
    }
}