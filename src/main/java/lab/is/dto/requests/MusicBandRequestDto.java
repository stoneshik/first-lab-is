package lab.is.dto.requests;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lab.is.bd.entities.MusicGenre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicBandRequestDto {
    private String name;
    private CoordinatesRequestDto coordinates;
    private Long coordinatesId;
    private LocalDateTime creationDate;
    private MusicGenre genre;
    private Long numberOfParticipants;
    private Long singlesCount;
    private String description;
    private AlbumRequestDto bestAlbum;
    private Long bestAlbumId;
    private long albumsCount;
    private LocalDate establishmentDate;
    private StudioRequestDto studio;
    private Long studioId;
}
