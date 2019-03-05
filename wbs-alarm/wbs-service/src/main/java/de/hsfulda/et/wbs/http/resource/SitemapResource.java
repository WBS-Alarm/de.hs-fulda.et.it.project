package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.http.haljson.SitemapHalJson;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static de.hsfulda.et.wbs.http.resource.SitemapResource.PATH;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

@RestController
@RequestMapping(PATH)
public class SitemapResource {

    public static final String PATH = "/public/sitemap";

    @GetMapping(produces = HAL_JSON)
    HttpEntity<HalJsonResource> sitemap() {
        return new HttpEntity<>(new SitemapHalJson());
    }
}