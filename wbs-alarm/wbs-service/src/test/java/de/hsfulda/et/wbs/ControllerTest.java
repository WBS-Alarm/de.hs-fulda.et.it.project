package de.hsfulda.et.wbs;

import de.hsfulda.et.wbs.service.UserAuthenticationService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private UserAuthenticationService authentication;

    protected String getTokenAsSuperuser() {
        return getToken("Superuser", "password");
    }

    protected String getToken(String username, String password) {
        return "Bearer " + authentication
            .login(username, password)
            .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }

}