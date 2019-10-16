package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.http.resource.TraegerListResource;
import de.hsfulda.et.wbs.http.resource.TraegerResource;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.stream.Collectors;

public class TraegerHalJson extends HalJsonResource {

    public TraegerHalJson(WbsUser user, TraegerData traeger) {
        this(user, traeger, true);
    }

    public TraegerHalJson(WbsUser user, TraegerData traeger, boolean embedded) {
        addTraegerProperties(user, traeger);

        if (embedded) {
            addEmbeddedResources("benutzer", traeger.getBenutzer()
                    .stream()
                    .map(b -> BenutzerHalJson.ofNoEmbaddables(user, b))
                    .collect(Collectors.toList()));

            addEmbeddedResources("zielorte", traeger.getZielorte()
                    .stream()
                    .map(z -> new ZielortHalJson(user, z, false))
                    .collect(Collectors.toList()));

            addEmbeddedResources("kategorien", traeger.getKategorien()
                    .stream()
                    .map(z -> new KategorieHalJson(user, z, false))
                    .collect(Collectors.toList()));
        }
    }

    private void addTraegerProperties(WbsUser user, TraegerData traeger) {
        String traegerResource = UriUtil.build(TraegerResource.PATH, traeger.getId());

        addLink(Link.self(traegerResource));
        if (user.isAdmin()) {
            addLink(Link.create("delete", traegerResource));
            addLink(Link.create("add", TraegerListResource.PATH));
        }

        if (user.isTraegerManager()) {
            addLink(Link.create("update", traegerResource));
        }

        addProperty("id", traeger.getId());
        addProperty("name", traeger.getName());
    }
}
