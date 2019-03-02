package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.security.User;
import de.hsfulda.et.wbs.security.service.UserCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static de.hsfulda.et.wbs.security.resource.UserRegisterResource.PATH;

@RestController
@RequestMapping(PATH)
public final class UserRegisterResource {

    public static final String PATH = "/users/register";

    private final UserCrudService users;

    UserRegisterResource(UserCrudService users) {
        this.users = users;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void post(@RequestBody final Benutzer user) {
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
}