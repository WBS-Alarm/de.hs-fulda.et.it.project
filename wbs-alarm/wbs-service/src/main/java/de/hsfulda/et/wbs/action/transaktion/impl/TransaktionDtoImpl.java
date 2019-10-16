package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.core.dto.PositionDto;
import de.hsfulda.et.wbs.core.dto.TransaktionDto;

import java.util.List;

class TransaktionDtoImpl implements TransaktionDto {

    private final Long warenkorbId;
    private final Long lagerId;
    private final List<PositionDto> positionDtos;

    private TransaktionDtoImpl(Long warenkorbId, Long lagerId, List<PositionDto> positionDtos) {
        this.warenkorbId = warenkorbId;
        this.lagerId = lagerId;
        this.positionDtos = positionDtos;
    }

    static TransaktionDto of(Long wareneingangId, Long lagerId, List<PositionDto> positionDtos) {
        return new TransaktionDtoImpl(wareneingangId, lagerId, positionDtos);
    }

    @Override
    public Long getVon() {
        return warenkorbId;
    }

    @Override
    public Long getNach() {
        return lagerId;
    }

    @Override
    public List<PositionDto> getPositions() {
        return positionDtos;
    }
}
