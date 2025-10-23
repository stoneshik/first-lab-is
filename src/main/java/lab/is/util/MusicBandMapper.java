package lab.is.util;

import org.springframework.stereotype.Service;

import lab.is.bd.entities.Album;
import lab.is.bd.entities.Coordinates;
import lab.is.bd.entities.MusicBand;
import lab.is.bd.entities.Studio;
import lab.is.dto.requests.AlbumRequestDto;
import lab.is.dto.requests.CoordinatesRequestDto;
import lab.is.dto.requests.MusicBandRequestDto;
import lab.is.dto.requests.StudioRequestDto;
import lab.is.dto.responses.MusicBandResponseDto;
import lab.is.exceptions.IncorrectDtoInRequestException;
import lab.is.services.AlbumService;
import lab.is.services.CoordinatesService;
import lab.is.services.StudioService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MusicBandMapper {
    private final AlbumService albumService;
    private final CoordinatesService coordinatesService;
    private final StudioService studioService;

    public MusicBand toEntityFromDto(MusicBandRequestDto dto) {
        if (isCombinationInfoAboutNestedObjectsDtoIncorrect(
            dto.getCoordinates(), dto.getCoordinatesId(),
            dto.getBestAlbum(), dto.getBestAlbumId(),
            dto.getStudio(), dto.getStudioId()
        )) {
            throw new IncorrectDtoInRequestException("Ошибка в комбинации в информации о вложенных объектов");
        }
        Coordinates coordinates = extractCoordinatesEntityFromMusicBandDto(
            dto.getCoordinates(),
            dto.getCoordinatesId()
        );
        Album bestAlbum = extractBestAlbumEntityFromMusicBandDto(
            dto.getBestAlbum(),
            dto.getBestAlbumId()
        );
        Studio studio = extractStudioEntityFromMusicBandDto(
            dto.getStudio(),
            dto.getStudioId()
        );
        return MusicBand.builder()
            .name(dto.getName())
            .coordinates(coordinates)
            .creationDate(dto.getCreationDate())
            .genre(dto.getGenre())
            .numberOfParticipants(dto.getNumberOfParticipants())
            .singlesCount(dto.getSinglesCount())
            .description(dto.getDescription())
            .bestAlbum(bestAlbum)
            .albumsCount(dto.getAlbumsCount())
            .establishmentDate(dto.getEstablishmentDate())
            .studio(studio)
            .build();
    }

    private boolean isCombinationInfoAboutNestedObjectsDtoIncorrect(
        CoordinatesRequestDto coordinates, Long coordinatesId,
        AlbumRequestDto bestAlbum, Long bestAlbumId,
        StudioRequestDto studio, Long studioId
    ) {
        return (
            (coordinates == null && coordinatesId == null) ||
            (coordinates != null && coordinatesId != null) ||
            (bestAlbum != null && bestAlbumId != null) ||
            (studio != null && studioId != null)
        );
    }

    private Coordinates extractCoordinatesEntityFromMusicBandDto(
        CoordinatesRequestDto dto,
        Long id
    ) {
        if (dto != null) {
            return CoordinatesMapper.toEntityFromDto(dto);
        }
        return coordinatesService.findById(id);
    }

    private Album extractBestAlbumEntityFromMusicBandDto(
        AlbumRequestDto dto,
        Long id
    ) {
        if (dto == null && id == null) return null;
        if (dto != null) {
            return AlbumMapper.toEntityFromDto(dto);
        }
        return albumService.findById(id);
    }

    private Studio extractStudioEntityFromMusicBandDto(
        StudioRequestDto dto,
        Long id
    ) {
        if (dto == null && id == null) return null;
        if (dto != null) {
            return StudioMapper.toEntityFromDto(dto);
        }
        return studioService.findById(id);
    }

    public MusicBandResponseDto toDtoFromEntity(MusicBand entity) {
        return MusicBandResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .address(entity.getAddress())
            .build();
    }
}
