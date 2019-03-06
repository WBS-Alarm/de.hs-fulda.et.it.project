package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.ResourceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static de.hsfulda.et.wbs.http.resource.SitemapResource.PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test des Sitemap Controllers.")
class SitemapResourceTest extends ResourceTest {

    @Autowired
    private SitemapResource controller;

    @DisplayName("Laden des Resource erfolgreich.")
    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @DisplayName("Lades der Sitemap.")
    @Test
    void sitemap() throws Exception {
        mockMvc.perform(get(PATH)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._links.self[0].href", is(PATH)))
            .andExpect(jsonPath("$._links.self[0].templated", is(false)));
    }
}