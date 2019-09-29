package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.action.transaktion.AddTransaktionAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.core.data.ZielortData;
import de.hsfulda.et.wbs.core.dto.TransaktionDto;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.core.exception.TransaktionValidationException;
import de.hsfulda.et.wbs.repository.BestandRepository;
import de.hsfulda.et.wbs.repository.GroesseRepository;
import de.hsfulda.et.wbs.repository.ZielortRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Transactional
@Component
public class AddTransaktionActionImpl implements AddTransaktionAction {

    private final GroesseRepository groessen;
    private final BestandRepository bestaende;
    private final ZielortRepository zielorte;
    private final AccessService accessService;

    public AddTransaktionActionImpl(
            GroesseRepository groessen,
            BestandRepository bestaende,
            ZielortRepository zielorte,
            AccessService accessService) {
        this.groessen = groessen;
        this.bestaende = bestaende;
        this.zielorte = zielorte;
        this.accessService = accessService;
    }

    @Override
    public TransaktionData perform(WbsUser user, TransaktionDto dto) {
        return accessService.hasAccessOnTransaktion(user, dto, () -> {

            validateTransaktionDto(dto);
            return null;
        });
    }

    /**
     * TODO: Was passiert beim Einkauf?
     *
     * Die Transaktion wird an dieser Stelle validiert. Dabei wird geprüft ob die beiden Zielorte dem gleichen  Träger
     * zugeordnet sind. Desweiteren wird geprüft, ob die Zielorte und die Größen existieren. Dabei ist zu beachten,
     * dass Zielorte und Größen aktiv sind. Auf inaktive darf nicht gebucht werden. Hierbei wird ein 404 ausgelöst über
     * die {@link ResourceNotFoundException}. Die Erfassung der Besände muss zudem abgeschlossen sein. Dazu wird
     * geprüft, ob genug Bestand im ausgehenden Zielort vorhanden ist.
     *
     * @param dto Übergebene Daten einer Transaktion.
     */
    private void validateTransaktionDto(TransaktionDto dto) {
        ZielortData vonZielort = getZielort(dto.getVon());
        ZielortData nachZielort = getZielort(dto.getNach());

        if (hasSameTraeger(vonZielort, nachZielort)) {
            throw new TransaktionValidationException(
                    "Die Zielorte {0} und {1} besitzen nicht den gleichen Träger.", vonZielort.getName(),
                    nachZielort.getName());
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

        // Existiert genug Bestand für eine Position
        dto.getPositions().forEach(p -> {
            BestandData bestand = getBestand(vonZielort.getId(), p.getGroesse());
            GroesseData groesse = getGroesse(p.getGroesse());

            if (bestand.getAnzahl() < p.getAnzahl()) {
                throw new TransaktionValidationException(
                        "Die Position mit Kategorie \"{0}\", Größe \"{1}\", Anzahl {2} übersteigt den Bestand vom ausgehenden Zielort {3}.",
                        groesse.getKategorie().getName(), groesse.getName(), p.getAnzahl(), bestand.getZielort().getName());
            }
        });
    }

    private boolean hasSameTraeger(ZielortData von, ZielortData nach) {
        return Objects.equals(von.getTraeger(), nach.getTraeger());
    }

    private ZielortData getZielort(Long id) {
        Optional<ZielortData> managed = zielorte.findByIdAndAktivIsTrue(id);
        return managed.orElseThrow(() -> new ResourceNotFoundException("Zielort mit ID {0} nicht gefunden.", id));
    }

    private GroesseData getGroesse(Long id) {
        Optional<GroesseData> managed = groessen.findByIdAndAktivIsTrue(id);
        return managed.orElseThrow(() -> new ResourceNotFoundException("Groesse mit ID {0} nicht gefunden.", id));
    }

    private BestandData getBestand(Long zielortId, Long groesseId) {
        Optional<BestandData> managed = bestaende.findByZielortIdAndGroesseId(zielortId, groesseId);
        return managed.orElseThrow(() ->
                new TransaktionValidationException("Für den Zielort {0} wurde kein Bestand gefunden (Größe {1}).",
                        zielortId, groesseId));
    }
}
