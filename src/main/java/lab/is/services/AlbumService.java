package lab.is.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.is.bd.entities.Album;
import lab.is.dto.requests.album.AlbumRequestCreateDto;
import lab.is.dto.requests.album.AlbumRequestUpdateDto;
import lab.is.dto.responses.AlbumResponseDto;
import lab.is.exceptions.NestedObjectIsUsedException;
import lab.is.exceptions.NestedObjectNotFoundException;
import lab.is.repositories.AlbumRepository;
import lab.is.util.AlbumMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;

    @Transactional(readOnly = true)
    public AlbumResponseDto findById(Long id) {
        Album album = findByIdReturnsEntity(id);
        return AlbumMapper.toDtoFromEntity(album);
    }

    @Transactional
    public Album create(AlbumRequestCreateDto dto) {
        Album album = AlbumMapper.toEntityFromDto(dto);
        Album savedAlbum = albumRepository.save(album);
        albumRepository.flush();
        return savedAlbum;
    }

    @Transactional
    public Album update(long id, AlbumRequestUpdateDto dto) {
        Album album = findByIdReturnsEntity(id);
        Album updatedAlbum = album.toBuilder()
            .name(dto.getName())
            .length(dto.getLength())
            .build();
        Album savedAlbum = albumRepository.save(updatedAlbum);
        albumRepository.flush();
        return savedAlbum;
    }

    @Transactional
    public void delete(Long id) {
        Album album = findByIdReturnsEntity(id);
        if (isUsedNestedObject(album)) {
            throw new NestedObjectIsUsedException(
                String.format(
                    "Альбом с id: %s не может удален, так как связан с другими объектами",
                    id
                )
            );
        }
        albumRepository.delete(album);
    }

    private Album findByIdReturnsEntity(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(
                    () ->
                    new NestedObjectNotFoundException(
                        String.format("Альбом с id: %s не найден", id)
                    )
                );
    }

    private boolean isUsedNestedObject(Album album) {
        return !album.getMusicBands().isEmpty();
    }
}
