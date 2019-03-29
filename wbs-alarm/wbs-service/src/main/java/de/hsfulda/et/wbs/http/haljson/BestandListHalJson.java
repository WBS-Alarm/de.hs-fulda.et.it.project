package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.http.resource.BestandListResource;

import java.util.List;
import java.util.stream.Collectors;

public class BestandListHalJson extends HalJsonResource {

    public BestandListHalJson(List<BestandData> zielorte) {
        addLink(Link.self(BestandListResource.PATH));

        List<HalJsonResource> resources = zielorte.stream()
            .map(t -> new BestandHalJson(t, false))
            .collect(Collectors.toList());

        addEmbeddedResources("elemente", resources);

    }
}