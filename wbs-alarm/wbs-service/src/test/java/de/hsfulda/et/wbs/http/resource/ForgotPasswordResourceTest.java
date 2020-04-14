package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.ResourceTest;
import de.hsfulda.et.wbs.security.resource.ForgotPasswordResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@DisplayName("Die Forgot Password Resource")
class ForgotPasswordResourceTest extends ResourceTest {

    @Autowired
    private ForgotPasswordResource resource;

    @DisplayName("wird im Spring Context geladen und gefunden")
    @Test
    void contextLoads() {
        assertThat(resource).isNotNull();
    }

    @DisplayName("Token wird nicht gesetzt, da die Konfigurierte Verbindung fehlerhaft ist.")
    @Test
    void getBenutzer() throws Exception {
        assertNull(getBenutzer(LE_USER).getToken());

        mockMvc.perform(post(ForgotPasswordResource.PATH, LE_USER));

        assertNull(getBenutzer(LE_USER).getToken());
    }
}