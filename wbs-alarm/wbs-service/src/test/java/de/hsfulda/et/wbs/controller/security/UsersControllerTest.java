package de.hsfulda.et.wbs.controller.security;

import de.hsfulda.et.wbs.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static de.hsfulda.et.wbs.controller.security.UsersController.PATH_CURRENT;
import static de.hsfulda.et.wbs.controller.security.UsersController.PATH_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test des Users Controller und dessen Suppaths.")
class UsersControllerTest extends ControllerTest {

    @Autowired
    private UsersController controller;


    @DisplayName("Laden des Controllers erfolgreich.")
    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @DisplayName("Laden des angemeldeten Benutzers")
    @Test
    void getCurrentUser() throws Exception {
        mockMvc.perform(get(PATH_CURRENT)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", getTokenAsSuperuser()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", is("Superuser")))
            .andExpect(jsonPath("$._links.self[0].href", is("/users/Superuser")))
            .andExpect(jsonPath("$._links.self[0].templated", is(false)));
    }

    @DisplayName("Laden des angemeldeten Benutzers als nicht angemeldeter Benutzer")
    @Test
    void getCurrentUserNoToken() throws Exception {
        mockMvc.perform(get(PATH_CURRENT)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer "))
            .andExpect(status().isUnauthorized());
    }

    @DisplayName("Laden eines vorhandenen Benutzers")
    @Test
    void getSomeUser() throws Exception {
        mockMvc.perform(get(PATH_USER, "Superuser")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", getTokenAsSuperuser()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", is("Superuser")))
            .andExpect(jsonPath("$._links.self[0].href", is("/users/Superuser")))
            .andExpect(jsonPath("$._links.self[0].templated", is(false)));
    }

    @DisplayName("Laden eines unbekannten Benutzers")
    @Test
    void getSomeUserUnknown() throws Exception {
        mockMvc.perform(get(PATH_USER, "Nobody")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", getTokenAsSuperuser()))
            .andExpect(status().isNotFound());
    }
}