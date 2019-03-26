package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.entity.Groesse;
import de.hsfulda.et.wbs.http.resource.GroesseListResource;

import java.util.List;
import java.util.stream.Collectors;

public class GroesseListHalJson extends HalJsonResource {

    public GroesseListHalJson(List<Groesse> groesssen) {
        addLink(Link.self(GroesseListResource.PATH));

        List<HalJsonResource> resources = groesssen.stream()
            .map(t -> new GroesseHalJson(t, false))
            .collect(Collectors.toList());

        addEmbeddedResources("elemente", resources);

    }
}
