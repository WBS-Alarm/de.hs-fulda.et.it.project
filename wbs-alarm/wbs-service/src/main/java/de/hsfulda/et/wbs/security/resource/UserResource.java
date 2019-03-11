package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.security.User;
import de.hsfulda.et.wbs.security.haljson.UserHalJson;
import de.hsfulda.et.wbs.security.service.UserCrudService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

/**
 * Über diese Resource kann ein User ermittelt werden.
 */
@RestController
@RequestMapping(UserResource.PATH)
public class UserResource {

    public static final String PATH = "/users/{username}";

    private final UserCrudService users;

    UserResource(UserCrudService users) {
        this.users = users;
    }

    @GetMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('READ_ALL')")
    HttpEntity<HalJsonResource> get(@PathVariable("username") String username, @AuthenticationPrincipal final User user) {
        Optional<User> byName = users.findByUsername(username);
        return byName.<HttpEntity<HalJsonResource>>map(user1 ->
            new HttpEntity<>(new UserHalJson(user1)))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}