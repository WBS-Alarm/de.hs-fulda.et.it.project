package de.hsfulda.et.wbs.action.kategorie.impl;

import de.hsfulda.et.wbs.action.kategorie.GetKategorieAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.KategorieData;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.repository.KategorieRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetKategorieActionImpl implements GetKategorieAction {

    private final KategorieRepository repo;
    private final AccessService accessService;

    public GetKategorieActionImpl(KategorieRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public KategorieData perform(WbsUser user, Long id) {
        return accessService.hasAccessOnKategorie(user, id, () -> {
            Optional<KategorieData> managed = repo.findByIdAndAktivIsTrue(id);
            return managed.orElseThrow(() -> new ResourceNotFoundException("Kategorie mit ID {0} nicht gefunden.", id));
        });
    }
}
