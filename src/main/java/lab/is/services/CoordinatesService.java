package lab.is.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.is.bd.entities.Coordinates;
import lab.is.dto.requests.coordinates.CoordinatesRequestCreateDto;
import lab.is.dto.requests.coordinates.CoordinatesRequestUpdateDto;
import lab.is.dto.responses.CoordinatesResponseDto;
import lab.is.exceptions.NestedObjectIsUsedException;
import lab.is.exceptions.NestedObjectNotFoundException;
import lab.is.repositories.CoordinatesRepository;
import lab.is.util.CoordinatesMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoordinatesService {
    private final CoordinatesRepository coordinatesRepository;

    @Transactional(readOnly = true)
    public CoordinatesResponseDto findById(Long id) {
        Coordinates coordinates = findByIdReturnsEntity(id);
        return CoordinatesMapper.toDtoFromEntity(coordinates);
    }

    @Transactional
    public Coordinates create(CoordinatesRequestCreateDto dto) {
        Coordinates coordinates = CoordinatesMapper.toEntityFromDto(dto);
        Coordinates savedCoordinates = coordinatesRepository.save(coordinates);
        coordinatesRepository.flush();
        return savedCoordinates;
    }

    @Transactional
    public Coordinates update(long id, CoordinatesRequestUpdateDto dto) {
        Coordinates coordinates = findByIdReturnsEntity(id);
        Coordinates updatedCoordinates = coordinates.toBuilder()
            .x(dto.getX())
            .y(dto.getY())
            .build();
        Coordinates savedCoordinates = coordinatesRepository.save(updatedCoordinates);
        coordinatesRepository.flush();
        return savedCoordinates;
    }

    @Transactional
    public void delete(Long id) {
        Coordinates coordinates = findByIdReturnsEntity(id);
        if (isUsedNestedObject(coordinates)) {
            throw new NestedObjectIsUsedException(
                String.format(
                    "Координаты с id: %s не могут быть удалены, так как связаны с другими объектами",
                    id
                )
            );
        }
        coordinatesRepository.delete(coordinates);
    }

    private Coordinates findByIdReturnsEntity(Long id) {
        return coordinatesRepository.findById(id)
                .orElseThrow(
                    () ->
                    new NestedObjectNotFoundException(
                        String.format("Координаты с id: %s не найдены", id)
                    )
                );
    }

    private boolean isUsedNestedObject(Coordinates coordinates) {
        return !coordinates.getMusicBands().isEmpty();
    }
}
