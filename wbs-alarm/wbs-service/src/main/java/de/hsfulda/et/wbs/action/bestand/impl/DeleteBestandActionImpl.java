package de.hsfulda.et.wbs.action.bestand.impl;

import de.hsfulda.et.wbs.action.bestand.DeleteBestandAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.repository.BestandRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component
public class DeleteBestandActionImpl implements DeleteBestandAction {

    private final BestandRepository repo;
    private final AccessService accessService;

    public DeleteBestandActionImpl(BestandRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public void perform(WbsUser user, Long id) {
        accessService.hasAccessOnBestand(user, id, () -> {

            if (!repo.existsById(id)) {
                throw new ResourceNotFoundException("Bestand mit ID {0} nicht gefunden.", id);
            }

            if (repo.isZielortErfasst(id)) {
                throw new IllegalStateException(
                        "Zielort wurde bereits vollständig erfasst und kann Bestände nicht mehr löschen");
            }

            repo.deleteById(id);
            return null;
        });
    }
}
