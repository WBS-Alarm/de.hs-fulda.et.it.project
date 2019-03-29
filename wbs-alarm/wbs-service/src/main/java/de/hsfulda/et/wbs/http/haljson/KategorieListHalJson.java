package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.KategorieData;
import de.hsfulda.et.wbs.http.resource.KategorieListResource;

import java.util.List;
import java.util.stream.Collectors;

public class KategorieListHalJson extends HalJsonResource {

    public KategorieListHalJson(WbsUser user, List<KategorieData> kategorien) {
        addLink(Link.self(KategorieListResource.PATH));

        List<HalJsonResource> resources = kategorien.stream()
            .map(t -> new KategorieHalJson(user, t, false))
            .collect(Collectors.toList());

        addEmbeddedResources("elemente", resources);

    }
}
