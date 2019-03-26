package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.entity.Kategorie;
import de.hsfulda.et.wbs.http.resource.KategorieListResource;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.stream.Collectors;

public class KategorieHalJson extends HalJsonResource {

    public KategorieHalJson(Kategorie kategorie) {
        this(kategorie, true);
    }

    public KategorieHalJson(Kategorie kategorie, boolean embedded) {
        addKategorieProperties(kategorie);

        if (embedded) {
            addEmbeddedResource("traeger", new TraegerHalJson(kategorie.getTraeger(), false));

            addEmbeddedResources("groessen",
                kategorie.getGroessen()
                    .stream()
                    .map(g -> new GroesseHalJson(g, false))
                    .collect(Collectors.toList()));
        }
    }

    private void addKategorieProperties(Kategorie kategorie) {
        String kategorieResource = UriUtil.build("/kategorie/{id}", kategorie.getId());

        addLink(Link.self(kategorieResource));
        addLink(Link.create("add", KategorieListResource.PATH));
        addLink(Link.create("delete", kategorieResource));
        addLink(Link.create("update", kategorieResource));

        addProperty("id", kategorie.getId());
        addProperty("name", kategorie.getName());
    }
}
