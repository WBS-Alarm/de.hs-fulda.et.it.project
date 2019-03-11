package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.ResourceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Die Authorities")
class AuthorityResourceTest extends ResourceTest {

    @Autowired
    private AuthorityResource controller;


    @DisplayName("wird im Spring Context geladen und gefunden")
    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @DisplayName("liefert eine Liste an Rollen die vergeben werden können")
    @Test
    void getAuthorities() throws Exception {
        mockMvc.perform(get(AuthorityResource.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", getTokenAsSuperuser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.authorities[0].id", is(2)))
                .andExpect(jsonPath("$._embedded.authorities[0].code", is("CONTROL")))
                .andExpect(jsonPath("$._embedded.authorities[0].bezeichnung", is("Administration")))
                .andExpect(jsonPath("$._links.self[0].href", is("/authorities")))
                .andExpect(jsonPath("$._links.self[0].templated", is(false)));
    }

    @DisplayName("kann nicht ohne Trägermanager aufgerufen werden")
    @Test
    void getAuthoritiesNoPermission() throws Exception {
        mockMvc.perform(get(AuthorityResource.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", getToken(LE_USER)))
                .andExpect(status().isForbidden());
    }

}