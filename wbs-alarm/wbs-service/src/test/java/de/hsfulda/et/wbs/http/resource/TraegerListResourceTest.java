package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.ResourceTest;
import de.hsfulda.et.wbs.util.UriUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test der Traeger Resourcen.")
class TraegerListResourceTest extends ResourceTest {

    @Autowired
    private TraegerListResource listResource;

    @Autowired
    private TraegerResource resource;

    @DisplayName("Laden der Resourcen erfolgreich.")
    @Test
    void contextLoads() {
        assertThat(resource).isNotNull();
        assertThat(listResource).isNotNull();
    }

    @DisplayName("Ermitteln aller Traeger")
    @Test
    void getAll() throws Exception {
        mockMvc.perform(get(TraegerListResource.PATH)
            .header("Authorization", getTokenAsSuperuser()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._links.self[0].href", is(TraegerListResource.PATH)))
            .andExpect(jsonPath("$._links.self[0].templated", is(false)))
            .andExpect(jsonPath("$._embedded.elemente[0].id", is(1)))
            .andExpect(jsonPath("$._embedded.elemente[0].name", is("Feuerwehr")));
    }

    @DisplayName("Hinzufügen eines neuen Trägers")
    @Test
    void postNew() throws Exception {
        mockMvc.perform(post(TraegerListResource.PATH)
            .header("Authorization", getTokenAsSuperuser())
            .content("{\n \"name\": \"Kassel\"\n}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$._links.self[0].templated", is(false)))
            .andExpect(jsonPath("$.id", is(2)))
            .andExpect(jsonPath("$.name", is("Kassel")));
    }

    @DisplayName("Hinzufügen eines neuen Trägers ohne Berechtigung")
    @Test
    void postNewNoPermissions() throws Exception {
        mockMvc.perform(post(TraegerListResource.PATH)
                .header("Authorization", getToken("Leser"))
                .content("{\n \"name\": \"Haueda\"\n}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @DisplayName("Hinzufügen eines neuen Trägers - Ohne Namen")
    @Test
    void postNewNoName() throws Exception {
        mockMvc.perform(post(TraegerListResource.PATH)
            .header("Authorization", getTokenAsSuperuser())
            .content("{\n \"name\": \"\"\n}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }


    @DisplayName("Hinzufügen eines neuen Trägers - Ohne RequestBody")
    @Test
    void postNewNoBody() throws Exception {
        mockMvc.perform(post(TraegerListResource.PATH)
            .header("Authorization", getTokenAsSuperuser())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("Aktionen auf einen Träger")
    @Nested
    class TraegerTest {

        @DisplayName("Ermitteln eines Trägers")
        @Test
        void getKassel() throws Exception {
            String resourceLink = UriUtil.build(TraegerResource.PATH, 2L);
            mockMvc.perform(get(TraegerResource.PATH, 2L)
                .header("Authorization", getTokenAsSuperuser()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$._links.self[0].href", is(resourceLink)))
                .andExpect(jsonPath("$._links.self[0].templated", is(false)))
                .andExpect(jsonPath("$._links.delete[0].href", is(resourceLink)))
                .andExpect(jsonPath("$._links.delete[0].templated", is(false)))
                .andExpect(jsonPath("$._links.update[0].href", is(resourceLink)))
                .andExpect(jsonPath("$._links.update[0].templated", is(false)))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Kassel")));
        }

        @DisplayName("Ermitteln eines Trägers - nicht gefunden")
        @Test
        void getNotFound() throws Exception {
            mockMvc.perform(get(TraegerResource.PATH, 0L)
                .header("Authorization", getTokenAsSuperuser()))
                .andExpect(status().isNotFound());
        }

        @DisplayName("Ändern des Names vom Träger")
        @Nested
        class TraegerChangeTest {

            @DisplayName("Ändern eines Trägers")
            @Test
            void putKassel() throws Exception {
                mockMvc.perform(put(TraegerResource.PATH, 2L)
                    .header("Authorization", getTokenAsSuperuser())
                    .content("{\n \"name\": \"Fulda\"\n}")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(2)))
                    .andExpect(jsonPath("$.name", is("Fulda")));
            }

            @DisplayName("Ändern eines Trägers - der Name ist leer")
            @Test
            void putKasselNoName() throws Exception {
                mockMvc.perform(put(TraegerResource.PATH, 2L)
                    .header("Authorization", getTokenAsSuperuser())
                    .content("{\n \"name\": \"\"\n}")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
            }

            @DisplayName("Löschen des neuen Trägers")
            @Nested
            class TraegerDeleteTest {

                @DisplayName("Träger ist vorhanden")
                @Test
                void deleteKassel() throws Exception {
                    mockMvc.perform(delete(TraegerResource.PATH, 2L)
                        .header("Authorization", getTokenAsSuperuser()))
                        .andExpect(status().isOk());
                }

                @DisplayName("Träger ist unbekannt")
                @Test
                void deleteNotFound() throws Exception {
                    mockMvc.perform(delete(TraegerResource.PATH, 0L)
                        .header("Authorization", getTokenAsSuperuser()))
                        .andExpect(status().isNotFound());
                }

                @DisplayName("Sicherstellen das Träger nicht mehr da ist")
                @Nested
                class DeletedNotFoundTest {

                    @DisplayName("Gelöschter Träger nicht gefunden")
                    @Test
                    void getNotFound() throws Exception {
                        mockMvc.perform(get(TraegerResource.PATH, 2L)
                            .header("Authorization", getTokenAsSuperuser()))
                            .andExpect(status().isNotFound());
                    }
                }
            }
        }

    }
}