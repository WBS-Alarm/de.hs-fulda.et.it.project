package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.action.transaktion.impl.TransaktionDao;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.*;
import de.hsfulda.et.wbs.core.dto.TransaktionDto;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.core.exception.TransaktionValidationException;
import de.hsfulda.et.wbs.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Transactional
@Component
public class TransaktionDaoImpl implements TransaktionDao {

    private final TransaktionRepository transaktionen;
    private final BenutzerRepository benutzer;
    private final GroesseRepository groessen;
    private final BestandRepository bestaende;
    private final ZielortRepository zielorte;
    private final AccessRepository access;

    public TransaktionDaoImpl(TransaktionRepository transaktionen, BenutzerRepository benutzer,
            GroesseRepository groessen, BestandRepository bestaende, ZielortRepository zielorte,
            AccessRepository access) {
        this.transaktionen = transaktionen;
        this.benutzer = benutzer;
        this.groessen = groessen;
        this.bestaende = bestaende;
        this.zielorte = zielorte;
        this.access = access;
    }

    @Override
    public <T> T hasAccessOnTraeger(WbsUser user, Long traegerId, Supplier<T> supplier) {
        Long counts = access.findTraegerByUserAndTraegerId(user.getId(), traegerId);
        return evaluteCount(counts, traegerId, "Träger", supplier);
    }

    @Override
    public <T> T hasAccessOnTransaktion(WbsUser user, TransaktionDto dto, Supplier<T> supplier) {
        checkCount(access.findTraegerByUserAndZielortId(user.getId(), dto.getVon()), dto.getVon(), "Zielort");
        checkCount(access.findTraegerByUserAndZielortId(user.getId(), dto.getNach()), dto.getNach(), "Zielort");

        dto.getPositions()
                .forEach(p -> {
                    checkCount(access.findTraegerByUserAndGroesseId(user.getId(), p.getGroesse()), p.getGroesse(),
                            "Größe");
                });

        return supplier.get();
    }

    @Override
    public <T> T hasAccessOnTransaktion(WbsUser user, Long transaktionId, Supplier<T> supplier) {
        Long counts = access.findTraegerByUserAndTransaktionId(user.getId(), transaktionId);
        return evaluteCount(counts, transaktionId, "Transaktion", supplier);
    }

    private void checkCount(final Long counts, final Long id, final String resource) {
        if (counts == 0) {
            throw new ResourceNotFoundException("{1} mit ID {0} nicht gefunden.", id, resource);
        }
    }

    private <T> T evaluteCount(final Long counts, final Long id, final String resource, final Supplier<T> supplier) {
        checkCount(counts, id, resource);
        return supplier.get();
    }

    @Override
    public Optional<TransaktionData> getTransaktionData(Long id) {
        return transaktionen.findByIdAsData(id);
    }

    @Override
    public Page<TransaktionData> getTransaktionPageByTraegerId(Long traegerId, PageRequest pageable) {
        return transaktionen.findAllAsDataByTraegerId(traegerId, pageable);
    }

    @Override
    public TransaktionData saveTransaktion(Transaktion transaktion) {
        return transaktionen.save(transaktion);
    }

    @Override
    public Optional<Long> findWareneingangByTraegerId(Long tragerId) {
        return zielorte.findWareneingangByTraegerId(tragerId);
    }

    @Override
    public Optional<Long> findLagerByTraegerId(Long tragerId) {
        return zielorte.findLagerByTraegerId(tragerId);
    }

    @Override
    public Benutzer getBenutzer(WbsUser user) {
        Optional<Benutzer> managed = benutzer.findById(user.getId());
        return managed.orElseThrow(
                () -> new ResourceNotFoundException("Benutzer mit ID {0} nicht gefunden.", user.getId()));
    }

    @Override
    public List<BenutzerData> getEinkaeufer(Long benutzerId) {
        return benutzer.findAllEinkaeuferByUserId(benutzerId);
    }

    @Override
    public ZielortData getZielortData(Long id) {
        Optional<ZielortData> managed = findZielortByIdAndAktivIsTrue(id);
        return managed.orElseThrow(() -> new ResourceNotFoundException("Zielort mit ID {0} nicht gefunden.", id));
    }

    @Override
    public Zielort getZielort(Long id) {
        Optional<Zielort> managed = zielorte.findById(id);
        return managed.orElseThrow(() -> new ResourceNotFoundException("Zielort mit ID {0} nicht gefunden.", id));
    }

    private Optional<ZielortData> findZielortByIdAndAktivIsTrue(Long id) {
        return zielorte.findByIdAndAktivIsTrue(id);
    }

    @Override
    public boolean existsGroesse(Long groesseId) {
        return groessen.existsById(groesseId);
    }

    @Override
    public GroesseData getGroesseData(Long id) {
        Optional<GroesseData> managed = findGroesseByIdAndAktivIsTrue(id);
        return managed.orElseThrow(() -> new ResourceNotFoundException("Groesse mit ID {0} nicht gefunden.", id));
    }

    @Override
    public Groesse getGroesse(Long id) {
        Optional<Groesse> managed = groessen.findById(id);
        return managed.orElseThrow(() -> new ResourceNotFoundException("Groesse mit ID {0} nicht gefunden.", id));
    }

    private Optional<GroesseData> findGroesseByIdAndAktivIsTrue(Long id) {
        return groessen.findByIdAndAktivIsTrue(id);
    }

    @Override
    public boolean existsBestand(Long id) {
        return bestaende.existsById(id);
    }

    @Override
    public BestandData getBestandData(Long bestandId) {
        Optional<BestandData> managed = bestaende.findByIdAsData(bestandId);
        return managed.orElseThrow(
                () -> new ResourceNotFoundException("Bestand mit ID {0} nicht gefunden.", bestandId));
    }

    @Override
    public BestandData getBestandData(Long zielortId, Long groesseId) {
        Optional<BestandData> managed = findBestandByZielortIdAndGroesseId(zielortId, groesseId);
        return managed.orElseThrow(
                () -> new TransaktionValidationException("Für den Zielort {0} wurde kein Bestand gefunden (Größe {1}).",
                        zielortId, groesseId));
    }

    @Override
    public Optional<Bestand> getBestand(Long zielortId, Long groesseId) {
        return bestaende.findByZielortIdAndGroesseId(zielortId, groesseId);
    }

    @Override
    public Bestand getBestand(Long bestandId) {
        Optional<Bestand> managed = bestaende.findById(bestandId);
        return managed.orElseThrow(
                () -> new ResourceNotFoundException("Bestand mit ID {0} nicht gefunden.", bestandId));
    }

    private Optional<BestandData> findBestandByZielortIdAndGroesseId(Long zielortId, Long groesseId) {
        return bestaende.findByZielortIdAndGroesseIdAsData(zielortId, groesseId);
    }

    @Override
    public BestandData saveBestand(Bestand bestand) {
        return bestaende.save(bestand);
    }

    @Override
    public void updateBestandAnzahl(Long bestandId, Long anzahl) {
        bestaende.updateAnzahl(bestandId, anzahl);
    }
}
