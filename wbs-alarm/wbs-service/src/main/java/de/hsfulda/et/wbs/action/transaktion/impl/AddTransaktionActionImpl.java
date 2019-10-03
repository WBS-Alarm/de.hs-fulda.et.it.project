package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.action.bestand.CreateBestandAction;
import de.hsfulda.et.wbs.action.bestand.UpdateBestandAction;
import de.hsfulda.et.wbs.action.transaktion.AddTransaktionAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.*;
import de.hsfulda.et.wbs.core.dto.PositionDto;
import de.hsfulda.et.wbs.core.dto.TransaktionDto;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.core.exception.TransaktionValidationException;
import de.hsfulda.et.wbs.entity.Bestand;
import de.hsfulda.et.wbs.entity.Position;
import de.hsfulda.et.wbs.entity.Transaktion;
import de.hsfulda.et.wbs.repository.TransaktionRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
@Component
public class AddTransaktionActionImpl implements AddTransaktionAction {

    private final TransaktionContext context;
    private final TransaktionRepository transaktionen;
    private final CreateBestandAction createBestand;
    private final UpdateBestandAction updateBestand;
    private final AccessService accessService;

    public AddTransaktionActionImpl(TransaktionContext context, TransaktionRepository transaktionen,
            CreateBestandAction createBestand, UpdateBestandAction updateBestand, AccessService accessService) {
        this.context = context;
        this.transaktionen = transaktionen;
        this.createBestand = createBestand;
        this.updateBestand = updateBestand;
        this.accessService = accessService;
    }

    @Override
    public TransaktionData perform(WbsUser user, TransaktionDto dto) {
        return accessService.hasAccessOnTransaktion(user, dto, () -> {

            validateTransaktionDto(dto);
            Transaktion transaktion = createTransaktion(user, dto);

            return transaktionen.save(transaktion);
        });
    }

    private Transaktion createTransaktion(WbsUser user, TransaktionDto dto) {
        Transaktion.TransaktionBuilder builder = Transaktion.builder()
                .setBenutzer(context.getBenutzer(user))
                .setDatum(LocalDateTime.now())
                .setVon(context.getZielort(dto.getVon()))
                .setNach(context.getZielort(dto.getNach()));

        dto.getPositions()
                .forEach(p -> {
                    builder.addPosition(Position.builder()
                            .setAnzahl(p.getAnzahl())
                            .setGroesse(context.getGroesse(p.getGroesse()))
                            .build());

                    updateVonBestand(user, p, dto.getVon());
                    updateNachBestand(user, p, dto.getNach());
                });

        return builder.build();
    }

    /**
     * Ermittelt den Bestand vom Ausgangsort und aktualisiert den Bestand indem die Anzahl der Position abgezogen wird.
     * Wenn Bestand nicht existiert, wird ein Fehler geworfen.
     *
     * @param position Position, die verarbeitet wird.
     * @param von Zielort von dem eine Bestandteil an einen anderen Zielort übergeben werden soll.
     */
    private void updateVonBestand(WbsUser user, PositionDto position, Long von) {
        Optional<Bestand> vonBestand = context.getBestand(von, position.getGroesse());
        if (!vonBestand.isPresent()) {
            throw new ResourceNotFoundException(
                    "Es gibt keinen Bestand von dem die eine Position nicht abgebucht werden kann.");
        }

        substractAnzahl(user, position, vonBestand.get());
    }

    /**
     * Aktualisieren der Anzahl indem die in der Position übergebene Anzahl abgezogen wird.
     *
     * @param position Position, die verarbeitet wird.
     * @param bestand Zu aktualisierender Bestand.
     */
    private void substractAnzahl(WbsUser user, PositionDto position, Bestand bestand) {
        updateBestand.perform(user, bestand.getId(), () -> bestand.getAnzahl() - position.getAnzahl());
    }

    /**
     * Aktualisiert den Bestand des Zielorts an den eine Position abgegeben wird. Existiert noch kein Bestand beim
     * Zielort wird dieser erstellt und mit keinem Bestand initialisiert. danach wird die Anzahl der aktuellen
     * Position dem Bestand hinzugerechnet.
     *
     * @param user Aktueller Anwender.
     * @param position Position, die verarbeitet wird.
     * @param nach Zielort, an den die Position geht.
     */
    private void updateNachBestand(WbsUser user, PositionDto position, Long nach) {
        Bestand nachBestand = getNachBestand(user, position, nach);
        addAnzahl(user, position, nachBestand);
    }

    /**
     * Ermittelt den Bestand anhan des Zielorts und der Position. Sollte der Bestand nciht existieren wird er angelegt.
     *
     * @param user Aktueller Anwender.
     * @param position Position, die verarbeitet wird.
     * @param nach Zielort, an den die Position geht.
     * @return Persitierter Bestand.
     */
    private Bestand getNachBestand(WbsUser user, PositionDto position, Long nach) {
        Optional<Bestand> nachBestand = context.getBestand(nach, position.getGroesse());
        if (!nachBestand.isPresent()) {
            BestandData created = createBestand.perform(user, nach, BestandCreateDtoImpl.of(position));
            return context.getBestand(created.getId());
        }
        return nachBestand.get();
    }

    /**
     * Aktualisieren der Anzahl indem die in der Position übergebene Anzahl hinzugefügt wird.
     *
     * @param position Position, die verarbeitet wird.
     * @param bestand Zu aktualisierender Bestand.
     */
    private void addAnzahl(WbsUser user, PositionDto position, Bestand bestand) {
        updateBestand.perform(user, bestand.getId(), () -> bestand.getAnzahl() + position.getAnzahl());
    }

    /**
     * Die Transaktion wird an dieser Stelle validiert. Dabei wird geprüft ob die beiden Zielorte dem gleichen  Träger
     * zugeordnet sind. Desweiteren wird geprüft, ob die Zielorte und die Größen existieren. Dabei ist zu beachten,
     * dass Zielorte und Größen aktiv sind. Auf inaktive darf nicht gebucht werden. Hierbei wird ein 404 ausgelöst über
     * die {@link ResourceNotFoundException}. Die Erfassung der Bestände muss zudem abgeschlossen sein. Dazu wird
     * geprüft, ob genug Bestand im ausgehenden Zielort vorhanden ist. Es wird zudem geprüft, ob mindestens eine
     * Position ind er Transaktion angegeben wurde. Gleiche Größen (und die dazugehörige Kategorie) darf nicht in
     * verschiendenen Positionen auftauchen.
     *
     * @param dto Übergebene Daten einer Transaktion.
     */
    private void validateTransaktionDto(TransaktionDto dto) {
        ZielortData vonZielort = context.getZielortData(dto.getVon());
        ZielortData nachZielort = context.getZielortData(dto.getNach());

        if (hasSameTraeger(vonZielort, nachZielort)) {
            throw new TransaktionValidationException("Die Zielorte {0} und {1} besitzen nicht den gleichen Träger.",
                    vonZielort.getName(), nachZielort.getName());
        }

        // Ausgehender Zielort erfasst?
        if (!vonZielort.isErfasst()) {
            throw new TransaktionValidationException(
                    "Der Zielort \"{0}\" muss in der Erfassung der Bestände abgeschlossen sein. " +
                            "Bitte wenden Sie sich an ihren Systembetreuer, ", vonZielort.getName());
        }

        // Zielort erfasst?
        if (!nachZielort.isErfasst()) {
            throw new TransaktionValidationException(
                    "Der Zielort \"{0}\" muss in der Erfassung der Bestände abgeschlossen sein. " +
                            "Bitte wenden Sie sich an ihren Systembetreuer, ", nachZielort.getName());
        }

        // Es wurde mindestens eine Position angegeben.
        List<PositionDto> positions = dto.getPositions();
        if (positions.isEmpty()) {
            throw new TransaktionValidationException("Es muss mindestens eine Position angegeben werden.");
        }

        // Eine Größe darf in den Positionen nur einmal vorkommen.
        positions.forEach(p -> {
            if (positions.stream()
                    .anyMatch(pi -> Objects.equals(p.getGroesse(), pi.getGroesse()))) {
                throw new TransaktionValidationException(
                        "Die Größe mit der ID {0} wurde in den POsitionen doppelt " + "angegeben.", p.getGroesse());
            }
        });

        // Existiert genug Bestand für eine Position
        positions.forEach(p -> {
            BestandData bestand = context.getBestandData(vonZielort.getId(), p.getGroesse());
            GroesseData groesse = context.getGroesseData(p.getGroesse());

            if (bestand.getAnzahl() < p.getAnzahl()) {
                KategorieData kategorie = groesse.getKategorie();
                throw new TransaktionValidationException(
                        "Die Position mit Kategorie \"{0}\", Größe \"{1}\", Anzahl {2} übersteigt den " +
                                "Bestand vom ausgehenden Zielort {3}.", kategorie.getName(), groesse.getName(),
                        p.getAnzahl(), bestand.getZielort()
                        .getName());
            }
        });
    }

    private boolean hasSameTraeger(ZielortData von, ZielortData nach) {
        return Objects.equals(von.getTraeger(), nach.getTraeger());
    }
}
