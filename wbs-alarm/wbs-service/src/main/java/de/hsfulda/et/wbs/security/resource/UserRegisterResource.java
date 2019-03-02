package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.security.User;
import de.hsfulda.et.wbs.security.service.UserCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static de.hsfulda.et.wbs.security.resource.UserRegisterResource.PATH;
import static org.springframework.util.StringUtils.isEmpty;

@RestController
@RequestMapping(PATH)
public final class UserRegisterResource {

    public static final String PATH = "/users/register";

    private final UserCrudService users;

    UserRegisterResource(UserCrudService users) {
        this.users = users;
    }

    @PostMapping
    ResponseEntity<Void> post(@RequestBody final Benutzer user) {
        if (isEmpty(user.getUsername()) || isEmpty(user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<User> found = users.findByUsername(user.getUsername());
        if (found.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        users
            .register(
                User
                    .builder()
                    .id(user.getUsername())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build()
            );
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}