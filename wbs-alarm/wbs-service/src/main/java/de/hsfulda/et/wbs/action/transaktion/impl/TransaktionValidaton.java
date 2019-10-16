package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.core.data.KategorieData;
import de.hsfulda.et.wbs.core.data.ZielortData;
import de.hsfulda.et.wbs.core.dto.PositionDto;
import de.hsfulda.et.wbs.core.dto.TransaktionDto;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.core.exception.TransaktionValidationException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
class TransaktionValidaton {

    private final TransaktionContext context;

    TransaktionValidaton(TransaktionContext context) {
        this.context = context;
    }

    /**
     * Die Transaktion wird an dieser Stelle validiert. Dabei wird geprüft ob die beiden Zielorte dem gleichen Träger
     * zugeordnet sind. Desweiteren wird geprüft, ob die Zielorte und die Größen existieren. Dabei ist zu beachten,
     * dass Zielorte und Größen aktiv sind. Auf inaktive darf nicht gebucht werden. Hierbei wird ein 404 ausgelöst über
     * die {@link ResourceNotFoundException}. Die Erfassung der Bestände muss zudem abgeschlossen sein. Dazu wird
     * geprüft, ob genug Bestand im ausgehenden Zielort vorhanden ist. Es wird zudem geprüft, ob mindestens eine
     * Position ind er Transaktion angegeben wurde. Gleiche Größen (und die dazugehörige Kategorie) darf nicht in
     * verschiendenen Positionen auftauchen.
     *
     * @param dto Übergebene Daten einer Transaktion.
     */
    void validateTransaktionDto(TransaktionDto dto) {
        ZielortData vonZielort = context.getZielortData(dto.getVon());
        ZielortData nachZielort = context.getZielortData(dto.getNach());

        if (!hasSameTraeger(vonZielort, nachZielort)) {
            throw new TransaktionValidationException("Die Zielorte {0} und {1} besitzen nicht den gleichen Träger.",
                    vonZielort.getName(), nachZielort.getName());
        }

        // Ausgehender Zielort erfasst?
        if (!vonZielort.isErfasst()) {
            throw new TransaktionValidationException(
                    "Der Zielort \"{0}\" muss in der Erfassung der Bestände abgeschlossen sein. " +
                            "Bitte wenden Sie sich an ihren Systembetreuer.", vonZielort.getName());
        }

        // Zielort erfasst?
        if (!nachZielort.isErfasst()) {
            throw new TransaktionValidationException(
                    "Der Zielort \"{0}\" muss in der Erfassung der Bestände abgeschlossen sein. " +
                            "Bitte wenden Sie sich an ihren Systembetreuer.", nachZielort.getName());
        }

        // Zielort ist nicht der Wareneingang
        if (nachZielort.isEingang()) {
            throw new TransaktionValidationException(
                    "Der Zielort \"{0}\" ist als Wareneingang definiert. Dieser kann nicht als Zielort angegeben " +
                            "werden.", nachZielort.getName());
        }

        // Es wurde mindestens eine Position angegeben.
        List<PositionDto> positions = dto.getPositions();
        if (positions.isEmpty()) {
            throw new TransaktionValidationException("Es muss mindestens eine Position angegeben werden.");
        }

        Set<Long> duplicates = getDuplicateGroesse(positions);
        if (!duplicates.isEmpty()) {
            throw new TransaktionValidationException(
                    "Die Größe mit der/den ID/s {0} wurde in den Positionen doppelt angegeben.", duplicates);
        }

        // Der Wareneingang in den BEständen nicht aktualisiert.
        if (!vonZielort.isEingang()) {
            // Existiert genug Bestand für eine Position
            positions.forEach(p -> {
                BestandData bestand = context.getBestandData(vonZielort.getId(), p.getGroesse());
                GroesseData groesse = context.getGroesseData(p.getGroesse());

                if (bestand.getAnzahl() < p.getAnzahl()) {
                    KategorieData kategorie = groesse.getKategorie();
                    throw new TransaktionValidationException(
                            "Die Position mit Kategorie \"{0}\", Größe \"{1}\", Anzahl {2} übersteigt den " +
                                    "Bestand vom ausgehenden Zielort {3}.", kategorie.getName(), groesse.getName(),
                            p.getAnzahl(), vonZielort.getName());
                }
            });
        }
    }

    private static Set<Long> getDuplicateGroesse(List<PositionDto> positions) {
        List<Long> numbers = positions.stream()
                .map(PositionDto::getGroesse)
                .collect(Collectors.toList());
        return numbers.stream()
                .filter(i -> Collections.frequency(numbers, i) > 1)
                .collect(Collectors.toSet());
    }

    private boolean hasSameTraeger(ZielortData von, ZielortData nach) {
        return Objects.equals(von.getTraeger(), nach.getTraeger());
    }

}
