package lab.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import lab.is.bd.entities.Album;
import lab.is.bd.entities.Coordinates;
import lab.is.bd.entities.MusicBand;
import lab.is.bd.entities.MusicGenre;
import lab.is.bd.entities.Studio;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class MusicBandControllerCrudTest extends AbstractMusicBandTest {
    @Autowired
    MockMvc mockMvc;

    protected String getEndpointGettingEntityById() {
        return "/api/v1/music-bands/{id}";
    }

    @Test
    void createMusicBand_ReturnsResponseWithStatusCreated() throws Exception {
        setupDb();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1/music-bands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": "created music band",
                    "coordinates": {
                        "x": 123456.0,
                        "y": 2147483647
                    },
                    "coordinatesId": null,
                    "genre": "BRIT_POP",
                    "numberOfParticipants": 9223372036854775807,
                    "singlesCount": 9223372036854775807,
                    "description": "",
                    "bestAlbum": {
                        "name": "new album",
                        "length": 2147483647
                    },
                    "bestAlbumId": null,
                    "albumsCount": 9223372036854775807,
                    "establishmentDate": "2021-01-01",
                    "studio": {
                        "name": "",
                        "address": ""
                    },
                    "studioId": null
                }
                """
            );

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isCreated()
            );

        checkEntityExistByIdAndEqualExpectedMusicBandEntity(
            mockMvc,
            MusicBand.builder()
                .id(3L)
                .name("created music band")
                .coordinates(
                    Coordinates.builder()
                        .id(4L)
                        .x(123456.0f)
                        .y(2147483647)
                        .build()
                )
                .genre(MusicGenre.BRIT_POP)
                .numberOfParticipants(9223372036854775807L)
                .singlesCount(9223372036854775807L)
                .description("")
                .bestAlbum(
                    Album.builder()
                        .id(4L)
                        .name("new album")
                        .length(2147483647)
                        .build()
                )
                .albumsCount(9223372036854775807L)
                .establishmentDate(LocalDate.of(2021, 1, 1))
                .studio(
                    Studio.builder()
                        .id(3L)
                        .name("")
                        .address("")
                        .build()
                )
                .build()
        );
    }

    @Test
    void createMusicBand_ReturnsResponseWithStatusBadRequest() throws Exception {
        setupDb();
        final Long id = 3L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1/music-bands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": "",
                    "coordinates": {
                        "x": 123456.0,
                        "y": 2147483648
                    },
                    "coordinatesId": null,
                    "genre": "BRIT_POP",
                    "numberOfParticipants": 9223372036854775808,
                    "singlesCount": 0,
                    "description": "",
                    "bestAlbum": {
                        "name": "first album",
                        "length": 2147483647
                    },
                    "bestAlbumId": null,
                    "albumsCount": -10,
                    "establishmentDate": "2021-01-01",
                    "studio": {
                        "name": "",
                        "address": ""
                    },
                    "studioId": null
                }
                """
            );

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isBadRequest()
            );

        checkEntityNotExistsById(mockMvc, id);
    }

    @Test
    void createMusicBandWithSubObjectsIds_ReturnsResponseWithStatusCreated() throws Exception {
        setupDb();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1/music-bands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": "created music band",
                    "coordinates": null,
                    "coordinatesId": 1,
                    "genre": "BRIT_POP",
                    "numberOfParticipants": 9223372036854775807,
                    "singlesCount": 9223372036854775807,
                    "description": "",
                    "bestAlbum": null,
                    "bestAlbumId": 2,
                    "albumsCount": 9223372036854775807,
                    "establishmentDate": "2021-01-01",
                    "studio": null,
                    "studioId": 1
                }
                """
            );

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isCreated()
            );

        checkEntityExistByIdAndEqualExpectedMusicBandEntity(
            mockMvc,
            MusicBand.builder()
                .id(3L)
                .name("created music band")
                .coordinates(
                    Coordinates.builder()
                        .id(1L)
                        .x(1.0f)
                        .y(2)
                        .build()
                )
                .genre(MusicGenre.BRIT_POP)
                .numberOfParticipants(9223372036854775807L)
                .singlesCount(9223372036854775807L)
                .description("")
                .bestAlbum(
                    Album.builder()
                        .id(2L)
                        .name("second album")
                        .length(1000)
                        .build()
                )
                .albumsCount(9223372036854775807L)
                .establishmentDate(LocalDate.of(2021, 1, 1))
                .studio(
                    Studio.builder()
                        .id(1L)
                        .name("first studio")
                        .address("first studio address")
                        .build()
                )
                .build()
        );
    }

    @Test
    void createMusicBandWithSubObjectsIds_ReturnsResponseWithStatusNotFound() throws Exception {
        setupDb();
        final Long id = 3L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1/music-bands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": "created music band",
                    "coordinates": null,
                    "coordinatesId": 4,
                    "genre": "BRIT_POP",
                    "numberOfParticipants": 9223372036854775807,
                    "singlesCount": 9223372036854775807,
                    "description": "",
                    "bestAlbum": null,
                    "bestAlbumId": 2,
                    "albumsCount": 9223372036854775807,
                    "establishmentDate": "2021-01-01",
                    "studio": null,
                    "studioId": 1
                }
                """
            );

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNotFound()
            );

        checkEntityNotExistsById(mockMvc, id);
    }

    @Test
    void getMusicBandById_ReturnsResponseWithStatusOk() throws Exception {
        setupDb();
        checkEntityExistByIdAndEqualExpectedMusicBandEntity(
            mockMvc,
            MusicBand.builder()
                .id(1L)
                .name("first band")
                .coordinates(
                    Coordinates.builder()
                        .id(1L)
                        .x(1.0f)
                        .y(2)
                        .build()
                )
                .genre(MusicGenre.PROGRESSIVE_ROCK)
                .numberOfParticipants(4L)
                .singlesCount(5L)
                .description("first band description")
                .bestAlbum(
                    Album.builder()
                        .id(1L)
                        .name("first album")
                        .length(12)
                        .build()
                )
                .albumsCount(2L)
                .establishmentDate(LocalDate.of(2024, 8, 3))
                .studio(
                    Studio.builder()
                        .id(1L)
                        .name("first studio")
                        .address("first studio address")
                        .build()
                )
                .build()
        );
    }

    @Test
    void getMusicBandById_ReturnsResponseWithStatusNotFound() throws Exception {
        setupDb();
        final Long id = 100L;
        checkEntityNotExistsById(mockMvc, id);
    }

    @Test
    void putMusicBandById_ReturnsResponseWithStatusNoContent() throws Exception {
        setupDb();
        final Long id = 1L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/music-bands/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": "012345678901234567890123456789",
                    "coordinates": {
                        "x": -100.12314,
                        "y": -2147483648
                    },
                    "coordinatesId": null,
                    "genre": "POST_PUNK",
                    "numberOfParticipants": 9223372036854775807,
                    "singlesCount": 9223372036854775807,
                    "description": null,
                    "bestAlbum": null,
                    "bestAlbumId": null,
                    "albumsCount": 9223372036854775807,
                    "establishmentDate": "2024-08-03",
                    "studio": null,
                    "studioId": null
                }
                """
            );

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNoContent()
            );

        checkEntityExistByIdAndEqualExpectedJsonString(
            mockMvc,
            id,
            """
            {
                "id": 1,
                "name": "012345678901234567890123456789",
                "coordinates": {
                    "id": 3,
                    "x": -100.12314,
                    "y": -2147483648
                },
                "creationDate": "2024-08-03T19:09:40.936657",
                "genre": "POST_PUNK",
                "numberOfParticipants": 9223372036854775807,
                "singlesCount": 9223372036854775807,
                "description": null,
                "bestAlbum": null,
                "albumsCount": 9223372036854775807,
                "establishmentDate": "2024-08-03",
                "studio": null
            }
            """
        );
    }

    @Test
    void putMusicBandById_ReturnsResponseWithStatusNotFound() throws Exception {
        setupDb();
        final Long id = 100L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/music-bands/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": "012345678901234567890123456789",
                    "coordinates": {
                        "x": -100.12314,
                        "y": -2147483648
                    },
                    "coordinatesId": null,
                    "genre": "POST_PUNK",
                    "numberOfParticipants": 9223372036854775807,
                    "singlesCount": 9223372036854775807,
                    "description": null,
                    "bestAlbum": null,
                    "bestAlbumId": null,
                    "albumsCount": 9223372036854775807,
                    "establishmentDate": "2024-08-03",
                    "studio": null,
                    "studioId": null
                }
                """
            );

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNotFound()
            );

        checkEntityNotExistsById(mockMvc, id);
    }

    @Test
    void putMusicBandById_ReturnsResponseWithStatusBadRequest() throws Exception {
        setupDb();
        final Long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/music-bands/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": "",
                    "coordinates": {
                        "x": 123456.0,
                        "y": 2147483648
                    },
                    "coordinatesId": null,
                    "genre": "BRIT_POP",
                    "numberOfParticipants": 9223372036854775808,
                    "singlesCount": 0,
                    "description": "",
                    "bestAlbum": {
                        "name": "first album",
                        "length": 2147483647
                    },
                    "bestAlbumId": null,
                    "albumsCount": -10,
                    "establishmentDate": "2021-01-01",
                    "studio": {
                        "name": "",
                        "address": ""
                    },
                    "studioId": null
                }
                """
            );

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isBadRequest()
            );

        checkEntityExistByIdAndEqualExpectedJsonString(
            mockMvc,
            id,
            """
            {
                "id": 1,
                "name": "first band",
                "coordinates": {
                    "id": 1,
                    "x": 1.0,
                    "y": 2
                },
                "creationDate": "2024-08-03T19:09:40.936657",
                "genre": "PROGRESSIVE_ROCK",
                "numberOfParticipants": 4,
                "singlesCount": 5,
                "description": "first band description",
                "bestAlbum": {
                    "id": 1,
                    "name": "first album",
                    "length": 12
                },
                "albumsCount": 2,
                "establishmentDate": "2024-08-03",
                "studio": {
                    "id": 1,
                    "name": "first studio",
                    "address": "first studio address"
                }
            }
            """
        );
    }

    @Test
    void putMusicBandByIdWithSubObjectsIds_ReturnsResponseWithStatusNoContent() throws Exception {
        setupDb();
        final Long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/music-bands/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": "012345678901234567890123456789",
                    "coordinates": null,
                    "coordinatesId": 1,
                    "genre": "POST_PUNK",
                    "numberOfParticipants": 9223372036854775807,
                    "singlesCount": 9223372036854775807,
                    "description": null,
                    "bestAlbum": null,
                    "bestAlbumId": 1,
                    "albumsCount": 9223372036854775807,
                    "establishmentDate": "2024-08-03",
                    "studio": null,
                    "studioId": 1
                }
                """
            );

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNoContent()
            );

        checkEntityExistByIdAndEqualExpectedJsonString(
            mockMvc,
            id,
            """
            {
                "id": 1,
                "name": "012345678901234567890123456789",
                "coordinates": {
                    "id": 1,
                    "x": 1.0,
                    "y": 2
                },
                "creationDate": "2024-08-03T19:09:40.936657",
                "genre": "POST_PUNK",
                "numberOfParticipants": 9223372036854775807,
                "singlesCount": 9223372036854775807,
                "description": null,
                "bestAlbum": {
                    "id": 1,
                    "name": "first album",
                    "length": 12
                },
                "albumsCount": 9223372036854775807,
                "establishmentDate": "2024-08-03",
                "studio": {
                    "id": 1,
                    "name": "first studio",
                    "address": "first studio address"
                }
            }
            """
        );
    }

    @Test
    void putMusicBandByIdWithSubObjectsIds_ReturnsResponseWithStatusNotFound() throws Exception {
        setupDb();
        final Long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/music-bands/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": "012345678901234567890123456789",
                    "coordinates": null,
                    "coordinatesId": 3,
                    "genre": "POST_PUNK",
                    "numberOfParticipants": 9223372036854775807,
                    "singlesCount": 9223372036854775807,
                    "description": null,
                    "bestAlbum": null,
                    "bestAlbumId": null,
                    "albumsCount": 9223372036854775807,
                    "establishmentDate": "2024-08-03",
                    "studio": null,
                    "studioId": null
                }
                """
            );

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNotFound()
            );

        checkEntityExistByIdAndEqualExpectedJsonString(
            mockMvc,
            id,
            """
            {
                "id": 1,
                "name": "012345678901234567890123456789",
                "coordinates": {
                    "id": 1,
                    "x": 1.0,
                    "y": 2
                },
                "creationDate": "2024-08-03T19:09:40.936657",
                "genre": "POST_PUNK",
                "numberOfParticipants": 9223372036854775807,
                "singlesCount": 9223372036854775807,
                "description": null,
                "bestAlbum": {
                    "id": 1,
                    "name": "first album",
                    "length": 12
                },
                "albumsCount": 9223372036854775807,
                "establishmentDate": "2024-08-03",
                "studio": {
                    "id": 1,
                    "name": "first studio",
                    "address": "first studio address"
                }
            }
            """
        );
    }

    @Test
    void deleteMusicBandById_ReturnsResponseWithStatusNoContent() throws Exception {
        setupDb();
        final Long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/api/v1/music-bands/{id}", id);

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNoContent()
            );

        checkEntityNotExistsById(mockMvc, id);
    }

    @Test
    void deleteMusicBandById_ReturnsResponseWithStatusNotFound() throws Exception {
        setupDb();
        final Long id = 100L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/api/v1/music-bands/{id}", id);

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNotFound()
            );
    }
}
