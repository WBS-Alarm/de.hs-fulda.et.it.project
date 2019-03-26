package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.ResourceTest;
import de.hsfulda.et.wbs.entity.Groesse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

@DisplayName("Die Groesse Resource")
class GroesseeResourceTest extends ResourceTest {

    @Autowired
    private GroesseResource resource;

    @DisplayName("wird im Spring Context geladen und gefunden")
    @Test
    void contextLoads() {
        assertThat(resource).isNotNull();
    }

    @DisplayName("wird aufgelistet zu den Kategorien des Anwenders aufgelistet.")
    @Test
    void getGroesse() throws Exception {
        Long groesseId = getGroesseId("XXL", "Polo-Hemd", FW_TRAEGER);
        mockMvc.perform(get(GroesseResource.PATH, groesseId)
            .header("Authorization", getTokenAsSuperuser())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._links.self[0].templated", is(false)))
            .andExpect(jsonPath("$.name", is("XXL")))
            .andExpect(jsonPath("$.id", is(groesseId.intValue())))
            .andExpect(jsonPath("$._embedded.kategorie[0].name", is("Polo-Hemd")));
    }

    @DisplayName("wird von einer anderen Kategorie nicht angezeigt.")
    @Test
    void getGroesseNotFound() throws Exception {
        Long groesseId = getGroesseId("XXL", "Polo-Hemd", FW_TRAEGER);
        mockMvc.perform(get(GroesseResource.PATH, groesseId)
            .header("Authorization", getToken(HE_USER))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @DisplayName("kann für einer Kategorie erstellt werden")
    @Test
    void putAendernEinesGroesses() throws Exception {
        Long kategorie = getKategorieId("Polo-Hemd", HE_TRAEGER);
        mockMvc.perform(post(GroesseListResource.PATH, kategorie)
            .header("Authorization", getToken(HE_USER))
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n \"name\": \"S\"\n}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$._links.self[0].templated", is(false)))
            .andExpect(jsonPath("$.name", is("S")))
            .andExpect(jsonPath("$._embedded.kategorie[0].name", is("Polo-Hemd")));
    }

    @DisplayName("bei Änderungen")
    @Nested
    class GroesseAenderungen {

        @DisplayName("wird nicht geändert wenn der Name nicht angegeben wurde")
        @Test
        void putAendernErstellterGroesseKeinName() throws Exception {
            Long groesseId = getGroesseId("S", "Polo-Hemd", HE_TRAEGER);
            mockMvc.perform(put(GroesseResource.PATH, groesseId)
                .header("Authorization", getToken(HE_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n \"name\": \"\"\n}"))
                .andExpect(status().isBadRequest());
        }

        @DisplayName("valide")
        @Nested
        class AenderGroesse {

            @DisplayName("wird geändert")
            @Test
            void putAendernErstellterGroesse() throws Exception {
                Long groesseId = getGroesseId("S", "Polo-Hemd", HE_TRAEGER);
                mockMvc.perform(put(GroesseResource.PATH, groesseId)
                    .header("Authorization", getToken(HE_USER))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n \"name\": \"M\"\n}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$._links.self[0].templated", is(false)))
                    .andExpect(jsonPath("$.name", is("M")))
                    .andExpect(jsonPath("$._embedded.kategorie[0].name", is("Polo-Hemd")));
            }

            @DisplayName("ist löschbar")
            @Nested
            class LoeschenGroesse {

                @DisplayName("wird inaktiv gesetzt")
                @Test
                void deleteDeleteValid() throws Exception {
                    Long groesseId = getGroesseId("M", "Polo-Hemd", HE_TRAEGER);
                    mockMvc.perform(delete(GroesseResource.PATH, groesseId)
                        .header("Authorization", getToken(HE_USER))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

                    // Und nicht mehr auffindbar
                    mockMvc.perform(get(GroesseResource.PATH, groesseId)
                        .header("Authorization", getToken(HE_USER))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());

                    Optional<Groesse> byId = groesseRepository.findById(groesseId);

                    assertTrue(byId.isPresent());
                    assertFalse(byId.get().isAktiv());
                }


                @DisplayName("wird nicht gelöscht wenn Groesse nicht gefunden wurde")
                @Test
                void deleteNotFoun() throws Exception {
                    mockMvc.perform(delete(GroesseResource.PATH, 0L)
                        .header("Authorization", getToken(HE_USER))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
                }
            }
        }
    }
}