package de.hsfulda.et.wbs.action.zielort.impl;

import de.hsfulda.et.wbs.action.zielort.LockZielortAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.ZielortData;
import de.hsfulda.et.wbs.repository.ZielortRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component
public class LockZielortActionImpl implements LockZielortAction {

    private final ZielortRepository repo;
    private final AccessService accessService;

    public LockZielortActionImpl(ZielortRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public ZielortData perform(WbsUser user, Long id) {
        return accessService.hasAccessOnZielort(user, id, () -> {

            repo.lock(id);
            return repo.findByIdAsData(id)
                    .get();

        });
    }
}
