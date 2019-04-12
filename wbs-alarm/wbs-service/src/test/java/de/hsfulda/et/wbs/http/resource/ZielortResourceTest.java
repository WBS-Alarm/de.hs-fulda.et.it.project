package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.ResourceTest;
import de.hsfulda.et.wbs.entity.Zielort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Die Zielort Resource")
class ZielortResourceTest extends ResourceTest {

    @Autowired
    private ZielortResource resource;

    @DisplayName("wird im Spring Context geladen und gefunden")
    @Test
    void contextLoads() {
        assertThat(resource).isNotNull();
    }

    @DisplayName("wird aufgelistet zu dem Träger des Anwenders aufgelistet.")
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

    @DisplayName("wird von einem anderen Träger nicht angezeigt.")
    @Test
    void getZielortNotFound() throws Exception {
        Long zielortId = getZielortId("Wareneingang", FW_TRAEGER);
        mockMvc.perform(get(ZielortResource.PATH, zielortId)
                .header("Authorization", getToken(HE_USER))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @DisplayName("kann für einen Träger erstellt werden")
    @Test
    void putAendernEinesZielorts() throws Exception {
        Long traegerId = getTraegerId(HE_TRAEGER);
        mockMvc.perform(post(ZielortListResource.PATH, traegerId)
                .header("Authorization", getToken(HE_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n \"name\": \"Eschenstruth\"\n}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", startsWith("/wbs/zielort/")));
    }

    @DisplayName("bei Änderungen")
    @Nested
    class ZielortAenderungen {

        @DisplayName("wird nicht geändert wenn der Name nicht angegeben wurde")
        @Test
        void putAendernErstellterZielortKeinName() throws Exception {
            Long zielortId = getZielortId("Eschenstruth", HE_TRAEGER);
            mockMvc.perform(put(ZielortResource.PATH, zielortId)
                    .header("Authorization", getToken(HE_USER))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n \"name\": \"\"\n}"))
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("wird nicht geändert wenn es sich um einen automatisierten Zielort handelt")
        @Test
        void putAendernStandardZielort() throws Exception {
            Long zielortId = getZielortId("Wareneingang", HE_TRAEGER);
            mockMvc.perform(put(ZielortResource.PATH, zielortId)
                    .header("Authorization", getToken(HE_USER))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n \"name\": \"Wasanderes\"\n}"))
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("valide")
        @Nested
        class AenderZielort {

            @DisplayName("wird geändert wenn der Zielort nicht automatisiert angelegt wurde ")
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

            @DisplayName("Erfassung abgeschlossen")
            @Nested
            class ErfassungAbgeschlossen {

                @DisplayName("Zielort wird als erfasst markiert")
                @Test
                void deleteDeleteValid() throws Exception {
                    Long zielortId = getZielortId("Eschenstr.", HE_TRAEGER);
                    mockMvc.perform(post(ZielortLockResource.PATH, zielortId)
                            .header("Authorization", getToken(HE_USER))
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());

                    Optional<Zielort> byId = zielortRepository.findById(zielortId);

                    assertTrue(byId.isPresent());
                    assertTrue(byId.get().isErfasst());
                }

                @DisplayName("ist löschbar")
                @Nested
                class LoeschenTraeger {

                    @DisplayName("wird inaktiv gesetzt wenn der ZIelort nicht automatisiert angelegt wurde")
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

                        Optional<Zielort> byId = zielortRepository.findById(zielortId);

                        assertTrue(byId.isPresent());
                        assertFalse(byId.get().isAktiv());
                    }


                    @DisplayName("wird nicht gelöscht wenn ZIelort nicht gefunden wurde")
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
}