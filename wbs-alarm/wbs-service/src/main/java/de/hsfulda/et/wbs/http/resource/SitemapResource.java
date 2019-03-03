package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static de.hsfulda.et.wbs.http.resource.SitemapResource.SITEMAP_PATH;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

@RestController
@RequestMapping(SITEMAP_PATH)
final class SitemapResource {

    static final String SITEMAP_PATH = "/public/sitemap";

    @GetMapping(produces = HAL_JSON)
    HttpEntity<HalJsonResource> sitemap() {
        return new HttpEntity<>(new de.hsfulda.et.wbs.http.haljson.SitemapResource());
    }
}