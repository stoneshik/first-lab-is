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

import lab.is.dto.requests.AlbumRequestDto;
import lab.is.dto.responses.AlbumResponseDto;
import lab.is.services.AlbumService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/albums")
@RequiredArgsConstructor
public class AlbumController {
    private static final String URI_RESOURCE = "/api/v1/albums";
    private final AlbumService albumService;

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponseDto> getById(@PathVariable Long id) {
        AlbumResponseDto dto = albumService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AlbumRequestDto dto) {
        Long createdId = albumService.create(dto);
        URI location = URI.create(URI_RESOURCE + "/" + createdId);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AlbumRequestDto dto) {
        albumService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        albumService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
