package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.ResourceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Die GrantAuthority Resource")
class GrantAuthorityResourceTest extends ResourceTest {

    @Autowired
    private GrantAuthorityResource controller;

    @DisplayName("wird im Spring Context geladen und gefunden")
    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @DisplayName("authorisiert einen neuen Benutzer mit dem Recht Lesen")
    @Test
    void grantAuthority() throws Exception {
        Long grantId = getBenutzerId("ForGrantTest");

        mockMvc.perform(post(GrantAuthorityResource.PATH, 5, grantId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", getTokenAsSuperuser()))
                .andExpect(status().isCreated());

        assertTrue(hasGrantedAuthority(grantId, 5L));
    }

    @DisplayName("kann nicht ohne Trägermanager vergeben werden ")
    @Test
    void grantAuthorityNoPermissions() throws Exception {
        Long grantId = getBenutzerId("ForGrantTest");
        mockMvc.perform(post(GrantAuthorityResource.PATH, 5, grantId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", getToken(LE_USER)))
                .andExpect(status().isForbidden());
    }

    @DisplayName("authorisiert einen unbekannten Benutzer")
    @Test
    void grantAuthorityUserNotFound() throws Exception {
        mockMvc.perform(post(GrantAuthorityResource.PATH, 5, 0L)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", getTokenAsSuperuser()))
                .andExpect(status().isNotFound());
    }

    @DisplayName("authorisiert ein unbekanntes Recht")
    @Test
    void grantAuthorityAuthorityNotFound() throws Exception {
        Long grantId = getBenutzerId("ForGrantTest");
        mockMvc.perform(post(GrantAuthorityResource.PATH, 0L, grantId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", getTokenAsSuperuser()))
                .andExpect(status().isNotFound());
    }

    @DisplayName("doppelte Vergabe von Rechten")
    @Nested
    class DoubleGrantTest {

        @DisplayName("ein bereits vergebenes Recht löst einen Konflikt aus")
        @Test
        void grantDouble() throws Exception {
            Long grantId = getBenutzerId("ForGrantTest");
            mockMvc.perform(post(GrantAuthorityResource.PATH, 5, grantId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", getTokenAsSuperuser()))
                    .andExpect(status().isConflict());
        }
    }
}