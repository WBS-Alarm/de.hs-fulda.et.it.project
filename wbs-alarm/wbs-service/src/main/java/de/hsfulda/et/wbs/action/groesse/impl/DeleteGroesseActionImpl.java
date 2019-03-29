package de.hsfulda.et.wbs.action.groesse.impl;

import de.hsfulda.et.wbs.action.groesse.DeleteGroesseAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.repository.GroesseRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component
public class DeleteGroesseActionImpl implements DeleteGroesseAction {

    private final GroesseRepository repo;
    private final AccessService accessService;

    public DeleteGroesseActionImpl(GroesseRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public void perform(WbsUser user, Long id) {
        accessService.hasAccessOnGroesse(user, id, () -> {
            if (!repo.existsById(id)) {
                throw new ResourceNotFoundException("Größe mit ID {0} nicht gefunden.", id);
            }

            repo.deactivate(id);
            return null;
        });
    }
}
