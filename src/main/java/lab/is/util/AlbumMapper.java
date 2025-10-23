package lab.is.util;

import lab.is.bd.entities.Album;
import lab.is.dto.requests.AlbumRequestDto;
import lab.is.dto.responses.AlbumResponseDto;

public class AlbumMapper {
    private AlbumMapper() {}

    public static Album toEntityFromDto(AlbumRequestDto dto) {
        return Album.builder()
            .name(dto.getName())
            .length(dto.getLength())
            .build();
    }

    public static AlbumResponseDto toDtoFromEntity(Album entity) {
        return AlbumResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .length(entity.getLength())
            .build();
    }
}
