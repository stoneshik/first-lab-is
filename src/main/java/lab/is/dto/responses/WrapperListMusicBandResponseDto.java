package lab.is.dto.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WrapperListMusicBandResponseDto {
    private Long totalElements;
    private Long totalPages;
    private Long currentPage;
    private Long pageSize;
    private List<MusicBandResponseDto> musicBands;
}
