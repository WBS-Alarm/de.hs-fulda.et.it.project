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
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Die Benutzer Resource")
class BenutzerResourceTest extends ResourceTest {

    @Autowired
    private BenutzerResource resource;

    @DisplayName("wird im Spring Context geladen und gefunden")
    @Test
    void contextLoads() {
        assertThat(resource).isNotNull();
    }

    @DisplayName("wird bei gleichen Träger angezeigt")
    @Test
    void getBenutzer() throws Exception {
        Long benutzerId = getBenutzerId(LE_USER);
        mockMvc.perform(get(BenutzerResource.PATH, benutzerId)
                .header("Authorization", getTokenAsSuperuser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.authorities[0].id", is(5)))
                .andExpect(jsonPath("$._embedded.authorities[0].code", is("READ")))
                .andExpect(jsonPath("$._embedded.traeger", notNullValue()))
                .andExpect(jsonPath("$._links.self[0].href", is("/benutzer/" + benutzerId)))
                .andExpect(jsonPath("$._links.self[0].templated", is(false)));
    }

    @DisplayName("wird bei einem anderen Träger nicht angezeigt")
    @Test
    void getBenutzerAndererTraeger() throws Exception {
        mockMvc.perform(get(BenutzerResource.PATH, getBenutzerId(HE_USER))
                .header("Authorization", getTokenAsSuperuser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @DisplayName("kann die E-Mail Adresse ändern aber nicht den Namen")
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

    @DisplayName("kann keinen unbekannten Benutzer ändern")
    @Test
    void putAendernVonUnbekanntenBenutzer() throws Exception {
        mockMvc.perform(put(BenutzerResource.PATH, 0L)
                .header("Authorization", getTokenAsSuperuser())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n  \"username\": \"Anners\",\n  \"mail\": \"neueMail@domain.de\"\n}"))
                .andExpect(status().isNotFound());
    }

    @DisplayName("kann keinen Benutzer von einem anderen Träger ändern")
    @Test
    void putAendernVonUnberechtigtenBenutzer() throws Exception {
        mockMvc.perform(put(BenutzerResource.PATH, getBenutzerId(LE_USER))
                .header("Authorization", getToken(HE_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n  \"username\": \"Anners\",\n  \"mail\": \"neueMail@domain.de\"\n}"))
                .andExpect(status().isNotFound());
    }

    @DisplayName("kann einen Benutzer löschen indem er inaktiv gesetzt wird")
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