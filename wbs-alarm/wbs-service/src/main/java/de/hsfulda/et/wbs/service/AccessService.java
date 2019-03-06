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

    public HttpEntity<HalJsonResource> hasAccess(User user, Long benutzerId, Supplier<HttpEntity<HalJsonResource>> supplier) {
        Optional<Traeger> traeger = traegerRepository.findTraegerByUsernameAndBenutezrId(user.getUsername(), benutzerId);
        if (traeger.isPresent()) {
            return supplier.get();
        }
        return new ResponseEntity<>(NOT_FOUND);
    }
}
