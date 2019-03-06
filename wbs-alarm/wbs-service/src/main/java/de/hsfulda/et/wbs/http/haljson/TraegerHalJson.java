package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.http.resource.TraegerListResource;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.stream.Collectors;

public class TraegerHalJson extends HalJsonResource {

    public TraegerHalJson(Traeger traeger) {
        String traegerResource = UriUtil.build("/traeger/{id}", traeger.getId());

        addLink(Link.self(traegerResource));
        addLink(Link.create("add", TraegerListResource.PATH));
        addLink(Link.create("delete", traegerResource));
        addLink(Link.create("update", traegerResource));

        addProperty("id", traeger.getId());
        addProperty("name", traeger.getName());

        addEmbeddedResources("benutzer",
                traeger.getBenutzer()
                        .stream()
                        .map(b -> new BenutzerHalJson(b, false))
                        .collect(Collectors.toList()));

        // TODO: embedded Kategorien
        // TODO: embedded Zielorte
    }
}
