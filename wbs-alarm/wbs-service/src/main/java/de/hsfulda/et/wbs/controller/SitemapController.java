package de.hsfulda.et.wbs.controller;

import de.hsfulda.et.wbs.resource.SitemapResource;
import de.hsfulda.et.wbs.resource.core.HalJsonResource;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static de.hsfulda.et.wbs.controller.SitemapController.SITEMAP_PATH;
import static de.hsfulda.et.wbs.resource.core.HalJsonResource.HAL_JSON;

@RestController
@RequestMapping(SITEMAP_PATH)
final class SitemapController {

    static final String SITEMAP_PATH = "/public/sitemap";

    @GetMapping(produces = HAL_JSON)
    HttpEntity<HalJsonResource> sitemap() {
        return new HttpEntity<>(new SitemapResource());
    }
}