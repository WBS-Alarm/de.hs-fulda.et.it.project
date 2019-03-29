package de.hsfulda.et.wbs;

import de.hsfulda.et.wbs.repository.*;
import de.hsfulda.et.wbs.security.repository.GrantedAuthorityRepository;
import de.hsfulda.et.wbs.security.service.UserAuthenticationService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class ResourceTest {

    protected static final String SU_USER = "Superuser";
    protected static final String LE_USER = "Leser";
    protected static final String HE_USER = "HelsaUser";
    protected static final String FD_USER = "ForDelete";

    protected static final String FW_TRAEGER = "Feuerwehr";
    protected static final String HE_TRAEGER = "Helsa";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected TraegerRepository traegerRepository;

    @Autowired
    protected ZielortTestRepository zielortRepository;

    @Autowired
    protected KategorieTestRepository kategorieRepository;

    @Autowired
    protected GroesseTestRepository groesseRepository;

    @Autowired
    protected BestandTestRepository bestandRepository;

    @Autowired
    protected BenutzerRepository benutzerRepository;

    @Autowired
    protected GrantedAuthorityRepository grantedAuthorityRepository;

    @Autowired
    private UserAuthenticationService authentication;

    protected String getTokenAsSuperuser() {
        return getToken(SU_USER, "password");
    }

    protected String getToken(String username) {
        return getToken(username, "password");
    }

    private String getToken(String username, String password) {
        return "Bearer " + authentication
            .login(username, password)
            .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }

    protected Long getBenutzerId(String name) {
        return benutzerRepository.findByUsername(name).getId();
    }

    protected Long getTraegerId(String name) {
        return traegerRepository.findByName(name).get(0).getId();
    }

    protected Long getZielortId(String name, String traeger) {
        return zielortRepository.findByName(name, traeger).get(0).getId();
    }

    protected Long getKategorieId(String name, String traeger) {
        return kategorieRepository.findByName(name, traeger).get(0).getId();
    }

    protected Long getGroesseId(String name, String kategorie, String traeger) {
        return groesseRepository.findByName(name, kategorie, traeger).get(0).getId();
    }

    protected Long getBestandId(String traeger, String zielort, String groesse) {
        return bestandRepository.findByName(traeger, zielort, groesse).get(0).getId();
    }

    protected boolean hasGrantedAuthority(Long userId, Long authorityId) {
        return grantedAuthorityRepository.findByUserId(userId).stream().anyMatch(g -> authorityId.equals(g.getAuthorityId()));
    }
}