package de.hsfulda.et.wbs.action.benutzer.impl;

import de.hsfulda.et.wbs.action.benutzer.DeleteBenutzerAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component
public class DeleteBenutzerActionImpl implements DeleteBenutzerAction {

    private final BenutzerRepository repo;
    private final AccessService accessService;

    public DeleteBenutzerActionImpl(BenutzerRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public void perform(WbsUser user, Long id) {
        accessService.hasAccessOnBenutzer(user, id, () -> {
            if (!repo.existsById(id)) {
                throw new ResourceNotFoundException("Benutzer mit ID {0} nicht gefunden.", id);
            }

            repo.deactivate(id);
            return null;
        });

    }
}
