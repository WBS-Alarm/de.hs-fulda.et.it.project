package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.core.dto.BestandDto;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.core.exception.ZielortLockedException;
import de.hsfulda.et.wbs.repository.BestandRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component
public class UpdateBestandForTransaktion  {

    private final BestandRepository repo;

    public UpdateBestandForTransaktion(BestandRepository repo) {
        this.repo = repo;
    }

    public BestandData perform(Long id, BestandDto bestand) {
            if (!repo.existsById(id)) {
                throw new ResourceNotFoundException("Bestand mit ID {0} nicht gefunden.", id);
            }

            if (bestand.getAnzahl() < 0) {
                throw new IllegalArgumentException("Die Anzahl im Bestand darf nicht negativ sein.");
            }

            if (repo.isZielortErfasst(id)) {
                throw new ZielortLockedException(
                        "Zielort wurde bereits vollständig erfasst und kann Bestände nicht mehr ändern");
            }

            repo.updateAnzahl(id, bestand.getAnzahl());
            return repo.findByIdAsData(id)
                    .get();
    }
}
