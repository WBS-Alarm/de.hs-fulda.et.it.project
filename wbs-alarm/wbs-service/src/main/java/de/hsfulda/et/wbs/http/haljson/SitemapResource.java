package de.hsfulda.et.wbs.http.haljson;

import de.hsfulda.et.wbs.Relations;
import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.core.Link;

public class SitemapResource extends HalJsonResource {

    public SitemapResource() {
        addLink(Link.self("/public/sitemap"));

        for (Relations relations : Relations.values()) {
            addLink(Link.create(relations.getRel(), relations.getHref()));
        }
    }
}
