package lab.is.util.musicband;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.is.bd.entities.Album;
import lab.is.bd.entities.Coordinates;
import lab.is.bd.entities.MusicBand;
import lab.is.bd.entities.Studio;
import lab.is.dto.requests.album.AlbumRequestUpdateDto;
import lab.is.dto.requests.coordinates.CoordinatesRequestUpdateDto;
import lab.is.dto.requests.musicband.MusicBandRequestUpdateDto;
import lab.is.dto.requests.studio.StudioRequestUpdateDto;
import lab.is.exceptions.IncorrectDtoInRequestException;
import lab.is.services.album.AlbumService;
import lab.is.services.coordinates.CoordinatesService;
import lab.is.services.studio.StudioService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MusicBandToEntityFromDtoUpdateRequest {
    private final AlbumService albumService;
    private final CoordinatesService coordinatesService;
    private final StudioService studioService;

    @Transactional
    public MusicBand toEntityFromDto(
        MusicBandRequestUpdateDto dto,
        MusicBand foundedMusicBand
    ) {
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
        Coordinates coordinates = findOrCreateCoordinatesEntityByMusicBandDto(
            dto.getCoordinates(),
            dto.getCoordinatesId(),
            foundedMusicBand
        );
        Album bestAlbum = findOrCreateBestAlbumEntityByMusicBandDto(
            dto.getBestAlbum(),
            dto.getBestAlbumId(),
            foundedMusicBand
        );
        Studio studio = findOrCreateStudioEntityByMusicBandDto(
            dto.getStudio(),
            dto.getStudioId(),
            foundedMusicBand
        );
        return foundedMusicBand.toBuilder()
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
        CoordinatesRequestUpdateDto coordinates,
        AlbumRequestUpdateDto bestAlbum,
        StudioRequestUpdateDto studio,
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

    private Coordinates findOrCreateCoordinatesEntityByMusicBandDto(
        CoordinatesRequestUpdateDto dto,
        Long id,
        MusicBand foundedMusicBand
    ) {
        if (dto != null) {
            Coordinates coordinates = coordinatesService.create(
                dto.getX(),
                dto.getY()
            );
            coordinates.removeMusicBand(foundedMusicBand);
            return coordinates;
        }
        Coordinates coordinates = coordinatesService.findByIdReturnsEntity(id);
        Long foundedMusicBandCoordinatesId = foundedMusicBand.getCoordinates().getId();
        if (!foundedMusicBandCoordinatesId.equals(coordinates.getId())) {
            coordinates.removeMusicBand(foundedMusicBand);
        }
        return coordinates;
    }

    private Album findOrCreateBestAlbumEntityByMusicBandDto(
        AlbumRequestUpdateDto dto,
        Long id,
        MusicBand foundedMusicBand
    ) {
        if (dto == null && id == null) return null;
        if (dto != null) {
            Album album = albumService.create(
                dto.getName(),
                dto.getLength()
            );
            album.removeMusicBand(foundedMusicBand);
            return album;
        }
        Album album = albumService.findByIdReturnsEntity(id);
        Long foundedMusicBandBestAlbumId = foundedMusicBand.getBestAlbum().getId();
        if (!foundedMusicBandBestAlbumId.equals(album.getId())) {
            album.removeMusicBand(foundedMusicBand);
        }
        return album;
    }

    private Studio findOrCreateStudioEntityByMusicBandDto(
        StudioRequestUpdateDto dto,
        Long id,
        MusicBand foundedMusicBand
    ) {
        if (dto == null && id == null) return null;
        if (dto != null) {
            Studio studio = studioService.create(
                dto.getName(),
                dto.getAddress()
            );
            studio.removeMusicBand(foundedMusicBand);
            return studio;
        }
        Studio studio = studioService.findByIdReturnsEntity(id);
        Long foundedMusicBandStudioId = foundedMusicBand.getStudio().getId();
        if (!foundedMusicBandStudioId.equals(studio.getId())) {
            studio.removeMusicBand(foundedMusicBand);
        }
        return studio;
    }
}
