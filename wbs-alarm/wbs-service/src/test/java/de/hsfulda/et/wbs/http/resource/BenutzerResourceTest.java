package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.ResourceTest;
import de.hsfulda.et.wbs.entity.Benutzer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test der Benutzer Resource")
class BenutzerResourceTest extends ResourceTest {

    @Autowired
    private BenutzerResource resource;

    @DisplayName("Laden der Resourcen erfolgreich.")
    @Test
    void contextLoads() {
        assertThat(resource).isNotNull();
    }

    @DisplayName("Anzeigen eines Benutzers vom gleichen Träger wie der User")
    @Test
    void getBenutzer() throws Exception {
        mockMvc.perform(get(BenutzerResource.PATH, getBenutzerId(LE_USER))
                .header("Authorization", getTokenAsSuperuser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("Anzeigen eines Benutzers vom anderen Träger wie der User")
    @Test
    void getBenutzerAndererTraeger() throws Exception {
        mockMvc.perform(get(BenutzerResource.PATH, getBenutzerId(HE_USER))
                .header("Authorization", getTokenAsSuperuser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Ändern der Email Adresse eines Benutzers")
    @Test
    void putAendernVonVorhandenenBenutzer() throws Exception {
        mockMvc.perform(put(BenutzerResource.PATH, getBenutzerId(LE_USER))
                .header("Authorization", getTokenAsSuperuser())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n  \"username\": \"Anners\",\n  \"mail\": \"neueMail@domain.de\"\n}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(LE_USER)))
                .andExpect(jsonPath("$.mail", is("neueMail@domain.de")));
    }

    @DisplayName("Ändern der Email Adresse eines unbekannten Benutzers")
    @Test
    void putAendernVonUnbekanntenBenutzer() throws Exception {
        mockMvc.perform(put(BenutzerResource.PATH, 0L)
                .header("Authorization", getTokenAsSuperuser())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n  \"username\": \"Anners\",\n  \"mail\": \"neueMail@domain.de\"\n}"))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Ändern der Email Adresse eines unberechtigten Benutzers")
    @Test
    void putAendernVonUnberechtigtenBenutzer() throws Exception {
        mockMvc.perform(put(BenutzerResource.PATH, getBenutzerId(LE_USER))
                .header("Authorization", getToken(HE_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n  \"username\": \"Anners\",\n  \"mail\": \"neueMail@domain.de\"\n}"))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Löschen eines Benutzers")
    @Test
    void deleteBenutzer() throws Exception {
        Long forDeleteId = getBenutzerId(FD_USER);
        mockMvc.perform(delete(BenutzerResource.PATH, forDeleteId)
                .header("Authorization", getToken(HE_USER))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get(BenutzerResource.PATH, forDeleteId)
                .header("Authorization", getToken(HE_USER))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Optional<Benutzer> byId = benutzerRepository.findById(forDeleteId);
        assertTrue(byId.isPresent());
        Benutzer forDelete = byId.get();
        assertFalse(forDelete.getAktiv());

        mockMvc.perform(delete(BenutzerResource.PATH, forDeleteId)
                .header("Authorization", getToken(HE_USER))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // Rückgängig
        forDelete.setAktiv(true);
        benutzerRepository.save(forDelete);
    }
}