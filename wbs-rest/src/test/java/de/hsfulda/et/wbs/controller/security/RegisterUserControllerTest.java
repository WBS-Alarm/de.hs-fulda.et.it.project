package de.hsfulda.et.wbs.controller.security;

import de.hsfulda.et.wbs.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static de.hsfulda.et.wbs.controller.security.LoginController.LOGIN_PATH;
import static de.hsfulda.et.wbs.controller.security.UsersController.PATH_CURRENT;
import static de.hsfulda.et.wbs.controller.security.UsersController.PATH_LOGOUT;
import static de.hsfulda.et.wbs.controller.security.UsersController.PATH_REGISTER;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Registriertung eines Benutzers")
class RegisterUserControllerTest extends ControllerTest {

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
            mockMvc.perform(post(PATH_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n  \"username\": \"Paul\",\n  \"password\": \"1234\"\n}")
                .header("Authorization", bearerToken))
                .andExpect(status().isCreated());
        }

        @Nested
        @DisplayName("Superuser abmelden")
        class LogoutSuperuser {

            @BeforeEach
            void logoutSuperuser() throws Exception {
                mockMvc.perform(get(PATH_LOGOUT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", bearerToken))
                    .andExpect(status().isOk());
            }

            @DisplayName("Superuser ist abgemeldet")
            @Test
            void superuserIsLoggedOut() throws Exception {
                mockMvc.perform(get(PATH_CURRENT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer "))
                    .andExpect(status().isUnauthorized());
            }

            @DisplayName("und mit Paul anmelden")
            @Test
            void loginAsPaul() throws Exception {
                mockMvc.perform(post(LOGIN_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n  \"username\": \"Paul\",\n  \"password\": \"1234\"\n}"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(startsWith("ey")));
            }

        }
    }
}