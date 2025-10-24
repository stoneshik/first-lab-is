package lab.is.util;

import lab.is.bd.entities.Coordinates;
import lab.is.dto.requests.coordinates.CoordinatesRequestCreateDto;
import lab.is.dto.requests.coordinates.CoordinatesRequestUpdateDto;
import lab.is.dto.responses.CoordinatesResponseDto;

public class CoordinatesMapper {
    private CoordinatesMapper() {}

    public static Coordinates toEntityFromDto(CoordinatesRequestCreateDto dto) {
        return Coordinates.builder()
            .x(dto.getX())
            .y(dto.getY())
            .build();
    }

    public static Coordinates toEntityFromDto(CoordinatesRequestUpdateDto dto) {
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
