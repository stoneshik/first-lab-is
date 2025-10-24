package lab.is.dto.requests.musicband;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lab.is.bd.entities.MusicGenre;
import lab.is.dto.requests.album.AlbumRequestUpdateDto;
import lab.is.dto.requests.coordinates.CoordinatesRequestUpdateDto;
import lab.is.dto.requests.studio.StudioRequestUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicBandRequestUpdateDto {
    private String name;
    private CoordinatesRequestUpdateDto coordinates;
    private Long coordinatesId;
    private LocalDateTime creationDate;
    private MusicGenre genre;
    private Long numberOfParticipants;
    private Long singlesCount;
    private String description;
    private AlbumRequestUpdateDto bestAlbum;
    private Long bestAlbumId;
    private long albumsCount;
    private LocalDate establishmentDate;
    private StudioRequestUpdateDto studio;
    private Long studioId;
}
