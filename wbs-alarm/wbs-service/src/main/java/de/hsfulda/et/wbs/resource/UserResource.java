package de.hsfulda.et.wbs.resource;

import de.hsfulda.et.wbs.resource.core.HalJsonResource;
import de.hsfulda.et.wbs.resource.core.Link;
import de.hsfulda.et.wbs.security.User;

import static de.hsfulda.et.wbs.util.UriUtil.build;

public class UserResource extends HalJsonResource {

    public UserResource(final User user) {
        addLink(Link.self(build("/users/{username}", user.getUsername())));

        addProperty("username", user.getUsername());
    }
}
