package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.ResourceTest;
import de.hsfulda.et.wbs.entity.Bestand;
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
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Die Bestand Resource")
class BestandResourceTest extends ResourceTest {

    @Autowired
    private BestandResource resource;

    @Autowired
    private BestandListResource listResource;

    @DisplayName("wird im Spring Context geladen und gefunden")
    @Test
    void contextLoads() {
        assertThat(resource).isNotNull();
    }

    @DisplayName("wird aufgelistet zu dem Träger des Anwenders aufgelistet.")
    @Test
    void getBestandList() throws Exception {
        Long zielortId = getZielortId("Wareneingang", FW_TRAEGER);
        mockMvc.perform(get(BestandListResource.PATH, zielortId)
            .header("Authorization", getTokenAsSuperuser())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$._links.self[0].templated", is(true)))
            .andExpect(jsonPath("$._embedded.elemente[0]._embedded.groesse[0].name", is("XXL")))
            .andExpect(jsonPath("$._embedded.elemente[0]._embedded.kategorie[0].name", is("Polo-Hemd")))
            .andExpect(jsonPath("$._embedded.elemente[0].anzahl", is(10)));
    }

    @DisplayName("wird aufgelistet zu dem Träger des Anwenders aufgelistet.")
    @Test
    void getBestand() throws Exception {
        Long bestandId = getBestandId(FW_TRAEGER, "Wareneingang", "XXL");
        mockMvc.perform(get(BestandResource.PATH, bestandId)
            .header("Authorization", getTokenAsSuperuser())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._links.self[0].templated", is(false)))
            .andExpect(jsonPath("$.anzahl", is(10)))
            .andExpect(jsonPath("$.id", is(bestandId.intValue())))
            .andExpect(jsonPath("$._embedded.zielort[0].name", is("Wareneingang")))
            .andExpect(jsonPath("$._embedded.kategorie[0].name", is("Polo-Hemd")))
            .andExpect(jsonPath("$._embedded.groesse[0].name", is("XXL")));
    }

    @DisplayName("wird von einem anderen Träger nicht angezeigt.")
    @Test
    void getBestandNotFound() throws Exception {
        Long bestandId = getBestandId(FW_TRAEGER, "Wareneingang", "XXL");
        mockMvc.perform(get(BestandResource.PATH, bestandId)
            .header("Authorization", getToken(HE_USER))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @DisplayName("kann für einen Träger erstellt werden")
    @Test
    void putAendernEinesBestands() throws Exception {
        Long zielortId = getZielortId("Wäscherei", HE_TRAEGER);
        Long groesseId = getGroesseId("XXL", "Polo-Hemd", HE_TRAEGER);
        mockMvc.perform(post(BestandListResource.PATH, zielortId)
            .header("Authorization", getToken(HE_USER))
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n \"anzahl\": 5,\n \"groesseId\" : " + groesseId + "\n}"))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", startsWith("/wbs/bestand/")));
    }

    @DisplayName("bei Änderungen")
    @Nested
    class BestandAenderungen {

        @DisplayName("wird nicht geändert wenn die Anzahl negativ ist")
        @Test
        void putAendernErstellterBestandKeinName() throws Exception {
            Long bestandId = getBestandId(HE_TRAEGER, "Wäscherei", "XXL");
            mockMvc.perform(put(BestandResource.PATH, bestandId)
                .header("Authorization", getToken(HE_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n \"anzahl\": -10 \n}"))
                .andExpect(status().isBadRequest());
        }

        @DisplayName("wird nicht geändert wenn der Zielort gelocked ist")
        @Test
        void putAendernErstellterBestandZielortLocked() throws Exception {
            Optional<Zielort> z = zielortRepository.findById(getZielortId("Wäscherei", HE_TRAEGER));
            Zielort ort = z.get();
            ort.setErfasst(true);
            zielortRepository.save(ort);

            Long bestandId = getBestandId(HE_TRAEGER, "Wäscherei", "XXL");
            mockMvc.perform(put(BestandResource.PATH, bestandId)
                .header("Authorization", getToken(HE_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n \"anzahl\": 100 \n}"))
                .andExpect(status().isLocked());

            ort.setErfasst(false);
            zielortRepository.save(ort);

        }

        @DisplayName("wird nicht geändert wenn es sich bei der Anzahl um einen String handelt")
        @Test
        void putAendernStandardBestand() throws Exception {
            Long bestandId = getBestandId(HE_TRAEGER, "Wäscherei", "XXL");
            mockMvc.perform(put(BestandResource.PATH, bestandId)
                .header("Authorization", getToken(HE_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n \"anzahl\": \"Wasanderes\"\n}"))
                .andExpect(status().isBadRequest());
        }

        @DisplayName("valide")
        @Nested
        class AenderBestand {

            @DisplayName("wird geändert wenn der Bestand valide übergeben wird")
            @Test
            void putAendernErstellterBestand() throws Exception {
                Long bestandId = getBestandId(HE_TRAEGER, "Wäscherei", "XXL");
                mockMvc.perform(put(BestandResource.PATH, bestandId)
                    .header("Authorization", getToken(HE_USER))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n \"anzahl\": 100 \n}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$._links.self[0].templated", is(false)))
                    .andExpect(jsonPath("$.anzahl", is(100)))
                    .andExpect(jsonPath("$._embedded.zielort[0].name", is("Wäscherei")));
            }

            @DisplayName("ist löschbar")
            @Nested
            class LoeschenTraeger {

                @DisplayName("wird erfolgreich gelöscht")
                @Test
                void deleteDeleteValid() throws Exception {
                    Long bestandId = getBestandId(HE_TRAEGER, "Wäscherei", "XXL");
                    mockMvc.perform(delete(BestandResource.PATH, bestandId)
                        .header("Authorization", getToken(HE_USER))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

                    // Und nicht mehr auffindbar
                    mockMvc.perform(get(BestandResource.PATH, bestandId)
                        .header("Authorization", getToken(HE_USER))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());

                    Optional<Bestand> byId = bestandRepository.findById(bestandId);

                    assertFalse(byId.isPresent());
                }


                @DisplayName("wird nicht gelöscht wenn Bestand nicht gefunden wurde")
                @Test
                void deleteNotFoun() throws Exception {
                    mockMvc.perform(delete(BestandResource.PATH, 0L)
                        .header("Authorization", getToken(HE_USER))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
                }
            }
        }
    }
}