package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.core.data.GrantedAuthorityData;
import de.hsfulda.et.wbs.security.haljson.AuthorityHalJson;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.List;
import java.util.stream.Collectors;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;

public class BenutzerHalJson extends HalJsonResource {

    private BenutzerHalJson(BenutzerData benutzer, boolean embedded) {
        addBenutzerProperies(benutzer);
        if (embedded) {
            addEmbeddedResource("traeger", new TraegerHalJson(benutzer.getTraeger(), false));
        }
    }

    public static BenutzerHalJson of(BenutzerData benutzer) {
        return new BenutzerHalJson(benutzer, true);
    }

    public static BenutzerHalJson ofNoEmbaddables(BenutzerData benutzer) {
        return new BenutzerHalJson(benutzer, false);
    }

    public static BenutzerHalJson ofGrantedAuthorities(BenutzerData benutzer, List<GrantedAuthorityData> granted) {
        BenutzerHalJson hal = new BenutzerHalJson(benutzer, true);

        hal.addEmbeddedResources("authorities", granted.stream()
            .map(g -> new AuthorityHalJson(g.getGroup(), benutzer))
            .collect(Collectors.toList()));

        return hal;
    }

    public static BenutzerHalJson ofCurrent(WbsUser benutzer) {
        BenutzerHalJson hal = new BenutzerHalJson(benutzer.getBenutzer(), true);
        hal.addProperty("authorities", benutzer.getAuthorities());
        return hal;
    }

    private void addBenutzerProperies(BenutzerData benutzer) {
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
