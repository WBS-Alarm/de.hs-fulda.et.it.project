package de.hsfulda.et.wbs.action.zielort.impl;

import de.hsfulda.et.wbs.action.zielort.GetZielortAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.ZielortData;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.repository.ZielortRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetZielortActionImpl implements GetZielortAction {

    private final ZielortRepository repo;
    private final AccessService accessService;

    public GetZielortActionImpl(ZielortRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public ZielortData perform(WbsUser user, Long id) {
        return accessService.hasAccessOnZielort(user, id, () -> {
            Optional<ZielortData> managed = repo.findByIdAndAktivIsTrue(id);
            return managed.orElseThrow(() -> new ResourceNotFoundException("Zielort mit ID {0} nicht gefunden.", id));
        });
    }
}
