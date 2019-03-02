package de.hsfulda.et.wbs.http.resource.security;

import de.hsfulda.et.wbs.ResourceTest;
import de.hsfulda.et.wbs.security.resource.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test des Users Controller und dessen Suppaths.")
class UserResourceTest extends ResourceTest {

    @Autowired
    private UserLogoutResource userLogoutResource;

    @Autowired
    private CurrentUserResource currentUserResource;

    @Autowired
    private LoginResource loginResource;

    @Autowired
    private UserRegisterResource userRegisterResource;

    @Autowired
    private UserResource userResource;


    @DisplayName("Laden der Resourcen erfolgreich.")
    @Test
    void contextLoads() {
        Assertions.assertThat(userLogoutResource).isNotNull();
        Assertions.assertThat(currentUserResource).isNotNull();
        Assertions.assertThat(loginResource).isNotNull();
        Assertions.assertThat(userRegisterResource).isNotNull();
        Assertions.assertThat(userResource).isNotNull();
    }

    @DisplayName("Laden des angemeldeten Benutzers")
    @Test
    void getCurrentUser() throws Exception {
        mockMvc.perform(get(CurrentUserResource.PATH)
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
        mockMvc.perform(get(CurrentUserResource.PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer "))
            .andExpect(status().isUnauthorized());
    }

    @DisplayName("Laden eines vorhandenen Benutzers")
    @Test
    void getSomeUser() throws Exception {
        mockMvc.perform(get(UserResource.PATH, "Superuser")
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
        mockMvc.perform(get(UserResource.PATH, "Nobody")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", getTokenAsSuperuser()))
            .andExpect(status().isNotFound());
    }
}