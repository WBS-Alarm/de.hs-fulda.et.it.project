package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.ZielortData;
import de.hsfulda.et.wbs.http.resource.ZielortListResource;
import de.hsfulda.et.wbs.util.UriUtil;

import java.util.List;
import java.util.stream.Collectors;

public class ZielortListHalJson extends HalJsonResource {

    public ZielortListHalJson(WbsUser user, List<ZielortData> zielorte, Long traegerId) {
        addLink(Link.self(UriUtil.build(ZielortListResource.PATH, traegerId)));

        List<HalJsonResource> resources = zielorte.stream()
                .map(t -> new ZielortHalJson(user, t, false))
                .collect(Collectors.toList());

        addEmbeddedResources("elemente", resources);

    }
}
