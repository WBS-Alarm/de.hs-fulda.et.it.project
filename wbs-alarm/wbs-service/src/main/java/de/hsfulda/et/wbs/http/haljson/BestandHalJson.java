package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.http.resource.BestandListResource;
import de.hsfulda.et.wbs.util.UriUtil;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;

public class BestandHalJson extends HalJsonResource {

    public BestandHalJson(BestandData bestand) {
        this(bestand, true);
    }

    public BestandHalJson(BestandData bestand, boolean embedded) {
        addBestandProperties(bestand);

        if (embedded) {
            addEmbeddedResource("zielort", new ZielortHalJson(bestand.getZielort(), false));
            addEmbeddedResource("kategorie", new KategorieHalJson(bestand.getKategorie(), false));
            addEmbeddedResource("groesse", new GroesseHalJson(bestand.getGroesse(), false));
        }
    }

    private void addBestandProperties(BestandData bestand) {
        String bestandResource = UriUtil.build(CONTEXT_ROOT + "/bestand/{id}", bestand.getId());

        addLink(Link.self(bestandResource));
        addLink(Link.create("add", BestandListResource.PATH));
        addLink(Link.create("delete", bestandResource));
        addLink(Link.create("update", bestandResource));

        addProperty("id", bestand.getId());
        addProperty("anzahl", bestand.getAnzahl());
    }
}
