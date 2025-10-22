package lab.is.dto.responses;

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
public class MusicBandResponseDto {
    private Long id;
    private String name;
    private CoordinatesResponseDto coordinates;
    private LocalDateTime creationDate;
    private MusicGenre genre;
    private Long numberOfParticipants;
    private Long singlesCount;
    private String description;
    private AlbumResponseDto bestAlbum;
    private long albumsCount;
    private LocalDate establishmentDate;
    private StudioResponseDto studio;
}
