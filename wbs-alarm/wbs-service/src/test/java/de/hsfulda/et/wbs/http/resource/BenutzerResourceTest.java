package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.ResourceTest;
import de.hsfulda.et.wbs.security.resource.UserRegisterResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Resource Benutzer")
class BenutzerResourceTest extends ResourceTest {

    @Autowired
    private BenutzerResource resource;

    @DisplayName("Laden der Resourcen erfolgreich.")
    @Test
    void contextLoads() {
        assertThat(resource).isNotNull();
    }

    @DisplayName("einen Träger anlegen und zwei Benutzer.")
    @BeforeEach
    void setup() throws Exception {
        //Einen Träger hinzufügen
        mockMvc.perform(post(TraegerListResource.PATH)
                .header("Authorization", getTokenAsSuperuser())
                .content("{\n \"name\": \"Kassel\"\n}")
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post(UserRegisterResource.PATH, 1L)
                .header("Authorization", getTokenAsSuperuser())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n  \"username\": \"UserFeuerwehr\",\n  \"password\": \"1234\"\n}"));

        mockMvc.perform(post(UserRegisterResource.PATH, 2L)
                .header("Authorization", getTokenAsSuperuser())
                .content("{\n  \"username\": \"UserKassel\",\n  \"password\": \"1234\"\n}"));
    }

    @Test
    void getBenutzer() throws Exception {
        mockMvc.perform(get(BenutzerResource.PATH, 2L)
                .header("Authorization", getTokenAsSuperuser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}