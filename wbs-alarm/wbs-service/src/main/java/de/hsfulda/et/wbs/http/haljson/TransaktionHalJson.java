package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.Application;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.http.resource.TransaktionListResource;
import de.hsfulda.et.wbs.util.UriUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

public class TransaktionHalJson extends HalJsonResource {

    public TransaktionHalJson(WbsUser user, TransaktionData transaktion) {
        addTransaktionProperties(user, transaktion);

        addEmbeddedResource("benutzer",
                BenutzerHalJson.ofNoEmbaddables(user, transaktion.getBenutzer()));

        addEmbeddedResource("von",
                new ZielortHalJson(user, transaktion.getVon(), false));

        addEmbeddedResource("nach",
                new ZielortHalJson(user, transaktion.getNach(), false));

        addEmbeddedResources("positionen",
                transaktion.getPositionen()
                        .stream()
                        .map(z -> new PositionHalJson(user, z))
                        .collect(Collectors.toList()));

    }

    private void addTransaktionProperties(WbsUser user, TransaktionData transaktion) {
        String traegerResource = UriUtil.build(Application.CONTEXT_ROOT + "/transaktion/{id}", transaktion.getId());

        addLink(Link.self(traegerResource));

        if (user.isAccountant()) {
            addLink(Link.create("add", TransaktionListResource.PATH));
        }

        addProperty("id", transaktion.getId());
        addProperty("datum", toJavaDate(transaktion.getDatum()));
    }

    private Date toJavaDate(LocalDateTime datum) {
        if (datum == null) {
            return null;
        }
        return Date.from(datum.atZone(ZoneId.systemDefault()).toInstant());
    }
}
