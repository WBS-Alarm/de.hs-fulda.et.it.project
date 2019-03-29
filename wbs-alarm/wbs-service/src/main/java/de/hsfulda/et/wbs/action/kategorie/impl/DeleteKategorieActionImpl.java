package de.hsfulda.et.wbs.action.kategorie.impl;

import de.hsfulda.et.wbs.action.kategorie.DeleteKategorieAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.repository.KategorieRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component
public class DeleteKategorieActionImpl implements DeleteKategorieAction {

    private final KategorieRepository repo;
    private final AccessService accessService;

    public DeleteKategorieActionImpl(KategorieRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public void perform(WbsUser user, Long id) {
        accessService.hasAccessOnKategorie(user, id, () -> {
            if (!repo.existsById(id)) {
                throw new ResourceNotFoundException();
            }

            repo.deactivate(id);
            return null;
        });
    }
}
