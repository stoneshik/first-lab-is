package lab.is.services.musicband;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.is.bd.entities.MusicBand;
import lab.is.dto.requests.musicband.MusicBandRequestCreateDto;
import lab.is.dto.requests.musicband.MusicBandRequestUpdateDto;
import lab.is.dto.responses.MusicBandResponseDto;
import lab.is.repositories.MusicBandRepository;
import lab.is.util.musicband.MusicBandToDtoFromEntityMapper;
import lab.is.util.musicband.MusicBandToEntityFromDtoCreateRequest;
import lab.is.util.musicband.MusicBandToEntityFromDtoUpdateRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MusicBandService {
    private final MusicBandRepository musicBandRepository;
    private final MusicBandTxService musicBandTxService;
    private final MusicBandToEntityFromDtoCreateRequest musicBandToEntityFromDtoCreateRequest;
    private final MusicBandToEntityFromDtoUpdateRequest musicBandToEntityFromDtoUpdateRequest;

    @Transactional(readOnly = true)
    public MusicBandResponseDto findById(Long id) {
        MusicBand musicBand = musicBandTxService.findByIdReturnsEntity(id);
        return MusicBandToDtoFromEntityMapper.toDtoFromEntity(musicBand);
    }

    @Transactional
    public MusicBand create(MusicBandRequestCreateDto dto) {
        MusicBand musicBand = musicBandToEntityFromDtoCreateRequest.toEntityFromDto(dto);
        MusicBand savedMusicBand = musicBandRepository.save(musicBand);
        musicBandRepository.flush();
        return savedMusicBand;
    }

    @Transactional
    public MusicBand update(long id, MusicBandRequestUpdateDto dto) {
        MusicBand musicBand = musicBandTxService.findByIdReturnsEntity(id);
        musicBand = musicBandToEntityFromDtoUpdateRequest.toEntityFromDto(
            dto,
            musicBand
        );
        MusicBand savedMusicBand = musicBandRepository.save(musicBand);
        musicBandRepository.flush();
        return savedMusicBand;
    }

    @Transactional
    public void delete(Long id) {
        MusicBand musicBand = musicBandTxService.findByIdReturnsEntity(id);
        musicBandRepository.delete(musicBand);
        musicBandRepository.flush();
    }

    public MusicBand findByIdReturnsEntity(Long id) {
        return musicBandTxService.findByIdReturnsEntity(id);
    }
}
