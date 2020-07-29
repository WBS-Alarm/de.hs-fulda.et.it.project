package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.core.data.*;
import de.hsfulda.et.wbs.core.dto.PositionDto;
import de.hsfulda.et.wbs.core.dto.TransaktionDto;
import de.hsfulda.et.wbs.core.exception.TransaktionValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class TransaktionValidatonTest {

    @Mock
    private TransaktionDao context;

    @Mock
    private TransaktionDto dto;

    @InjectMocks
    TransaktionValidaton sut;

    @BeforeEach
    void setup() {
        given(dto.getVon()).willReturn(1L);
        given(dto.getNach()).willReturn(2L);
    }

    @Nested
    class ZielortValidation {

        @Mock
        ZielortData von;
        @Mock
        ZielortData nach;

        @BeforeEach
        void setup() {
            given(context.getZielortData(anyLong())).willAnswer((Answer<ZielortData>) invocationOnMock -> {
                Long argument = (Long) invocationOnMock.getArgument(0);
                switch (argument.toString()) {
                    case "1":
                        return von;
                    case "2":
                        return nach;
                }
                return null;
            });

            lenient().when(von.getName())
                    .thenReturn("Name Von");
            lenient().when(von.getId())
                    .thenReturn(1L);
            lenient().when(nach.getName())
                    .thenReturn("Name Nach");
            lenient().when(nach.getId())
                    .thenReturn(2L);
        }

        @Test
        void notSameTraeger(@Mock TraegerData traeger, @Mock TraegerData otherTraeger) {
            given(von.getTraeger()).willReturn(traeger);
            given(nach.getTraeger()).willReturn(otherTraeger);

            assertEquals("Die Zielorte Name Von und Name Nach besitzen nicht den gleichen Träger.",
                    assertThrows(TransaktionValidationException.class,
                            () -> sut.validateTransaktionDto(dto)).getMessage());

        }

        @Test
        void vonNotErfasst(@Mock TraegerData traeger) {
            given(von.getTraeger()).willReturn(traeger);
            given(nach.getTraeger()).willReturn(traeger);

            assertEquals("Der Zielort \"Name Von\" muss in der Erfassung der Bestände abgeschlossen sein. " +
                    "Bitte wenden Sie sich an ihren Systembetreuer.", assertThrows(TransaktionValidationException.class,
                    () -> sut.validateTransaktionDto(dto)).getMessage());

        }

        @Test
        void nachNotErfasst(@Mock TraegerData traeger) {
            given(von.getTraeger()).willReturn(traeger);
            given(nach.getTraeger()).willReturn(traeger);
            given(von.isErfasst()).willReturn(true);

            assertEquals("Der Zielort \"Name Nach\" muss in der Erfassung der Bestände abgeschlossen sein. " +
                    "Bitte wenden Sie sich an ihren Systembetreuer.", assertThrows(TransaktionValidationException.class,
                    () -> sut.validateTransaktionDto(dto)).getMessage());
        }

        @Test
        void nachNotWareneingang(@Mock TraegerData traeger) {
            given(von.getTraeger()).willReturn(traeger);
            given(nach.getTraeger()).willReturn(traeger);
            given(von.isErfasst()).willReturn(true);
            given(nach.isErfasst()).willReturn(true);
            given(nach.isEingang()).willReturn(true);

            assertEquals("Der Zielort \"Name Nach\" ist als Wareneingang definiert. Dieser kann nicht als " +
                    "Zielort angegeben werden.", assertThrows(TransaktionValidationException.class,
                    () -> sut.validateTransaktionDto(dto)).getMessage());

        }

        @Nested
        class PositionsValidation {

            @BeforeEach
            void setup(@Mock TraegerData traeger) {
                given(von.getTraeger()).willReturn(traeger);
                given(nach.getTraeger()).willReturn(traeger);
                given(von.isErfasst()).willReturn(true);
                given(nach.isErfasst()).willReturn(true);
            }

            @Test
            void noPositionsDefined() {
                assertEquals("Es muss mindestens eine Position angegeben werden.",
                        assertThrows(TransaktionValidationException.class,
                                () -> sut.validateTransaktionDto(dto)).getMessage());
            }

            @Test
            void doubledPositionDefined() {
                given(dto.getPositions()).willReturn(Arrays.asList(createPosition(10L, 5L), createPosition(10L, 100L)));

                assertEquals("Die Größe mit der/den ID/s [10] wurde in den Positionen doppelt angegeben.",
                        assertThrows(TransaktionValidationException.class,
                                () -> sut.validateTransaktionDto(dto)).getMessage());
            }

            @Test
            void doubledPositionsDefined() {
                given(dto.getPositions()).willReturn(
                        Arrays.asList(createPosition(10L, 5L), createPosition(10L, 100L), createPosition(11L, 100L),
                                createPosition(11L, 100L)));

                assertEquals("Die Größe mit der/den ID/s [10, 11] wurde in den Positionen doppelt angegeben.",
                        assertThrows(TransaktionValidationException.class,
                                () -> sut.validateTransaktionDto(dto)).getMessage());
            }

            @Test
            void vonIsEingang() {
                given(von.isEingang()).willReturn(true);
                given(dto.getPositions()).willReturn(Arrays.asList(createPosition(10L, 5L), createPosition(11L, 5L)));

                sut.validateTransaktionDto(dto);
            }

            @Nested
            class BestandValidation {
                @Mock
                private BestandData bestand;
                @Mock
                private GroesseData groesse;

                @BeforeEach
                void setup() {
                    given(dto.getPositions()).willReturn(Collections.singletonList(createPosition(10L, 5L)));

                    given(context.getBestandData(eq(1L), anyLong())).willReturn(bestand);
                    given(context.getGroesseData(anyLong())).willReturn(groesse);
                }

                @Test
                void positionAnzahlGtBestand(@Mock KategorieData kategorie) {
                    given(groesse.getKategorie()).willReturn(kategorie);

                    given(groesse.getName()).willReturn("Name Größe");
                    given(kategorie.getName()).willReturn("Name Kategorie");

                    given(bestand.getAnzahl()).willReturn(4L);

                    assertEquals("Die Position mit Kategorie \"Name Kategorie\", Größe \"Name Größe\", Anzahl 5 " +
                                    "übersteigt den Bestand vom ausgehenden Zielort Name Von.",
                            assertThrows(TransaktionValidationException.class,
                                    () -> sut.validateTransaktionDto(dto)).getMessage());
                }

                @Test
                void positionAnzahlEqBestand() {
                    given(bestand.getAnzahl()).willReturn(5L);

                    sut.validateTransaktionDto(dto);
                }

                @Test
                void positionAnzahlLtBestand() {
                    given(bestand.getAnzahl()).willReturn(6L);

                    sut.validateTransaktionDto(dto);
                }
            }
        }
    }

    PositionDto createPosition(Long groesse, Long anzahl) {
        return new PositionDto() {
            @Override
            public Long getAnzahl() {
                return anzahl;
            }

            @Override
            public Long getGroesse() {
                return groesse;
            }
        };
    }
}