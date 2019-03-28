package de.hsfulda.et.wbs.action.impl;

import de.hsfulda.et.wbs.action.GetTraegerAction;
import de.hsfulda.et.wbs.core.ResourceNotFoundException;
import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetTraegerActionImpl implements GetTraegerAction {

    private final TraegerRepository repo;

    public GetTraegerActionImpl(TraegerRepository repo) {
        this.repo = repo;
    }

    @Override
    public TraegerData perform(Long id) {
        Optional<TraegerData> managed = repo.findByIdAsData(id);
        return managed.orElseThrow(ResourceNotFoundException::new);
    }
}
