package de.hsfulda.et.wbs.controller.security;

import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.resource.UserResource;
import de.hsfulda.et.wbs.resource.core.HalJsonResource;
import de.hsfulda.et.wbs.security.User;
import de.hsfulda.et.wbs.service.UserAuthenticationService;
import de.hsfulda.et.wbs.service.UserCrudService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static de.hsfulda.et.wbs.resource.core.HalJsonResource.HAL_JSON;

@RestController
public final class UsersController {

    public static final String PATH_REGISTER = "/users/register";
    public static final String PATH_CURRENT = "/users/current";
    public static final String PATH_LOGOUT = "/users/logout";
    public static final String PATH_USER = "/users/{username}";

    private final UserAuthenticationService authentication;
    private final UserCrudService users;

    UsersController(UserAuthenticationService authentication, UserCrudService users) {
        this.authentication = authentication;
        this.users = users;
    }

    @PostMapping(PATH_REGISTER)
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

    @GetMapping(value = PATH_CURRENT, produces = HAL_JSON)
    HttpEntity<HalJsonResource> getCurrent(@AuthenticationPrincipal final User user) {
        return new HttpEntity<>(new UserResource(user));
    }

    @GetMapping(PATH_LOGOUT)
    boolean logout(@AuthenticationPrincipal final User user) {
        authentication.logout(user);
        return true;
    }

    @GetMapping(value = PATH_USER, produces = HAL_JSON)
    HttpEntity<HalJsonResource> getUserByName(@PathVariable("username") String username, @AuthenticationPrincipal final User user) {
        Optional<User> byName = users.findByUsername(username);
        if (byName.isPresent()) {
            return new HttpEntity<>(new UserResource(byName.get()));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}