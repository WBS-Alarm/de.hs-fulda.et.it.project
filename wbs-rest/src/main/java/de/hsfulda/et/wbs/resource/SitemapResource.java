package de.hsfulda.et.wbs.resource;

import de.hsfulda.et.wbs.controller.Relations;
import de.hsfulda.et.wbs.resource.core.HalJsonResource;
import de.hsfulda.et.wbs.resource.core.Link;

public class SitemapResource extends HalJsonResource {

    public SitemapResource() {
        addLink(Link.self("/public/sitemap"));

        for (Relations relations : Relations.values()) {
            addLink(Link.create(relations.getRel(), relations.getHref()));
        }
    }
}
