package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.http.resource.TraegerListResource;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.stream.Collectors;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;

public class TraegerHalJson extends HalJsonResource {

    public TraegerHalJson(TraegerData traeger) {
        this(traeger, true);
    }

    public TraegerHalJson(TraegerData traeger, boolean embedded) {
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

            addEmbeddedResources("kategorien",
                traeger.getKategorien()
                    .stream()
                    .map(z -> new KategorieHalJson(z, false))
                    .collect(Collectors.toList()));
        }
    }

    private void addTraegerProperties(TraegerData traeger) {
        String traegerResource = UriUtil.build(CONTEXT_ROOT + "/traeger/{id}", traeger.getId());

        addLink(Link.self(traegerResource));
        addLink(Link.create("add", TraegerListResource.PATH));
        addLink(Link.create("delete", traegerResource));
        addLink(Link.create("update", traegerResource));

        addProperty("id", traeger.getId());
        addProperty("name", traeger.getName());
    }
}
