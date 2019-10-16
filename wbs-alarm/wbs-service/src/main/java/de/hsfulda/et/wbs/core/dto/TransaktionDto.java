package de.hsfulda.et.wbs.core.dto;

import java.util.List;

public interface TransaktionDto {

    Long getVon();

    Long getNach();

    List<PositionDto> getPositions();
}
