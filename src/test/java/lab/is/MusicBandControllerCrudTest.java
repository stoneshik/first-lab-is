package lab.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
class MusicBandControllerCrudTest extends SpringBootApplicationTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void createMusicBand_ReturnsResponseWithStatusCreated() throws Exception {
        setupDb();
        final Long id = 1L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1/music-bands")
            .content(
                """
                {
                    "name": "created music band",
                    "coordinates": {
                        "x": 123456.0,
                        "y": 2147483647
                    },
                    "genre": "BRIT_POP",
                    "numberOfParticipants": 9223372036854775807,
                    "singlesCount": 9223372036854775807,
                    "description": "",
                    "bestAlbum": {
                        "name": "first album",
                        "length": 2147483647
                    },
                    "albumsCount": 9223372036854775807,
                    "establishmentDate": "2021-01-01",
                    "studio": {
                        "name": "",
                        "address": ""
                    }
                }
                """
            );

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isCreated()
            );

        checkMusicBandByIdAndExpectedJsonString(
            id,
            """
            {
                "id": 3,
                "name": "created music band",
                "coordinates": {
                    "x": 123456.0,
                    "y": 2147483647
                },
                "creationDate": "2024-08-03T19:09:40.936657",
                "genre": "BRIT_POP",
                "numberOfParticipants": 9223372036854775807,
                "singlesCount": 9223372036854775807,
                "description": "",
                "bestAlbum": {
                    "name": "first album",
                    "length": 2147483647
                },
                "albumsCount": 9223372036854775807,
                "establishmentDate": "2021-01-01",
                "studio": {
                    "name": "",
                    "address": ""
                }
            }
            """
        );
    }

    @Test
    void createMusicBand_ReturnsResponseWithStatusBadRequest() throws Exception {
        setupDb();
        final Long id = 1L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1/music-bands")
            .content(
                """
                {
                    "name": "",
                    "coordinates": {
                        "x": 123456.0,
                        "y": 2147483648
                    },
                    "genre": "BRIT_POP",
                    "numberOfParticipants": 9223372036854775808,
                    "singlesCount": 0,
                    "description": "",
                    "bestAlbum": {
                        "name": "first album",
                        "length": 2147483647
                    },
                    "albumsCount": -10,
                    "establishmentDate": "2021-01-01",
                    "studio": {
                        "name": "",
                        "address": ""
                    }
                }
                """
            );

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isBadRequest()
            );

        checkMusicBandByIdNotFound(id);
    }

    @Test
    void createMusicBandWithSubObjectsIds_ReturnsResponseWithStatusCreated() throws Exception {
        setupDb();
        final Long id = 1L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1/music-bands")
            .content(
                """
                {
                    "name": "created music band",
                    "coordinates": 1,
                    "genre": "BRIT_POP",
                    "numberOfParticipants": 9223372036854775807,
                    "singlesCount": 9223372036854775807,
                    "description": "",
                    "bestAlbum": 2,
                    "albumsCount": 9223372036854775807,
                    "establishmentDate": "2021-01-01",
                    "studio": 1
                }
                """
            );

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isCreated()
            );

        checkMusicBandByIdAndExpectedJsonString(
            id,
            """
            {
                "id": 3,
                "name": "created music band",
                "coordinates": {
                    "x": 1.0,
                    "y": 2
                },
                "creationDate": "2024-08-03T19:09:40.936657",
                "genre": "BRIT_POP",
                "numberOfParticipants": 9223372036854775807,
                "singlesCount": 9223372036854775807,
                "description": "",
                "bestAlbum": 2,
                "albumsCount": 9223372036854775807,
                "establishmentDate": "2021-01-01",
                "studio": {
                    "name": "first studio",
                    "address": "first studio address"
                }
            }
            """
        );
    }

    @Test
    void createMusicBandWithSubObjectsIds_ReturnsResponseWithStatusNotFound() throws Exception {
        setupDb();
        final Long id = 1L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1/music-bands")
            .content(
                """
                {
                    "name": "created music band",
                    "coordinates": 2,
                    "genre": "BRIT_POP",
                    "numberOfParticipants": 9223372036854775807,
                    "singlesCount": 9223372036854775807,
                    "description": "",
                    "bestAlbum": 2,
                    "albumsCount": 9223372036854775807,
                    "establishmentDate": "2021-01-01",
                    "studio": 1
                }
                """
            );

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNotFound()
            );

        checkMusicBandByIdNotFound(id);
    }

    @Test
    void getMusicBandById_ReturnsResponseWithStatusOk() throws Exception {
        setupDb();
        final Long id = 1L;
        checkMusicBandByIdAndExpectedJsonString(
            id,
            """
            {
                "id": 1,
                "name": "first band",
                "coordinates": {
                    "x": 1.0,
                    "y": 2
                },
                "creationDate": "2024-08-03T19:09:40.936657",
                "genre": "PROGRESSIVE_ROCK",
                "numberOfParticipants": 4,
                "singlesCount": 5,
                "description": "first band description",
                "bestAlbum": {
                    "name": "first album",
                    "length": 12
                },
                "albumsCount": 2,
                "establishmentDate": "2024-08-03",
                "studio": {
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

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v1/music-bands/{id}", id);

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNotFound()
            );
    }

    @Test
    void putMusicBandById_ReturnsResponseWithStatusNoContent() throws Exception {
        setupDb();
        final Long id = 1L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/music-bands/{id}", id)
            .content(
                """
                {
                    "name": "012345678901234567890123456789",
                    "coordinates": {
                        "x": -100.12314,
                        "y": -2147483648
                    },
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

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNoContent()
            );

        checkMusicBandByIdAndExpectedJsonString(
            id,
            """
            {
                "id": 1,
                "name": "012345678901234567890123456789",
                "coordinates": {
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
            .content(
                """
                {
                    "name": "012345678901234567890123456789",
                    "coordinates": {
                        "x": -100.12314,
                        "y": -2147483648
                    },
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

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNotFound()
            );
    }

    @Test
    void putMusicBandById_ReturnsResponseWithStatusBadRequest() throws Exception {
        setupDb();
        final Long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/music-bands/{id}", id)
            .content(
                """
                {
                    "name": "",
                    "coordinates": {
                        "x": 123456.0,
                        "y": 2147483648
                    },
                    "genre": "BRIT_POP",
                    "numberOfParticipants": 9223372036854775808,
                    "singlesCount": 0,
                    "description": "",
                    "bestAlbum": {
                        "name": "first album",
                        "length": 2147483647
                    },
                    "albumsCount": -10,
                    "establishmentDate": "2021-01-01",
                    "studio": {
                        "name": "",
                        "address": ""
                    }
                }
                """
            );

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isBadRequest()
            );

        checkMusicBandByIdAndExpectedJsonString(
            id,
            """
            {
                "id": 1,
                "name": "first band",
                "coordinates": {
                    "x": 1.0,
                    "y": 2
                },
                "creationDate": "2024-08-03T19:09:40.936657",
                "genre": "PROGRESSIVE_ROCK",
                "numberOfParticipants": 4,
                "singlesCount": 5,
                "description": "first band description",
                "bestAlbum": {
                    "name": "first album",
                    "length": 12
                },
                "albumsCount": 2,
                "establishmentDate": "2024-08-03",
                "studio": {
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

        checkMusicBandByIdNotFound(id);
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

    private void checkMusicBandByIdAndExpectedJsonString(
        Long id,
        String expectedJsonString
    ) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v1/music-bands/{id}", id);

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith("application/json"),
                content().json(expectedJsonString)
            );
    }

    private void checkMusicBandByIdNotFound(Long id) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v1/music-bands/{id}", id);

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNotFound()
            );
    }
}
