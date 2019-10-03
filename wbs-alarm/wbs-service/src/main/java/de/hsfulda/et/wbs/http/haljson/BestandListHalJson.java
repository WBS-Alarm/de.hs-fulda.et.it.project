package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.http.resource.BestandListResource;

import java.util.List;
import java.util.stream.Collectors;

public class BestandListHalJson extends HalJsonResource {

    public BestandListHalJson(WbsUser user, List<BestandData> bestaende) {
        addLink(Link.self(BestandListResource.PATH));

        List<HalJsonResource> resources = bestaende.stream()
                .map(t -> new BestandHalJson(user, t, false))
                .collect(Collectors.toList());

        addEmbeddedResources("elemente", resources);

    }
}
