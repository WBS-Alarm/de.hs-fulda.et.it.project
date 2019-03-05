package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.ResourceTest;
import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import de.hsfulda.et.wbs.security.resource.UserRegisterResource;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("Hier nochmal prüfen wie diese Fälle aufgebaut werden können.")
@DisplayName("Resource Benutzer")
class BenutzerResourceTest extends ResourceTest {

    @Autowired
    private TraegerRepository traegerRepository;

    @Autowired
    private BenutzerRepository benutzerRepository;

    @Autowired
    private BenutzerResource resource;

    @DisplayName("Laden der Resourcen erfolgreich.")
    @Test
    void contextLoads() {
        assertThat(resource).isNotNull();
    }

    private Long benutzerGleich;
    private Long benutzerAnners;

    @BeforeEach
    void setup() throws Exception {
        Traeger s = new Traeger();
        s.setName("KasselDings");
        Benutzer b = new Benutzer();
        b.setUsername("UserKassel");
        s.addBenutzer(b);
        traegerRepository.save(s);
        benutzerAnners = b.getId();

        Optional<Traeger> ma = traegerRepository.findById(1L);
        Benutzer b1 = new Benutzer();
        b1.setUsername("UserFeuerwehr");
        Traeger traeger = ma.get();
        traeger.addBenutzer(b1);

        traegerRepository.save(traeger);
        benutzerGleich = b1.getId();

    }

    @AfterEach
    void tearDown() {
        deleteUser("UserFeuerwehr");
        deleteUser("UserKassel");
        deleteTraegr("KasselDings");
    }

    private void deleteUser(String username) {
        Benutzer b1 = benutzerRepository.findByUsername(username);
        if(b1 != null) {
            benutzerRepository.delete(b1);
        }
    }

    private void deleteTraegr(String name) {
        List<Traeger> b1 = traegerRepository.findByName(name);
        traegerRepository.deleteAll(b1);
    }

    @DisplayName("Anzeigen eines Benutzers vom gleichen Träger wie der User")
    @Test
    void getBenutzer() throws Exception {
        mockMvc.perform(get(BenutzerResource.PATH, benutzerGleich)
                .header("Authorization", getTokenAsSuperuser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}