package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.ResourceTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Die User Resourcen und Subresourcen")
class UserResourceTest extends ResourceTest {

    @Autowired
    private CurrentUserResource currentUserResource;

    @Autowired
    private LoginResource loginResource;

    @Autowired
    private UserRegisterResource userRegisterResource;

    @DisplayName("werden im Spring Context geladen und gefunden")
    @Test
    void contextLoads() {
        Assertions.assertThat(currentUserResource).isNotNull();
        Assertions.assertThat(loginResource).isNotNull();
        Assertions.assertThat(userRegisterResource).isNotNull();
    }

    @DisplayName("ermitteln den angemeldeten Benutzer")
    @Test
    void getCurrentUser() throws Exception {
        mockMvc.perform(get(CurrentUserResource.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", getTokenAsSuperuser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("Superuser")));
    }

    @DisplayName("können nicht von unangemeldeten Benutzern zugegriffen werden")
    @Test
    void getCurrentUserNoToken() throws Exception {
        mockMvc.perform(get(CurrentUserResource.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer "))
                .andExpect(status().isUnauthorized());
    }
}