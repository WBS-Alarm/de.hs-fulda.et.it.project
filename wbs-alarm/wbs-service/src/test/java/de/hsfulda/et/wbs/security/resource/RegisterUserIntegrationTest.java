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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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

        @DisplayName("erstellt Paul mit Password 1234")
        @Test
        void registerNewUser() throws Exception {
            mockMvc.perform(post(UserRegisterResource.PATH, getTraegerId(FW_TRAEGER))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n  \"username\": \"Paul\",\n  \"password\": \"1234\"\n}")
                    .header("Authorization", bearerToken))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", startsWith("/wbs/benutzer/")));
        }

        @DisplayName("erstellt nicht Paul mit Password 1234 zu unbekannten Träger")
        @Test
        void registerNewUserTraegerNotExists() throws Exception {
            mockMvc.perform(post(UserRegisterResource.PATH, 0L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n  \"username\": \"Paul\",\n  \"password\": \"1234\"\n}")
                    .header("Authorization", bearerToken))
                    .andExpect(status().isNotFound());
        }

        @DisplayName("erstellen nicht Paul ohne Password")
        @Test
        void registerNewUserNoPassword() throws Exception {
            mockMvc.perform(post(UserRegisterResource.PATH, getTraegerId(FW_TRAEGER))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n  \"username\": \"Paul\"\n}")
                    .header("Authorization", bearerToken))
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("erstellt nicht Benutzer ohne angegebenen Namen mit Password 1234")
        @Test
        void registerNewUserNoName() throws Exception {
            mockMvc.perform(post(UserRegisterResource.PATH, getTraegerId(FW_TRAEGER))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n \"password\": \"1234\"\n}")
                    .header("Authorization", bearerToken))
                    .andExpect(status().isBadRequest());
        }

        @Nested
        @DisplayName("bei erneuter Registrierung")
        class RegisterPaulAgainn {

            @DisplayName("kann keinen zweiten Paul mit beliebigen Password erstellen")
            @Test
            void registerNewUser() throws Exception {
                mockMvc.perform(post(UserRegisterResource.PATH, getTraegerId(FW_TRAEGER))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n  \"username\": \"Paul\",\n  \"password\": \"2345\"\n}")
                        .header("Authorization", bearerToken))
                        .andExpect(status().isConflict());
            }
        }

        @Nested
        @DisplayName("Superuser abmelden")
        class LogoutSuperuser {

            @DisplayName("ist Superuser abgemeldet")
            @Test
            void superuserIsLoggedOut() throws Exception {
                mockMvc.perform(get(CurrentUserResource.PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "))
                        .andExpect(status().isUnauthorized());
            }

            @DisplayName("kann Paul anmelden")
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