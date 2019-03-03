package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.ResourceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Registriertung eines Benutzers")
class RegisterUserIntegrationTest extends ResourceTest {

    private String bearerToken;

    @Nested
    @DisplayName("als Superuser")
    class AsSuperuser {

        @BeforeEach
        void createNewStack() {
            bearerToken = getTokenAsSuperuser();
        }


        @DisplayName("Paul mit Password 1234 erstellen")
        @Test
        void registerNewUser() throws Exception {
            mockMvc.perform(post(UserRegisterResource.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n  \"username\": \"Paul\",\n  \"password\": \"1234\"\n}")
                .header("Authorization", bearerToken))
                .andExpect(status().isCreated());
        }

        @DisplayName("Paul ohne Password erstellen")
        @Test
        void registerNewUserNoPassword() throws Exception {
            mockMvc.perform(post(UserRegisterResource.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n  \"username\": \"Paul\"\n}")
                .header("Authorization", bearerToken))
                .andExpect(status().isBadRequest());
        }

        @DisplayName("Ohne Namen mit Password 1234 erstellen")
        @Test
        void registerNewUserNoName() throws Exception {
            mockMvc.perform(post(UserRegisterResource.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n \"password\": \"1234\"\n}")
                .header("Authorization", bearerToken))
                .andExpect(status().isBadRequest());
        }

        @Nested
        @DisplayName("Wiederholtes Registrieren")
        class RegisterPaulAgainn {

            @DisplayName("Nochmal Paul mit Password 1234 erstellen")
            @Test
            void registerNewUser() throws Exception {
                mockMvc.perform(post(UserRegisterResource.PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n  \"username\": \"Paul\",\n  \"password\": \"1234\"\n}")
                    .header("Authorization", bearerToken))
                    .andExpect(status().isConflict());
            }
        }

        @Nested
        @DisplayName("Superuser abmelden")
        class LogoutSuperuser {

            @BeforeEach
            void logoutSuperuser() throws Exception {
                mockMvc.perform(get(UserLogoutResource.PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", bearerToken))
                    .andExpect(status().isOk());
            }


            @DisplayName("Superuser ist abgemeldet")
            @Test
            void superuserIsLoggedOut() throws Exception {
                mockMvc.perform(get(CurrentUserResource.PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", bearerToken))
                    .andExpect(status().isUnauthorized());
            }

            @DisplayName("und mit Paul anmelden")
            @Test
            void loginAsPaul() throws Exception {
                mockMvc.perform(post(LoginResource.PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n  \"username\": \"Paul\",\n  \"password\": \"1234\"\n}"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(startsWith("ey")));

            }
        }
    }
}