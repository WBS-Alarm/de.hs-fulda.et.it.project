package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.http.haljson.SitemapHalJson;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

/**
 * Die Sitemap stellt eine Übersicht über alle Resourcen dar. über diese kann relativ zum Context-Root die URL einer
 * Resource ermittelt werden.
 */
@RestController
@RequestMapping(SitemapResource.PATH)
public class SitemapResource {

    public static final String PATH = CONTEXT_ROOT + "/public/sitemap";

    /**
     * Erstellt aus den Relationen die Sitemap.
     *
     * @return Sitemap über die angebotenen Resourcen.
     */
    @GetMapping(produces = HAL_JSON)
    HttpEntity<HalJsonResource> sitemap() {
        return new HttpEntity<>(new SitemapHalJson());
    }
}