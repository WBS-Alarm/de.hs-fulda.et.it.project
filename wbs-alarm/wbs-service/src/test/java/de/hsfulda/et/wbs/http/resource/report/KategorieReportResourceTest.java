package de.hsfulda.et.wbs.http.resource.report;

import de.hsfulda.et.wbs.ResourceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Die Kategorie Report Resource")
class KategorieReportResourceTest extends ResourceTest {

    @Autowired
    private KategorieReportResource resource;

    @DisplayName("wird im Spring Context geladen und gefunden")
    @Test
    void contextLoads() {
        assertThat(resource).isNotNull();
    }

    @DisplayName("Benutzer werden zu Träger aufgelistet")
    @Test
    void getBenutzerList() throws Exception {
        Long traegerId = getTraegerId(FW_TRAEGER);
        mockMvc.perform(get(KategorieReportResource.PATH, traegerId).header("Authorization", getTokenAsSuperuser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}