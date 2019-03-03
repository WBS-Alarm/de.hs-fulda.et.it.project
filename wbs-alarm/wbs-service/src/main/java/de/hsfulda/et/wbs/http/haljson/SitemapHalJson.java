package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.Relations;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;
import de.hsfulda.et.wbs.http.resource.SitemapResource;

public class SitemapHalJson extends HalJsonResource {

    public SitemapHalJson() {
        addLink(Link.self(SitemapResource.PATH));

        for (Relations relations : Relations.values()) {
            addLink(Link.create(relations.getRel(), relations.getHref()));
        }
    }
}
