package de.hsfulda.et.wbs.controller;

import de.hsfulda.et.wbs.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static de.hsfulda.et.wbs.controller.SitemapController.SITEMAP_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test des Sitemap Controllers.")
class SitemapControllerTest extends ControllerTest {

    @Autowired
    private SitemapController controller;


    @DisplayName("Laden des Controllers erfolgreich.")
    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @DisplayName("Lades der Sitemap.")
    @Test
    void sitemap() throws Exception {
        mockMvc.perform(get(SITEMAP_PATH)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._links.self[0].href", is(SITEMAP_PATH)))
            .andExpect(jsonPath("$._links.self[0].templated", is(false)));
    }
}