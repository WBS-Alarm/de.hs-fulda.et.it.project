package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.core.data.ZielortData;
import de.hsfulda.et.wbs.util.UriUtil;

import static de.hsfulda.et.wbs.Relations.REL_BESTAND;
import static de.hsfulda.et.wbs.Relations.REL_BESTAND_LIST;

public class BestandHalJson extends HalJsonResource {

    public BestandHalJson(WbsUser user, BestandData bestand) {
        this(user, bestand, true);
    }

    public BestandHalJson(WbsUser user, BestandData bestand, boolean embedded) {
        addBestandProperties(user, bestand);

        addEmbeddedResource("kategorie", new KategorieHalJson(user, bestand.getKategorie(), false));
        addEmbeddedResource("groesse", new GroesseHalJson(user, bestand.getGroesse(), false));

        if (embedded) {
            addEmbeddedResource("zielort", new ZielortHalJson(user, bestand.getZielort(), false));
        }
    }

    private void addBestandProperties(WbsUser user, BestandData bestand) {
        String bestandResource = UriUtil.build(REL_BESTAND, bestand.getId());

        addLink(Link.self(bestandResource));
        if (user.isTraegerManager()) {
            ZielortData zielort = bestand.getZielort();
            addLink(Link.create("add", UriUtil.build(REL_BESTAND_LIST, zielort.getId())));
            addLink(Link.create("delete", bestandResource));
            addLink(Link.create("update", bestandResource));
        }

        addProperty("id", bestand.getId());
        addProperty("anzahl", bestand.getAnzahl());
    }
}
