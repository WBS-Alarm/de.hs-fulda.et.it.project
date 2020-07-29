package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.*;
import de.hsfulda.et.wbs.core.dto.TransaktionDto;
import de.hsfulda.et.wbs.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public interface TransaktionDao {

    <T> T hasAccessOnTraeger(WbsUser user, Long traegerId, Supplier<T> supplier);

    <T> T hasAccessOnTransaktion(WbsUser user, TransaktionDto dto, Supplier<T> supplier);

    <T> T hasAccessOnTransaktion(WbsUser user, Long transaktionId, Supplier<T> supplier);

    Optional<TransaktionData> getTransaktionData(Long id);

    Page<TransaktionData> getTransaktionPageByTraegerId(Long traegerId, PageRequest pageable);

    TransaktionData saveTransaktion(Transaktion transaktion);

    Optional<Long> findWareneingangByTraegerId(Long tragerId);

    Optional<Long> findLagerByTraegerId(Long tragerId);

    Benutzer getBenutzer(WbsUser user);

    List<BenutzerData> getEinkaeufer(Long benutzerId);

    ZielortData getZielortData(Long id);

    Zielort getZielort(Long id);

    boolean existsGroesse(Long groesseId);

    GroesseData getGroesseData(Long id);

    Groesse getGroesse(Long id);

    boolean existsBestand(Long id);

    BestandData getBestandData(Long bestandId);

    BestandData getBestandData(Long zielortId, Long groesseId);

    Optional<Bestand> getBestand(Long zielortId, Long groesseId);

    Bestand getBestand(Long bestandId);

    BestandData saveBestand(Bestand saved);

    void updateBestandAnzahl(Long bestandId, Long anzahl);

}
