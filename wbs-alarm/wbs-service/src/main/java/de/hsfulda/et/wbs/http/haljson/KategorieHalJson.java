package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.data.KategorieData;
import de.hsfulda.et.wbs.http.resource.KategorieListResource;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.stream.Collectors;

public class KategorieHalJson extends HalJsonResource {

    public KategorieHalJson(KategorieData kategorie) {
        this(kategorie, true);
    }

    public KategorieHalJson(KategorieData kategorie, boolean embedded) {
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

    private void addKategorieProperties(KategorieData kategorie) {
        String kategorieResource = UriUtil.build("/kategorie/{id}", kategorie.getId());

        addLink(Link.self(kategorieResource));
        addLink(Link.create("add", KategorieListResource.PATH));
        addLink(Link.create("delete", kategorieResource));
        addLink(Link.create("update", kategorieResource));

        addProperty("id", kategorie.getId());
        addProperty("name", kategorie.getName());
    }
}
