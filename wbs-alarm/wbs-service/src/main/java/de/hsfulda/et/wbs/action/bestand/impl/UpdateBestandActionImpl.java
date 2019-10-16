package de.hsfulda.et.wbs.action.bestand.impl;

import de.hsfulda.et.wbs.action.bestand.UpdateBestandAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.core.dto.BestandDto;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.core.exception.ZielortLockedException;
import de.hsfulda.et.wbs.repository.BestandRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component
public class UpdateBestandActionImpl implements UpdateBestandAction {

    private final BestandRepository repo;
    private final AccessService accessService;

    public UpdateBestandActionImpl(BestandRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public BestandData perform(WbsUser user, Long id, BestandDto bestand) {
        return accessService.hasAccessOnBestand(user, id, () -> {

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
        });
    }
}
