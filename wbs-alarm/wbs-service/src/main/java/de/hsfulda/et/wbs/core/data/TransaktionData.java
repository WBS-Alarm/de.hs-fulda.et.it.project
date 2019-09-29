package de.hsfulda.et.wbs.core.data;

import java.time.LocalDateTime;
import java.util.List;

public interface TransaktionData {

    Long getId();

    LocalDateTime getDatum();

    BenutzerData getBenutzer();

    ZielortData getVon();

    ZielortData getNach();

    List<PositionData> getPositionen();
}
