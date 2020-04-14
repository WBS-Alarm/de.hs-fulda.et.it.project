package de.hsfulda.et.wbs.http.resource;

import de.hsfulda.et.wbs.ResourceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Die Transaktion Resource")
class TransaktionResourceTest extends ResourceTest {

    @Autowired
    private TransaktionResource resource;

    @DisplayName("wird im Spring Context geladen und gefunden")
    @Test
    void contextLoads() {
        assertThat(resource).isNotNull();
    }

    @DisplayName("Transaktionen werden zu Träger aufgelistet, Seitengröße 5")
    @Test
    void getTransaktionListSizeFive() throws Exception {
        Long traegerId = getTraegerId(HE_TRAEGER);
        mockMvc.perform(get(TransaktionListResource.PATH, traegerId).param("size", "5")
                .header("Authorization", getToken("HelsaUser"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size", is(5)))
                .andExpect(jsonPath("$.page", is(0)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(1)));
    }

    @DisplayName("Transaktionen werden zu Träger aufgelistet, Seitengröße 20")
    @Test
    void getTransaktionList() throws Exception {
        Long traegerId = getTraegerId(HE_TRAEGER);
        mockMvc.perform(get(TransaktionListResource.PATH, traegerId).header("Authorization", getToken("HelsaUser"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size", is(20)))
                .andExpect(jsonPath("$.page", is(0)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(1)));
    }

    @DisplayName("JSON kann interpretiert werden, invalide Daten.")
    @Test
    void putAendernEinesZielorts() throws Exception {
        Long traegerId = getTraegerId(HE_TRAEGER);
        mockMvc.perform(post(TransaktionListResource.PATH, traegerId).header("Authorization", getToken("HelsaBuchung"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"von\":-12,\"nach\":-10,\"positions\":[{\"groesse\":-1,\"anzahl\":1}]}"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Zielort mit ID -12 nicht gefunden.")));
    }
}