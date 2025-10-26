package lab.is.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lab.is.dto.requests.musicband.MusicBandRequestCreateDto;
import lab.is.dto.requests.musicband.MusicBandRequestUpdateDto;
import lab.is.dto.responses.MusicBandResponseDto;
import lab.is.services.musicband.MusicBandService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/music-bands")
@RequiredArgsConstructor
public class MusicBandController {
    private static final String URI_RESOURCE = "/api/v1/music-bands";
    private final MusicBandService musicBandService;

    @GetMapping("/{id}")
    public ResponseEntity<MusicBandResponseDto> getById(@PathVariable Long id) {
        MusicBandResponseDto dto = musicBandService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid MusicBandRequestCreateDto dto) {
        Long createdId = musicBandService.create(dto).getId();
        URI location = URI.create(URI_RESOURCE + "/" + createdId);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
        @PathVariable Long id,
        @RequestBody @Valid MusicBandRequestUpdateDto dto
    ) {
        musicBandService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        musicBandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
