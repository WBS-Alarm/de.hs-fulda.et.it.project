package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.entity.Kategorie;
import de.hsfulda.et.wbs.http.resource.KategorieListResource;

import java.util.List;
import java.util.stream.Collectors;

public class KategorieListHalJson extends HalJsonResource {

    public KategorieListHalJson(List<Kategorie> zielorte) {
        addLink(Link.self(KategorieListResource.PATH));

        List<HalJsonResource> resources = zielorte.stream()
            .map(t -> new KategorieHalJson(t, false))
            .collect(Collectors.toList());

        addEmbeddedResources("elemente", resources);

    }
}
