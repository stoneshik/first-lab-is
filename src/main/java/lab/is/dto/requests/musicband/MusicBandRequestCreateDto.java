package lab.is.dto.requests.musicband;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lab.is.bd.entities.MusicGenre;
import lab.is.dto.requests.album.AlbumRequestCreateDto;
import lab.is.dto.requests.coordinates.CoordinatesRequestCreateDto;
import lab.is.dto.requests.studio.StudioRequestCreateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicBandRequestCreateDto {
    private String name;
    private CoordinatesRequestCreateDto coordinates;
    private Long coordinatesId;
    private LocalDateTime creationDate;
    private MusicGenre genre;
    private Long numberOfParticipants;
    private Long singlesCount;
    private String description;
    private AlbumRequestCreateDto bestAlbum;
    private Long bestAlbumId;
    private long albumsCount;
    private LocalDate establishmentDate;
    private StudioRequestCreateDto studio;
    private Long studioId;
}
