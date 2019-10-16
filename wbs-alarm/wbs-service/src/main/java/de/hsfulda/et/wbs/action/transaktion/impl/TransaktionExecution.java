package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.action.bestand.CreateBestandAction;
import de.hsfulda.et.wbs.action.bestand.UpdateBestandAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.core.dto.PositionDto;
import de.hsfulda.et.wbs.core.dto.TransaktionDto;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.entity.Bestand;
import de.hsfulda.et.wbs.entity.Position;
import de.hsfulda.et.wbs.entity.Transaktion;
import de.hsfulda.et.wbs.entity.Zielort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
class TransaktionExecution {

    private final TransaktionContext context;
    private final CreateBestandAction createBestand;
    private final UpdateBestandAction updateBestand;

    TransaktionExecution(TransaktionContext context, CreateBestandAction createBestand,
            UpdateBestandAction updateBestand) {
        this.context = context;
        this.createBestand = createBestand;
        this.updateBestand = updateBestand;
    }

    Transaktion createTransaktion(WbsUser user, TransaktionDto dto) {
        Zielort vonZielort = context.getZielort(dto.getVon());
        Transaktion.TransaktionBuilder builder = Transaktion.builder()
                .setBenutzer(context.getBenutzer(user))
                .setDatum(LocalDateTime.now())
                .setVon(vonZielort)
                .setNach(context.getZielort(dto.getNach()));

        dto.getPositions()
                .forEach(p -> {
                    builder.addPosition(Position.builder()
                            .setAnzahl(p.getAnzahl())
                            .setGroesse(context.getGroesse(p.getGroesse()))
                            .build());

                    // Der Wareneingang soll nicht gebucht werden.
                    if (!vonZielort.isEingang()) {
                        updateVonBestand(user, p, dto.getVon());
                    }
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
}
