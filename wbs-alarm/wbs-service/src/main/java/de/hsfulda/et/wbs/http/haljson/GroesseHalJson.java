package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.http.resource.GroesseListResource;
import de.hsfulda.et.wbs.util.UriUtil;

public class GroesseHalJson extends HalJsonResource {

    public GroesseHalJson(WbsUser user, GroesseData groesse) {
        this(user, groesse, true);
    }

    public GroesseHalJson(WbsUser user, GroesseData groesse, boolean embedded) {
        addGroesseProperties(user, groesse);

        if (embedded) {
            addEmbeddedResource("kategorie", new KategorieHalJson(user, groesse.getKategorie(), false));
        }
    }

    private void addGroesseProperties(WbsUser user, GroesseData groesse) {
        String groesseResource = UriUtil.build("/kategorie/{id}", groesse.getId());

        addLink(Link.self(groesseResource));
        if (user.isTraegerManager()) {
            addLink(Link.create("add", GroesseListResource.PATH));
            addLink(Link.create("delete", groesseResource));
            addLink(Link.create("update", groesseResource));
        }

        addProperty("id", groesse.getId());
        addProperty("name", groesse.getName());
    }
}
