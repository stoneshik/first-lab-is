package lab.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class MusicBandControllerCrudTest extends SpringBootApplicationTest {
    protected String getEndpointGettingEntityById() {
        return "/api/v1/music-bands/{id}";
    }

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
                    "coordinatesId": null,
                    "genre": "BRIT_POP",
                    "numberOfParticipants": 9223372036854775807,
                    "singlesCount": 9223372036854775807,
                    "description": "",
                    "bestAlbum": {
                        "name": "first album",
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

        checkEntityExistByIdAndExpectedJsonString(
            id,
            """
            {
                "id": 3,
                "name": "created music band",
                "coordinates": {
                    "id": 3,
                    "x": 123456.0,
                    "y": 2147483647
                },
                "creationDate": "2024-08-03T19:09:40.936657",
                "genre": "BRIT_POP",
                "numberOfParticipants": 9223372036854775807,
                "singlesCount": 9223372036854775807,
                "description": "",
                "bestAlbum": {
                    "id": 2,
                    "name": "first album",
                    "length": 2147483647
                },
                "albumsCount": 9223372036854775807,
                "establishmentDate": "2021-01-01",
                "studio": {
                    "id": 2,
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

        checkEntityNotExistsById(id);
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

        checkEntityExistByIdAndExpectedJsonString(
            id,
            """
            {
                "id": 3,
                "name": "created music band",
                "coordinates": {
                    "id": 3,
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
                    "id": 1,
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
                    "coordinates": null,
                    "coordinatesId": 3,
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

        checkEntityNotExistsById(id);
    }

    @Test
    void getMusicBandById_ReturnsResponseWithStatusOk() throws Exception {
        setupDb();
        final Long id = 1L;
        checkEntityExistByIdAndExpectedJsonString(
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

        checkEntityExistByIdAndExpectedJsonString(
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

        checkEntityExistByIdAndExpectedJsonString(
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

        checkEntityExistByIdAndExpectedJsonString(
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

        checkEntityNotExistsById(id);
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

        checkEntityNotExistsById(id);
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
