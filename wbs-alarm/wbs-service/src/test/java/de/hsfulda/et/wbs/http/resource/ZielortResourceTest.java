package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.ResourceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test der Zielort Resourcen.")
class ZielortResourceTest extends ResourceTest {

    @Autowired
    private ZielortResource resource;

    @DisplayName("Laden der Resourcen erfolgreich.")
    @Test
    void contextLoads() {
        assertThat(resource).isNotNull();
    }

    @DisplayName("Anzeigen eines Zielorts vom gleichen Träger wie der User")
    @Test
    void getZielort() throws Exception {
        Long zielortId = getZielortId("Wareneingang", FW_TRAEGER);
        mockMvc.perform(get(ZielortResource.PATH, zielortId)
                .header("Authorization", getTokenAsSuperuser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self[0].templated", is(false)))
                .andExpect(jsonPath("$.name", is("Wareneingang")))
                .andExpect(jsonPath("$.id", is(zielortId.intValue())))
                .andExpect(jsonPath("$._embedded.traeger[0].name", is(FW_TRAEGER)));
    }

    @DisplayName("Anzeigen eines Zielorts vom anderen Träger wie der User")
    @Test
    void getZielortNotFound() throws Exception {
        Long zielortId = getZielortId("Wareneingang", FW_TRAEGER);
        mockMvc.perform(get(ZielortResource.PATH, zielortId)
                .header("Authorization", getToken(HE_USER))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Erstellen eines Zielorts")
    @Test
    void putAendernEinesZielorts() throws Exception {
        Long traegerId = getTraegerId(HE_TRAEGER);
        mockMvc.perform(post(ZielortListResource.PATH, traegerId)
                .header("Authorization", getToken(HE_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n \"name\": \"Eschenstruth\"\n}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$._links.self[0].templated", is(false)))
                .andExpect(jsonPath("$.name", is("Eschenstruth")))
                .andExpect(jsonPath("$._embedded.traeger[0].name", is(HE_TRAEGER)));
    }

    @DisplayName("Aktionen auf einen Zielorts")
    @Nested
    class ZielortAenderungen {

        @DisplayName("selbstangelegter Zielort ist ohne Namen nicht änderbar")
        @Test
        void putAendernErstellterZielortKeinName() throws Exception {
            Long zielortId = getZielortId("Eschenstruth", HE_TRAEGER);
            mockMvc.perform(put(ZielortResource.PATH, zielortId)
                    .header("Authorization", getToken(HE_USER))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n \"name\": \"\"\n}"))
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("Standard-Zielort ist nicht änderbar")
        @Test
        void putAendernStandardZielort() throws Exception {
            Long zielortId = getZielortId("Wareneingang", HE_TRAEGER);
            mockMvc.perform(put(ZielortResource.PATH, zielortId)
                    .header("Authorization", getToken(HE_USER))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n \"name\": \"Wasanderes\"\n}"))
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("Valides Ändern")
        @Nested
        class AenderZielort {

            @DisplayName("selbstangelegter Zielort ist änderbar")
            @Test
            void putAendernErstellterZielort() throws Exception {
                Long zielortId = getZielortId("Eschenstruth", HE_TRAEGER);
                mockMvc.perform(put(ZielortResource.PATH, zielortId)
                        .header("Authorization", getToken(HE_USER))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n \"name\": \"Eschenstr.\"\n}"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$._links.self[0].templated", is(false)))
                        .andExpect(jsonPath("$.name", is("Eschenstr.")))
                        .andExpect(jsonPath("$._embedded.traeger[0].name", is(HE_TRAEGER)));
            }

            @DisplayName("und wieder löschen")
            @Nested
            class LoeschenTraeger {

                @DisplayName("eines vorhandenen Zielorts")
                @Test
                void deleteDeleteValid() throws Exception {
                    Long zielortId = getZielortId("Eschenstr.", HE_TRAEGER);
                    mockMvc.perform(delete(ZielortResource.PATH, zielortId)
                            .header("Authorization", getToken(HE_USER))
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());

                    // Und nicht mehr auffindbar
                    mockMvc.perform(get(ZielortResource.PATH, zielortId)
                            .header("Authorization", getToken(HE_USER))
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isNotFound());
                }


                @DisplayName("eines unbekannten Zielorts")
                @Test
                void deleteNotFoun() throws Exception {
                    mockMvc.perform(delete(ZielortResource.PATH, 0L)
                            .header("Authorization", getToken(HE_USER))
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isNotFound());
                }
            }
        }
    }
}