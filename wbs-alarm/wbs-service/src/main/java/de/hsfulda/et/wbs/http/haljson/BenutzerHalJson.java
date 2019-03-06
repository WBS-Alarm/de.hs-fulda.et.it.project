package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.util.UriUtil;

public class BenutzerHalJson extends HalJsonResource {

    public BenutzerHalJson(Benutzer benutzer) {
        this(benutzer, true);
    }

    public BenutzerHalJson(Benutzer benutzer, boolean embedded) {
        addBenutzerProperies(benutzer);
        if (embedded) {
            addEmbeddedResource("traeger", new TraegerHalJson(benutzer.getTraeger()));
        }
    }

    private void addBenutzerProperies(Benutzer benutzer) {
        String traegerResource = UriUtil.build("/benutzer/{id}", benutzer.getId());

        addLink(Link.self(traegerResource));
        addLink(Link.create("delete", traegerResource));
        addLink(Link.create("update", traegerResource));

        addProperty("id", benutzer.getId());
        addProperty("username", benutzer.getUsername());
        addProperty("einkaeufer", benutzer.getEinkaeufer());
        addProperty("mail", benutzer.getMail());
    }
}
