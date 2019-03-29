package de.hsfulda.et.wbs.security.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.data.AuthorityData;
import de.hsfulda.et.wbs.security.resource.AuthorityResource;

import java.util.List;
import java.util.stream.Collectors;

public class AuthoritiesHalJson extends HalJsonResource {

    public AuthoritiesHalJson(final List<AuthorityData> authorities) {
        addLink(Link.self(AuthorityResource.PATH));

        addEmbeddedResources("authorities", authorities.stream()
            .map(AuthorityHalJson::new)
            .collect(Collectors.toList()));
    }
}
