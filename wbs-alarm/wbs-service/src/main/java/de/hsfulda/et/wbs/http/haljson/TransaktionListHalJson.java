package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.http.resource.TransaktionListResource;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.List;
import java.util.stream.Collectors;

public class TransaktionListHalJson extends HalJsonResource {

    public TransaktionListHalJson(WbsUser user, List<TransaktionData> transaktionen, Long traegerId) {
        addLink(Link.self(UriUtil.build(TransaktionListResource.PATH, traegerId)));

        List<HalJsonResource> resources = transaktionen.stream()
                .map(t -> new TransaktionHalJson(user, t))
                .collect(Collectors.toList());

        addEmbeddedResources("elemente", resources);

    }
}
