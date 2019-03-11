package de.hsfulda.et.wbs.security.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.security.User;
import de.hsfulda.et.wbs.security.resource.UserResource;

import static de.hsfulda.et.wbs.util.UriUtil.build;

public class UserHalJson extends HalJsonResource {

    public UserHalJson(final User user) {
        addLink(Link.self(build(UserResource.PATH, user.getUsername())));

        addProperty("username", user.getUsername());
        addProperty("authorities", user.getAuthorities());
    }
}
