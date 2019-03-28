package de.hsfulda.et.wbs.action.zielort.impl;

import de.hsfulda.et.wbs.action.zielort.DeleteZielortAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.repository.ZielortRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class DeleteZielortActionImpl implements DeleteZielortAction {

    private final ZielortRepository repo;
    private final AccessService accessService;

    public DeleteZielortActionImpl(ZielortRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public void perform(WbsUser user, Long id) {
        accessService.hasAccessOnZielort(user, id, () -> {
            if (!repo.existsById(id)) {
                throw new ResourceNotFoundException();
            }

            repo.deactivate(id);
            return null;
        });
    }
}
