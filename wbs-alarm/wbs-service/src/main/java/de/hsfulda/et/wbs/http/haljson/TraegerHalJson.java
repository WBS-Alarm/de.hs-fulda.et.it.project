package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.http.resource.TraegerListResource;
import de.hsfulda.et.wbs.util.UriUtil;

public class TraegerHalJson extends HalJsonResource {

    public TraegerHalJson(Traeger traeger) {
        String traegerResource = UriUtil.build("/traeger/{id}", traeger.getId());

        addLink(Link.self(traegerResource));
        addLink(Link.create("add", TraegerListResource.PATH));
        addLink(Link.create("delete", traegerResource));
        addLink(Link.create("update", traegerResource));

        addProperty("id", traeger.getId());
        addProperty("name", traeger.getName());

        // TODO: embedded Kategorien
        // TODO: embedded Zielorte
        // TODO: embedded Benutzer
    }
}
