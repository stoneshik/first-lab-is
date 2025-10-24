package lab.is.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.is.bd.entities.Coordinates;
import lab.is.bd.entities.Studio;
import lab.is.dto.requests.studio.StudioRequestCreateDto;
import lab.is.dto.requests.studio.StudioRequestUpdateDto;
import lab.is.dto.responses.StudioResponseDto;
import lab.is.exceptions.NestedObjectIsUsedException;
import lab.is.exceptions.NestedObjectNotFoundException;
import lab.is.repositories.StudioRepository;
import lab.is.util.StudioMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudioService {
    private final StudioRepository studioRepository;

    @Transactional(readOnly = true)
    public StudioResponseDto findById(Long id) {
        Studio studio = findByIdReturnsEntity(id);
        return StudioMapper.toDtoFromEntity(studio);
    }

    @Transactional
    public Studio create(StudioRequestCreateDto dto) {
        Studio studio = StudioMapper.toEntityFromDto(dto);
        Studio savedStudio = studioRepository.save(studio);
        studioRepository.flush();
        return savedStudio;
    }

    @Transactional
    public Studio update(long id, StudioRequestUpdateDto dto) {
        Studio studio = findByIdReturnsEntity(id);
        Studio updatedCoordinates = studio.toBuilder()
            .name(dto.getName())
            .address(dto.getAddress())
            .build();
        Studio savedStudio = studioRepository.save(updatedCoordinates);
        studioRepository.flush();
        return savedStudio;
    }

    @Transactional
    public void delete(Long id) {
        Studio studio = findByIdReturnsEntity(id);
        if (isUsedNestedObject(studio)) {
            throw new NestedObjectIsUsedException(
                String.format(
                    "Студия с id: %s не может быть удалена, так как связана с другими объектами",
                    id
                )
            );
        }
        studioRepository.delete(studio);
    }

    private Studio findByIdReturnsEntity(Long id) {
        return studioRepository.findById(id)
                .orElseThrow(
                    () ->
                    new NestedObjectNotFoundException(
                        String.format("Студия с id: %s не найдена", id)
                    )
                );
    }

    private boolean isUsedNestedObject(Studio studio) {
        return !studio.getMusicBands().isEmpty();
    }
}
