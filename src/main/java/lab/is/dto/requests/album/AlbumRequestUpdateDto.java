package lab.is.dto.requests.album;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumRequestUpdateDto {
    @NotBlank(message = "{not-blank}")
    private String name;

    @Positive(message = "{positive}")
    private int length;
}
