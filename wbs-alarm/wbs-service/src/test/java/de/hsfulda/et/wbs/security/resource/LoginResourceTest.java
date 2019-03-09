package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.ResourceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static de.hsfulda.et.wbs.security.resource.LoginResource.PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Die Login Resource")
class LoginResourceTest extends ResourceTest {

    @Autowired
    private LoginResource controller;


    @DisplayName("wird im Spring Context geladen und gefunden")
    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @DisplayName("meldet einen bereits registrierten Benutzer an")
    @Test
    void login() throws Exception {
        mockMvc.perform(post(PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n  \"username\": \"Superuser\",\n  \"password\": \"password\"\n}"))
            .andExpect(status().isOk())
            .andExpect(content().string(startsWith("ey")));
    }

    @DisplayName("weißt eine fehlerhafte Anmeldung ab")
    @Test
    void loginFailed() throws Exception {
        mockMvc.perform(post(PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n  \"username\": \"johnny\",\n  \"password\": \"rainbow\"\n}"))
            .andExpect(status().isForbidden());
    }

}