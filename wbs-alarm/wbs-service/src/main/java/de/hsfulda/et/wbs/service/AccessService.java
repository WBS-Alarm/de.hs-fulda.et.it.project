package de.hsfulda.et.wbs.service;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.entity.Traeger;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import de.hsfulda.et.wbs.security.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AccessService {

    private final TraegerRepository traegerRepository;

    public AccessService(TraegerRepository traegerRepository) {
        this.traegerRepository = traegerRepository;
    }

    public HttpEntity<HalJsonResource> hasAccessOnBenutzer(User user, Long benutzerId, Supplier<HttpEntity<HalJsonResource>> supplier) {
        Optional<Traeger> traeger = traegerRepository.findTraegerByUsernameAndBenutzerId(user.getUsername(), benutzerId);
        return getHalJsonResourceHttpEntity(supplier, traeger);
    }

    public HttpEntity<HalJsonResource> hasAccessOnZielort(User user, Long zielortId, Supplier<HttpEntity<HalJsonResource>> supplier) {
        Optional<Traeger> traeger = traegerRepository.findTraegerByUsernameAndZielortId(user.getUsername(), zielortId);
        return getHalJsonResourceHttpEntity(supplier, traeger);
    }

    public HttpEntity<HalJsonResource> hasAccessOnTraeger(User user, Long traegerId, Supplier<HttpEntity<HalJsonResource>> supplier) {
        Optional<Traeger> traeger = traegerRepository.findTraegerByUsernameAndTraegerId(user.getUsername(), traegerId);
        return getHalJsonResourceHttpEntity(supplier, traeger);
    }

    private HttpEntity<HalJsonResource> getHalJsonResourceHttpEntity(
        Supplier<HttpEntity<HalJsonResource>> supplier,
        Optional<Traeger> traeger) {

        if (traeger.isPresent()) {
            return supplier.get();
        }
        return new ResponseEntity<>(NOT_FOUND);
    }
}
