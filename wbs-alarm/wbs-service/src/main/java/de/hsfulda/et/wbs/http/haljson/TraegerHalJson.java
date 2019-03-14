package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.http.resource.TraegerListResource;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.stream.Collectors;

public class TraegerHalJson extends HalJsonResource {

    public TraegerHalJson(Traeger traeger) {
        this(traeger, true);
    }

    public TraegerHalJson(Traeger traeger, boolean embedded) {
        addTraegerProperties(traeger);

        if (embedded) {
            addEmbeddedResources("benutzer",
                    traeger.getBenutzer()
                            .stream()
                            .map(BenutzerHalJson::ofNoEmbaddables)
                            .collect(Collectors.toList()));

            addEmbeddedResources("zielorte",
                    traeger.getZielorte()
                            .stream()
                            .map(z -> new ZielortHalJson(z, false))
                            .collect(Collectors.toList()));
            // TODO: embedded Kategorien
        }
    }

    private void addTraegerProperties(Traeger traeger) {
        String traegerResource = UriUtil.build("/traeger/{id}", traeger.getId());

        addLink(Link.self(traegerResource));
        addLink(Link.create("add", TraegerListResource.PATH));
        addLink(Link.create("delete", traegerResource));
        addLink(Link.create("update", traegerResource));

        addProperty("id", traeger.getId());
        addProperty("name", traeger.getName());
    }
}
