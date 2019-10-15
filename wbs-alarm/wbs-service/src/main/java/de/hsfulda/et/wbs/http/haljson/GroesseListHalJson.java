package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.http.resource.GroesseListResource;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.List;
import java.util.stream.Collectors;

public class GroesseListHalJson extends HalJsonResource {

    public GroesseListHalJson(WbsUser user, List<GroesseData> groesssen, Long kategorieId) {
        addLink(Link.self(UriUtil.build(GroesseListResource.PATH, kategorieId)));

        List<HalJsonResource> resources = groesssen.stream()
                .map(t -> new GroesseHalJson(user, t, false))
                .collect(Collectors.toList());

        addEmbeddedResources("elemente", resources);

    }
}
