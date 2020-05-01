package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.core.data.KategorieData;
import de.hsfulda.et.wbs.util.UriUtil;

import static de.hsfulda.et.wbs.Relations.REL_GROESSE;
import static de.hsfulda.et.wbs.Relations.REL_GROESSE_LIST;

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
        String groesseResource = UriUtil.build(REL_GROESSE, groesse.getId());

        addLink(Link.self(groesseResource));
        if (user.isTraegerManager()) {
            KategorieData kategorie = groesse.getKategorie();
            addLink(Link.create("add", UriUtil.build(REL_GROESSE_LIST, kategorie.getId())));
            addLink(Link.create("delete", groesseResource));
            addLink(Link.create("update", groesseResource));
        }

        addProperty("id", groesse.getId());
        addProperty("name", groesse.getName());
        addProperty("bestandsgrenze", groesse.getBestandsgrenze());
    }
}
