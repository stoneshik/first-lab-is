package lab.is.util;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.is.bd.entities.Album;
import lab.is.bd.entities.Coordinates;
import lab.is.bd.entities.MusicBand;
import lab.is.bd.entities.Studio;
import lab.is.dto.requests.album.AlbumRequestCreateDto;
import lab.is.dto.requests.coordinates.CoordinatesRequestCreateDto;
import lab.is.dto.requests.musicband.MusicBandRequestCreateDto;
import lab.is.dto.requests.studio.StudioRequestCreateDto;
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

    @Transactional(readOnly = true)
    public MusicBand toEntityFromDto(MusicBandRequestCreateDto dto) {
        if (isCombinationInfoAboutNestedObjectsDtoIncorrect(
            dto.getCoordinates(),
            dto.getBestAlbum(),
            dto.getStudio(),
            dto.getCoordinatesId(),
            dto.getBestAlbumId(),
            dto.getStudioId()
        )) {
            throw new IncorrectDtoInRequestException("Ошибка в комбинации в информации о вложенных объектов");
        }
        Coordinates coordinates = extractAndCreateCoordinatesEntityFromMusicBandDto(
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

    public MusicBandResponseDto toDtoFromEntity(MusicBand entity) {
        return MusicBandResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .coordinates(
                CoordinatesMapper.toDtoFromEntity(
                    entity.getCoordinates()
                )
            )
            .creationDate(entity.getCreationDate())
            .genre(entity.getGenre())
            .numberOfParticipants(entity.getNumberOfParticipants())
            .singlesCount(entity.getSinglesCount())
            .description(entity.getDescription())
            .bestAlbum(
                (entity.getBestAlbum() == null) ? null :
                    AlbumMapper.toDtoFromEntity(entity.getBestAlbum())
            )
            .albumsCount(entity.getAlbumsCount())
            .establishmentDate(entity.getEstablishmentDate())
            .studio(
                (entity.getStudio() == null) ? null :
                    StudioMapper.toDtoFromEntity(entity.getStudio())
            )
            .build();
    }

    private boolean isCombinationInfoAboutNestedObjectsDtoIncorrect(
        CoordinatesRequestCreateDto coordinates,
        AlbumRequestCreateDto bestAlbum,
        StudioRequestCreateDto studio,
        Long coordinatesId,
        Long bestAlbumId,
        Long studioId
    ) {
        return (
            (coordinates == null && coordinatesId == null) ||
            (coordinates != null && coordinatesId != null) ||
            (bestAlbum != null && bestAlbumId != null) ||
            (studio != null && studioId != null)
        );
    }

    private Coordinates extractAndCreateCoordinatesEntityFromMusicBandDto(
        CoordinatesRequestCreateDto dto,
        Long id
    ) {
        if (dto != null) {
            return coordinatesService.create(dto);
        }
        return coordinatesService.findById(id);
    }

    private Album extractBestAlbumEntityFromMusicBandDto(
        AlbumRequestCreateDto dto,
        Long id
    ) {
        if (dto == null && id == null) return null;
        if (dto != null) {
            return albumService.create(dto);
        }
        return albumService.findById(id);
    }

    private Studio extractStudioEntityFromMusicBandDto(
        StudioRequestCreateDto dto,
        Long id
    ) {
        if (dto == null && id == null) return null;
        if (dto != null) {
            return studioService.create(dto);
        }
        return studioService.findById(id);
    }
}
