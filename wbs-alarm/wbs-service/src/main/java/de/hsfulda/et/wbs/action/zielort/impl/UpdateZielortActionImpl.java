package de.hsfulda.et.wbs.action.zielort.impl;

import de.hsfulda.et.wbs.action.zielort.UpdateZielortAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.ZielortData;
import de.hsfulda.et.wbs.core.data.ZielortDto;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.repository.ZielortRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;

@Transactional
@Component
public class UpdateZielortActionImpl implements UpdateZielortAction {

    private final ZielortRepository repo;
    private final AccessService accessService;

    public UpdateZielortActionImpl(ZielortRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public ZielortData perform(WbsUser user, Long id, ZielortDto zielort) {
        return accessService.hasAccessOnZielort(user, id, () -> {
            if (isEmpty(zielort.getName())) {
                throw new IllegalArgumentException("Name des Zielorts muss angegeben werden.");
            }

            Optional<ZielortData> managed = repo.findByIdAsData(id);
            if (!repo.existsById(id)) {
                throw new ResourceNotFoundException();
            }

            if (repo.isAutomated(id)) {
                throw new IllegalArgumentException("Zielort kann nicht geändert werden, da es sich um eine automatisierten Zielort handelt.");
            }

            repo.updateName(id, zielort.getName());
            return repo.findByIdAsData(id).get();
        });
    }
}
