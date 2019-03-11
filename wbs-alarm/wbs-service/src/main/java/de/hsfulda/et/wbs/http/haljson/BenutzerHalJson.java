package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.security.entity.GrantedAuthority;
import de.hsfulda.et.wbs.security.haljson.AuthorityHalJson;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.List;
import java.util.stream.Collectors;

public class BenutzerHalJson extends HalJsonResource {

    public BenutzerHalJson(Benutzer benutzer) {
        this(benutzer, true);
    }

    public BenutzerHalJson(Benutzer benutzer, boolean embedded) {
        addBenutzerProperies(benutzer);
        if (embedded) {
            addEmbeddedResource("traeger", new TraegerHalJson(benutzer.getTraeger(), false));
        }
    }

    public BenutzerHalJson(Benutzer benutzer, List<GrantedAuthority> granted) {
        this(benutzer, true);

        addEmbeddedResources("authorities", granted.stream()
                .map(g -> new AuthorityHalJson(g.getGroup()))
                .collect(Collectors.toList()));

    }

    private void addBenutzerProperies(Benutzer benutzer) {
        String benutzerResource = UriUtil.build("/benutzer/{id}", benutzer.getId());

        addLink(Link.self(benutzerResource));
        addLink(Link.create("delete", benutzerResource));
        addLink(Link.create("update", benutzerResource));

        addProperty("id", benutzer.getId());
        addProperty("username", benutzer.getUsername());
        addProperty("einkaeufer", benutzer.getEinkaeufer());
        addProperty("mail", benutzer.getMail());
    }
}
