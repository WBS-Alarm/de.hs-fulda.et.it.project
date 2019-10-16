package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.KategorieData;
import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.http.resource.KategorieListResource;
import de.hsfulda.et.wbs.http.resource.KategorieResource;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.stream.Collectors;

public class KategorieHalJson extends HalJsonResource {

    public KategorieHalJson(WbsUser user, KategorieData kategorie) {
        this(user, kategorie, true);
    }

    public KategorieHalJson(WbsUser user, KategorieData kategorie, boolean embedded) {
        addKategorieProperties(user, kategorie);

        if (embedded) {
            addEmbeddedResource("traeger", new TraegerHalJson(user, kategorie.getTraeger(), false));

            addEmbeddedResources("groessen", kategorie.getGroessen()
                    .stream()
                    .map(g -> new GroesseHalJson(user, g, false))
                    .collect(Collectors.toList()));
        }
    }

    private void addKategorieProperties(WbsUser user, KategorieData kategorie) {
        String kategorieResource = UriUtil.build(KategorieResource.PATH, kategorie.getId());

        addLink(Link.self(kategorieResource));
        if (user.isTraegerManager()) {
            TraegerData traeger = kategorie.getTraeger();
            addLink(Link.create("add", UriUtil.build(KategorieListResource.PATH, traeger.getId())));
            addLink(Link.create("delete", kategorieResource));
            addLink(Link.create("update", kategorieResource));
        }

        addProperty("id", kategorie.getId());
        addProperty("name", kategorie.getName());
    }
}
