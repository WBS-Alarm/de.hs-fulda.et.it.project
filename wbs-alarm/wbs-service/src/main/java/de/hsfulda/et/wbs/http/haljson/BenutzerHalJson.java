package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.User;
import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.security.entity.GrantedAuthority;
import de.hsfulda.et.wbs.security.haljson.AuthorityHalJson;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.List;
import java.util.stream.Collectors;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;

public class BenutzerHalJson extends HalJsonResource {

    private BenutzerHalJson(Benutzer benutzer, boolean embedded) {
        addBenutzerProperies(benutzer);
        if (embedded) {
            addEmbeddedResource("traeger", new TraegerHalJson(benutzer.getTraeger(), false));
        }
    }

    public static BenutzerHalJson of(Benutzer benutzer) {
        return new BenutzerHalJson(benutzer, true);
    }

    public static BenutzerHalJson ofNoEmbaddables(Benutzer benutzer) {
        return new BenutzerHalJson(benutzer, false);
    }

    public static BenutzerHalJson ofGrantedAuthorities(Benutzer benutzer, List<GrantedAuthority> granted) {
        BenutzerHalJson hal = new BenutzerHalJson(benutzer, true);

        hal.addEmbeddedResources("authorities", granted.stream()
                .map(g -> new AuthorityHalJson(g.getGroup(), benutzer))
                .collect(Collectors.toList()));

        return hal;
    }

    public static BenutzerHalJson ofCurrent(User benutzer) {
        BenutzerHalJson hal = new BenutzerHalJson(benutzer.getBenutzer(), true);
        hal.addProperty("authorities", benutzer.getAuthorities());
        return hal;
    }

    private void addBenutzerProperies(Benutzer benutzer) {
        String benutzerResource = UriUtil.build(CONTEXT_ROOT + "/benutzer/{id}", benutzer.getId());

        addLink(Link.self(benutzerResource));
        addLink(Link.create("delete", benutzerResource));
        addLink(Link.create("update", benutzerResource));

        addProperty("id", benutzer.getId());
        addProperty("username", benutzer.getUsername());
        addProperty("einkaeufer", benutzer.getEinkaeufer());
        addProperty("mail", benutzer.getMail());
    }
}
