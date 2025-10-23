package lab.is.util;

import lab.is.bd.entities.Coordinates;
import lab.is.dto.requests.CoordinatesRequestDto;
import lab.is.dto.responses.CoordinatesResponseDto;

public class CoordinatesMapper {
    private CoordinatesMapper() {}

    public static Coordinates toEntityFromDto(CoordinatesRequestDto dto) {
        return Coordinates.builder()
            .x(dto.getX())
            .y(dto.getY())
            .build();
    }

    public static CoordinatesResponseDto toDtoFromEntity(Coordinates entity) {
        return CoordinatesResponseDto.builder()
            .id(entity.getId())
            .x(entity.getX())
            .y(entity.getY())
            .build();
    }
}
