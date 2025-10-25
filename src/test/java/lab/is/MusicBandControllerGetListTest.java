package lab.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class MusicBandControllerGetListTest extends AbstractMusicBandTest {
    @Autowired
    MockMvc mockMvc;

    protected String getEndpointGettingEntityById() {
        return "/api/v1/music-bands/{id}";
    }

    @Test
    void getEmptyListMusicBands_ReturnsResponseWithStatusOk() throws Exception {
        setupEmptyDb();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v1/music-bands");

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith("application/json"),
                content().json("[]")
            );
    }

    @Test
    void getListMusicBands_ReturnsResponseWithStatusOk() throws Exception {
        setupDb();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v1/music-bands");

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith("application/json"),
                content().json("""
                    {
                        "totalElements": 2,
                        "totalPages": 1,
                        "currentPage": 0,
                        "pageSize": 10,
                        "musicBands": [
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
                                    "id": 1
                                    "name": "first studio",
                                    "address": "first studio address"
                                }
                            },
                            {
                                "id": 2,
                                "name": "012345678901234567890123456789",
                                "coordinates": {
                                    "id": 2,
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
                        ]
                    }
                """)
            );
    }

    @Test
    void getListMusicBand_WithPagination_ReturnsCorrectPageWithStatusOk() throws Exception {
        setupDb();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v1/music-bands")
            .param("page", "0")
            .param("size", "1")
            .param("sort", "name,asc");

        mockMvc.perform(requestBuilder)
            .andExpectAll(
                status().isOk(),
                content().json("""
                    {
                        "totalElements": 2,
                        "totalPages": 2,
                        "currentPage": 0,
                        "pageSize": 1,
                        "musicBands": [
                            {
                                "id": 2,
                                "name": "012345678901234567890123456789",
                                "coordinates": {
                                    "id": 2,
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
                        ]
                    }
                """)
            );
    }

    /* не стоит менять на параметризованный тест пока не решена проблема
     с контекстом при параллельном выполнении тестов */
    @Test
    void getListMusicBand_FilterByName_ReturnsMatchingBandsWithStatusOk() throws Exception {
        setupDb();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v1/music-bands")
            .param("name", "first");

        mockMvc.perform(requestBuilder)
            .andExpectAll(
                status().isOk(),
                content().json("""
                    {
                        "totalElements": 1,
                        "totalPages": 1,
                        "currentPage": 0,
                        "pageSize": 10,
                        "musicBands": [
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
                                    "id": 1
                                    "name": "first studio",
                                    "address": "first studio address"
                                }
                            }
                        ]
                    }
                """)
            );
    }

    @Test
    void getListMusicBand_FilterByGenre_ReturnsMatchingBandsWithStatusOk() throws Exception {
        setupDb();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v1/music-bands")
            .param("genre", "PROGRESS");

        mockMvc.perform(requestBuilder)
            .andExpectAll(
                status().isOk(),
                content().json("""
                    {
                        "totalElements": 1,
                        "totalPages": 1,
                        "currentPage": 0,
                        "pageSize": 10,
                        "musicBands": [
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
                                    "id": 1
                                    "name": "first studio",
                                    "address": "first studio address"
                                }
                            }
                        ]
                    }
                """)
            );
    }

    @Test
    void getListMusicBand_FilterByDescription_ReturnsMatchingBandsWithStatusOk() throws Exception {
        setupDb();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v1/music-bands")
            .param("description", "band");

        mockMvc.perform(requestBuilder)
            .andExpectAll(
                status().isOk(),
                content().json("""
                    {
                        "totalElements": 1,
                        "totalPages": 1,
                        "currentPage": 0,
                        "pageSize": 10,
                        "musicBands": [
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
                                    "id": 1
                                    "name": "first studio",
                                    "address": "first studio address"
                                }
                            }
                        ]
                    }
                """)
            );
    }

    @Test
    void getListMusicBand_FilterByBestAlbumName_ReturnsMatchingBandsWithStatusOk() throws Exception {
        setupDb();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v1/music-bands")
            .param("bestAlbumName", "first");

        mockMvc.perform(requestBuilder)
            .andExpectAll(
                status().isOk(),
                content().json("""
                    {
                        "totalElements": 1,
                        "totalPages": 1,
                        "currentPage": 0,
                        "pageSize": 10,
                        "musicBands": [
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
                                    "id": 1
                                    "name": "first studio",
                                    "address": "first studio address"
                                }
                            }
                        ]
                    }
                """)
            );
    }

    @Test
    void getListMusicBand_FilterByStudioName_ReturnsMatchingBandsWithStatusOk() throws Exception {
        setupDb();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v1/music-bands")
            .param("studioName", "first");

        mockMvc.perform(requestBuilder)
            .andExpectAll(
                status().isOk(),
                content().json("""
                    {
                        "totalElements": 1,
                        "totalPages": 1,
                        "currentPage": 0,
                        "pageSize": 10,
                        "musicBands": [
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
                                    "id": 1
                                    "name": "first studio",
                                    "address": "first studio address"
                                }
                            }
                        ]
                    }
                """)
            );
    }

    @Test
    void getListMusicBand_FilterByStudioAddress_ReturnsMatchingBandsWithStatusOk() throws Exception {
        setupDb();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v1/music-bands")
            .param("studioAddress", "first studio");

        mockMvc.perform(requestBuilder)
            .andExpectAll(
                status().isOk(),
                content().json("""
                    {
                        "totalElements": 1,
                        "totalPages": 1,
                        "currentPage": 0,
                        "pageSize": 10,
                        "musicBands": [
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
                                    "id": 1
                                    "name": "first studio",
                                    "address": "first studio address"
                                }
                            }
                        ]
                    }
                """)
            );
    }
}

