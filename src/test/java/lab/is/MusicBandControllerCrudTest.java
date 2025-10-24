package lab.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class MusicBandControllerCrudTest extends SpringBootApplicationTest {
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
                status().isCreated(),
                jsonPath("$.id").value(3L),
                jsonPath("$.name").value("created music band"),
                jsonPath("$.coordinates.id").value(4L),
                jsonPath("$.coordinates.x").value(123456.0f),
                jsonPath("$.coordinates.y").value(2147483647),
                jsonPath("$.genre").value("BRIT_POP"),
                jsonPath("$.numberOfParticipants").value(9223372036854775807L),
                jsonPath("$.singlesCount").value(9223372036854775807L),
                jsonPath("$.description").value(""),
                jsonPath("$.bestAlbum.id").value(4L),
                jsonPath("$.bestAlbum.name").value("new album"),
                jsonPath("$.bestAlbum.length").value(2147483647),
                jsonPath("$.albumsCount").value(9223372036854775807L),
                jsonPath("$.establishmentDate").value("2021-01-01"),
                jsonPath("$.studio.id").value(3L),
                jsonPath("$.studio.name").value(""),
                jsonPath("$.studio.address").value("")
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
                status().isCreated(),
                jsonPath("$.id").value(3L),
                jsonPath("$.name").value("created music band"),
                jsonPath("$.coordinates.id").value(1L),
                jsonPath("$.coordinates.x").value(1.0f),
                jsonPath("$.coordinates.y").value(2),
                jsonPath("$.genre").value("BRIT_POP"),
                jsonPath("$.numberOfParticipants").value(9223372036854775807L),
                jsonPath("$.singlesCount").value(9223372036854775807L),
                jsonPath("$.description").value(""),
                jsonPath("$.bestAlbum.id").value(2L),
                jsonPath("$.bestAlbum.name").value("second album"),
                jsonPath("$.bestAlbum.length").value(1000),
                jsonPath("$.albumsCount").value(9223372036854775807L),
                jsonPath("$.establishmentDate").value("2021-01-01"),
                jsonPath("$.studio.id").value(1L),
                jsonPath("$.studio.name").value("first studio"),
                jsonPath("$.studio.address").value("first studio")
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
        final Long id = 1L;
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
