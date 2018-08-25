package de.hsfulda.et.wbs.controller;

import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.security.User;
import de.hsfulda.et.wbs.service.UserAuthenticationService;
import de.hsfulda.et.wbs.service.UserCrudService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PublicUsersController {

    @NonNull
    UserAuthenticationService authentication;

    @NonNull
    UserCrudService users;

    @PostMapping("/register")
    String register(@RequestBody final Benutzer user) {
        users
            .save(
                User
                    .builder()
                    .id(user.getUsername())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build()
            );

        return login(user);
    }

    @PostMapping("/login")
    String login(@RequestBody final Benutzer user) {
        return authentication
            .login(user.getUsername(), user.getPassword())
            .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }
}