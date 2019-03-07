package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.entity.Zielort;
import de.hsfulda.et.wbs.http.resource.ZielortListResource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ZielortListHalJson extends HalJsonResource {

    public ZielortListHalJson(Iterable<Zielort> traeger) {
        addLink(Link.self(ZielortListResource.PATH));

        List<HalJsonResource> resources = StreamSupport.stream(traeger.spliterator(), false)
                .map(t -> new ZielortHalJson(t, false))
                .collect(Collectors.toList());

        addEmbeddedResources("elemente", resources);

    }
}
