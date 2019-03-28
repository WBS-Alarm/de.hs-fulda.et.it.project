package de.hsfulda.et.wbs.action.zielort.impl;

import de.hsfulda.et.wbs.action.zielort.CreateZielortAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.ZielortData;
import de.hsfulda.et.wbs.core.data.ZielortDto;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.entity.Zielort;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import de.hsfulda.et.wbs.repository.ZielortRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class CreateZielortActionImpl implements CreateZielortAction {

    private final ZielortRepository repo;
    private final TraegerRepository traegerRepository;
    private final AccessService accessService;

    public CreateZielortActionImpl(ZielortRepository repo, TraegerRepository traegerRepository, AccessService accessService) {
        this.repo = repo;
        this.traegerRepository = traegerRepository;
        this.accessService = accessService;
    }

    @Override
    public ZielortData perform(WbsUser user, Long traegerId, ZielortDto zielort) {
        return accessService.hasAccessOnTraeger(user, traegerId, () -> {

            if (isEmpty(zielort.getName())) {
                throw new IllegalArgumentException("Name des Zielorts muss angegeben werden.");
            }

            Optional<Traeger> traeger = traegerRepository.findById(traegerId);

            Zielort saved =
                    Zielort.builder()
                            .name(zielort.getName())
                            .aktiv(true)
                            .build();

            Traeger tr = traeger.get();
            tr.addZielort(saved);
            return repo.save(saved);
        });
    }
}
