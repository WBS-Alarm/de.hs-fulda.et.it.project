package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.http.resource.GroesseListResource;
import de.hsfulda.et.wbs.util.UriUtil;

public class GroesseHalJson extends HalJsonResource {

    public GroesseHalJson(GroesseData groesse) {
        this(groesse, true);
    }

    public GroesseHalJson(GroesseData groesse, boolean embedded) {
        addGroesseProperties(groesse);

        if (embedded) {
            addEmbeddedResource("kategorie", new KategorieHalJson(groesse.getKategorie(), false));
        }
    }

    private void addGroesseProperties(GroesseData groesse) {
        String groesseResource = UriUtil.build("/kategorie/{id}", groesse.getId());

        addLink(Link.self(groesseResource));
        addLink(Link.create("add", GroesseListResource.PATH));
        addLink(Link.create("delete", groesseResource));
        addLink(Link.create("update", groesseResource));

        addProperty("id", groesse.getId());
        addProperty("name", groesse.getName());
    }
}
