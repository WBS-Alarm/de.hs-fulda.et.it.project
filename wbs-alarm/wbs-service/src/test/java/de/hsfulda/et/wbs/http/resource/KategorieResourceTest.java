package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.ResourceTest;
import de.hsfulda.et.wbs.entity.Kategorie;
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

@DisplayName("Die Kategorie Resource")
class KategorieResourceTest extends ResourceTest {

    @Autowired
    private KategorieResource resource;

    @DisplayName("wird im Spring Context geladen und gefunden")
    @Test
    void contextLoads() {
        assertThat(resource).isNotNull();
    }

    @DisplayName("wird aufgelistet zu dem Träger des Anwenders aufgelistet.")
    @Test
    void getKategorie() throws Exception {
        Long kategorieId = getKategorieId("Polo-Hemd", FW_TRAEGER);
        mockMvc.perform(get(KategorieResource.PATH, kategorieId)
                .header("Authorization", getTokenAsSuperuser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self[0].templated", is(false)))
                .andExpect(jsonPath("$.name", is("Polo-Hemd")))
                .andExpect(jsonPath("$.id", is(kategorieId.intValue())))
                .andExpect(jsonPath("$._embedded.traeger[0].name", is(FW_TRAEGER)));
    }

    @DisplayName("wird von einem anderen Träger nicht angezeigt.")
    @Test
    void getKategorieNotFound() throws Exception {
        Long kategorieId = getKategorieId("Polo-Hemd", FW_TRAEGER);
        mockMvc.perform(get(KategorieResource.PATH, kategorieId)
                .header("Authorization", getToken(HE_USER))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @DisplayName("kann für einen Träger erstellt werden")
    @Test
    void putAendernEinesKategories() throws Exception {
        Long traegerId = getTraegerId(HE_TRAEGER);
        mockMvc.perform(post(KategorieListResource.PATH, traegerId)
                .header("Authorization", getToken(HE_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n \"name\": \"Hose\"\n}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$._links.self[0].templated", is(false)))
                .andExpect(jsonPath("$.name", is("Hose")))
                .andExpect(jsonPath("$._embedded.traeger[0].name", is(HE_TRAEGER)));
    }

    @DisplayName("bei Änderungen")
    @Nested
    class KategorieAenderungen {

        @DisplayName("wird nicht geändert wenn der Name nicht angegeben wurde")
        @Test
        void putAendernErstellterKategorieKeinName() throws Exception {
            Long kategorieId = getKategorieId("Hose", HE_TRAEGER);
            mockMvc.perform(put(KategorieResource.PATH, kategorieId)
                    .header("Authorization", getToken(HE_USER))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n \"name\": \"\"\n}"))
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("valide")
        @Nested
        class AenderKategorie {

            @DisplayName("wird geändert")
            @Test
            void putAendernErstellterKategorie() throws Exception {
                Long kategorieId = getKategorieId("Hose", HE_TRAEGER);
                mockMvc.perform(put(KategorieResource.PATH, kategorieId)
                        .header("Authorization", getToken(HE_USER))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n \"name\": \"Jeans-Hose.\"\n}"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$._links.self[0].templated", is(false)))
                        .andExpect(jsonPath("$.name", is("Jeans-Hose.")))
                        .andExpect(jsonPath("$._embedded.traeger[0].name", is(HE_TRAEGER)));
            }

            @DisplayName("ist löschbar")
            @Nested
            class LoeschenTraeger {

                @DisplayName("wird inaktiv gesetzt")
                @Test
                void deleteDeleteValid() throws Exception {
                    Long kategorieId = getKategorieId("Jeans-Hose.", HE_TRAEGER);
                    mockMvc.perform(delete(KategorieResource.PATH, kategorieId)
                            .header("Authorization", getToken(HE_USER))
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());

                    // Und nicht mehr auffindbar
                    mockMvc.perform(get(KategorieResource.PATH, kategorieId)
                            .header("Authorization", getToken(HE_USER))
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isNotFound());

                    Optional<Kategorie> byId = kategorieRepository.findById(kategorieId);

                    assertTrue(byId.isPresent());
                    assertFalse(byId.get().isAktiv());
                }


                @DisplayName("wird nicht gelöscht wenn Kategorie nicht gefunden wurde")
                @Test
                void deleteNotFoun() throws Exception {
                    mockMvc.perform(delete(KategorieResource.PATH, 0L)
                            .header("Authorization", getToken(HE_USER))
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isNotFound());
                }
            }
        }
    }
}