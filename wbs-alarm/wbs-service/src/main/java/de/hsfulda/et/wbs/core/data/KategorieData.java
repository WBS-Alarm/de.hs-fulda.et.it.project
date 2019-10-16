package de.hsfulda.et.wbs.core.data;

import de.hsfulda.et.wbs.core.dto.KategorieDto;

import java.util.List;

public interface KategorieData extends KategorieDto {

    Long getId();

    boolean isAktiv();

    TraegerData getTraeger();

    List<GroesseData> getGroessen();

}
