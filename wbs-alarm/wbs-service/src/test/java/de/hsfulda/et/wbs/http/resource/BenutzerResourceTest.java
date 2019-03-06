package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.ResourceTest;
import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import de.hsfulda.et.wbs.security.resource.UserRegisterResource;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test der Benutzer Resource")
class BenutzerResourceTest extends ResourceTest {

    @Autowired
    private BenutzerResource resource;

    @DisplayName("Laden der Resourcen erfolgreich.")
    @Test
    void contextLoads() {
        assertThat(resource).isNotNull();
    }

    @DisplayName("Anzeigen eines Benutzers vom gleichen Träger wie der User")
    @Test
    void getBenutzer() throws Exception {
        mockMvc.perform(get(BenutzerResource.PATH, getBenutzerId(LE_USER))
                .header("Authorization", getTokenAsSuperuser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("Anzeigen eines Benutzers vom anderen Träger wie der User")
    @Test
    void getBenutzerAndererTraeger() throws Exception {
        mockMvc.perform(get(BenutzerResource.PATH, getBenutzerId(HE_USER))
                .header("Authorization", getTokenAsSuperuser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}