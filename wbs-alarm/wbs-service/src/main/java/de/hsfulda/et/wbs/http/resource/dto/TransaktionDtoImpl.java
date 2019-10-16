package de.hsfulda.et.wbs.http.resource.dto;

import de.hsfulda.et.wbs.core.dto.PositionDto;
import de.hsfulda.et.wbs.core.dto.TransaktionDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransaktionDtoImpl implements TransaktionDto {

    private Long von;
    private Long nach;
    private List<PositionDtoImpl> positions;

    @Override
    public Long getVon() {
        return von;
    }

    public void setVon(Long von) {
        this.von = von;
    }

    @Override
    public Long getNach() {
        return nach;
    }

    public void setNach(Long nach) {
        this.nach = nach;
    }

    @Override
    public List<PositionDto> getPositions() {
        if (positions == null) {
            positions = new ArrayList<>();
        }
        return positions.stream()
                .map(p -> (PositionDto) p)
                .collect(Collectors.toList());
    }

    public void setPositions(List<PositionDtoImpl> positions) {
        this.positions = positions;
    }
}
