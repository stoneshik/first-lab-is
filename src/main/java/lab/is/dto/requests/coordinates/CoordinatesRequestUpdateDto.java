package lab.is.dto.requests.coordinates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoordinatesRequestUpdateDto {
    private Float x;
    private int y;
}
