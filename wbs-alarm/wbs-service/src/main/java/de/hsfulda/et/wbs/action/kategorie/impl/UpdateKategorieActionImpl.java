package de.hsfulda.et.wbs.action.kategorie.impl;

import de.hsfulda.et.wbs.action.kategorie.UpdateKategorieAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.KategorieData;
import de.hsfulda.et.wbs.core.dto.KategorieDto;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.repository.KategorieRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static org.springframework.util.ObjectUtils.isEmpty;

@Transactional
@Component
public class UpdateKategorieActionImpl implements UpdateKategorieAction {

    private final KategorieRepository repo;
    private final AccessService accessService;

    public UpdateKategorieActionImpl(KategorieRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public KategorieData perform(WbsUser user, Long id, KategorieDto kategorie) {
        return accessService.hasAccessOnKategorie(user, id, () -> {
            if (isEmpty(kategorie.getName())) {
                throw new IllegalArgumentException("Name des Trägers muss angegeben werden.");
            }

            if (!repo.existsById(id)) {
                throw new ResourceNotFoundException("Kategorie mit ID {0} nicht gefunden.", id);
            }

            repo.updateName(id, kategorie.getName());
            return repo.findByIdAndAktivIsTrue(id)
                    .get();
        });
    }
}
