package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.http.resource.TraegerListResource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TraegerListHalJson extends HalJsonResource {

    public TraegerListHalJson(WbsUser user, Iterable<TraegerData> traeger) {
        addLink(Link.self(TraegerListResource.PATH));

        List<HalJsonResource> resources = StreamSupport.stream(traeger.spliterator(), false)
                .map(t -> new TraegerHalJson(user, t, false))
                .collect(Collectors.toList());

        addEmbeddedResources("elemente", resources);
    }
}
