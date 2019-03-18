package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.entity.Kategorie;
import de.hsfulda.et.wbs.http.resource.KategorieListResource;
import de.hsfulda.et.wbs.util.UriUtil;

public class KategorieHalJson extends HalJsonResource {

    public KategorieHalJson(Kategorie zielort) {
        this(zielort, true);
    }

    public KategorieHalJson(Kategorie zielort, boolean embedded) {
        addKategorieProperties(zielort);

        if (embedded) {
            addEmbeddedResource("traeger", new TraegerHalJson(zielort.getTraeger(), false));
        }
    }

    private void addKategorieProperties(Kategorie zielort) {
        String zielortResource = UriUtil.build("/kategorie/{id}", zielort.getId());

        addLink(Link.self(zielortResource));
        addLink(Link.create("add", KategorieListResource.PATH));
        addLink(Link.create("delete", zielortResource));
        addLink(Link.create("update", zielortResource));

        addProperty("id", zielort.getId());
        addProperty("name", zielort.getName());
    }
}
