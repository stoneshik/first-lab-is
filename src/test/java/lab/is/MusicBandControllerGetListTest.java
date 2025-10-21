package lab.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class MusicBandControllerGetListTest extends SpringBootApplicationTest {
    protected String getEndpointGettingEntityById() {
        return "/api/v1/music-bands/{id}";
    }

    @Test
    void getEmptyListMusicBand_ReturnsResponseWithStatusOk() throws Exception {
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
    void getListMusicBand_ReturnsResponseWithStatusOk() throws Exception {
        setupDb();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v1/music-bands");

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith("application/json"),
                content().json("""
                    [
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
                """)
            );
    }

    //TODO пагинация
}
