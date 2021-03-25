package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.ResourceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Die Password Resourcen und Subresourcen")
class PasswordResourceTest extends ResourceTest {

    @Autowired
    private ForgotPasswordResource forgotPasswordResource;
    @Autowired
    private ResetPasswordResource resetPasswordResource;

    @DisplayName("werden im Spring Context geladen und gefunden")
    @Test
    void contextLoads() {
        assertThat(resetPasswordResource).isNotNull();
        assertThat(forgotPasswordResource).isNotNull();
    }

    @DisplayName("Passwort versessen für HelsaBuchung")
    @Test
    void resetBOUser() throws Exception {
        mockMvc.perform(post(ForgotPasswordResource.PATH, BO_USER).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("Passwort versessen unbekannten Uer")
    @Test
    void resetUnknown() throws Exception {
        mockMvc.perform(post(ForgotPasswordResource.PATH, "unbekannt").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Nested
    class ResetIt {
        String uuid;

        @BeforeEach
        void setup() {
            uuid = getUUID(BO_USER);
        }

        @DisplayName("setzen neues Passwort für HelsaBuchung")
        @Test
        void postNewPassword() throws Exception {
            mockMvc.perform(post(ResetPasswordResource.PATH).contentType(MediaType.APPLICATION_JSON)
                    .content("{\n \"token\": \"" + uuid + "\",\n \"password\": \"neues\" \n}"))
                    .andExpect(status().isOk());
        }

        @Nested
        class LoginValid {

            @DisplayName("HelsaBuchung kann ein Tolek erstellen")
            @Test
            void checkPostConditions() throws Exception {
                assertThat(getToken(BO_USER, "neues")).isNotNull();
                assertThat(getUUID(BO_USER)).isNull();
            }
        }
    }

    @Nested
    class InvalidReset {
        String uuid;

        @BeforeEach
        void setup() {
            uuid = getUUID(BO_USER);
        }

        @DisplayName("Kein Password")
        @Test
        void postNoPasswort() throws Exception {
            mockMvc.perform(post(ResetPasswordResource.PATH).contentType(MediaType.APPLICATION_JSON)
                    .content("{\n \"token\": \"" + uuid + "\",\n \"password\": \"\" \n}"))
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("Kein Token")
        @Test
        void postNoToken() throws Exception {
            mockMvc.perform(post(ResetPasswordResource.PATH).contentType(MediaType.APPLICATION_JSON)
                    .content("{\n \"token\": \"\",\n \"password\": \"neues\" \n}"))
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("Invalides Token")
        @Test
        void postInvalidToken() throws Exception {
            mockMvc.perform(post(ResetPasswordResource.PATH).contentType(MediaType.APPLICATION_JSON)
                    .content("{\n \"token\": \"1234-123-123\",\n \"password\": \"neues\" \n}"))
                    .andExpect(status().isBadRequest());
        }
    }
}