package de.hsfulda.et.wbs.action.traeger.impl;

import de.hsfulda.et.wbs.action.traeger.GetTraegerAction;
import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
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
