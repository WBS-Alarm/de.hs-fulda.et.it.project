package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.http.resource.TraegerListResource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TraegerListHalJson extends HalJsonResource {

    public TraegerListHalJson(Iterable<Traeger> traeger) {
        addLink(Link.self(TraegerListResource.PATH));

        List<HalJsonResource> resources = StreamSupport.stream(traeger.spliterator(), false)
                .map(t -> new TraegerHalJson(t, false))
                .collect(Collectors.toList());

        addEmbeddedResources("elemente", resources);

    }
}
