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

import lab.is.dto.requests.CoordinatesRequestDto;
import lab.is.dto.responses.CoordinatesResponseDto;
import lab.is.services.CoordinatesService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/coordinates")
@RequiredArgsConstructor
public class CoordinatesController {
    private static final String URI_RESOURCE = "/api/v1/coordinates";
    private final CoordinatesService coordinatesService;

    @GetMapping("/{id}")
    public ResponseEntity<CoordinatesResponseDto> getById(@PathVariable Long id) {
        CoordinatesResponseDto dto = coordinatesService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CoordinatesRequestDto dto) {
        Long createdId = coordinatesService.create(dto);
        URI location = URI.create(URI_RESOURCE + "/" + createdId);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody CoordinatesRequestDto dto) {
        coordinatesService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        coordinatesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
