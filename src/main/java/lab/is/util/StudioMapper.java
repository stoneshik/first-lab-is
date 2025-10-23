package lab.is.util;

import lab.is.bd.entities.Studio;
import lab.is.dto.requests.StudioRequestDto;
import lab.is.dto.responses.StudioResponseDto;

public class StudioMapper {
    private StudioMapper() {}

    public static Studio toEntityFromDto(StudioRequestDto dto) {
        return Studio.builder()
            .name(dto.getName())
            .address(dto.getAddress())
            .build();
    }

    public static StudioResponseDto toDtoFromEntity(Studio entity) {
        return StudioResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .address(entity.getAddress())
            .build();
    }
}
