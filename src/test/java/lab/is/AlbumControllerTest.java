package lab.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class AlbumControllerTest extends SpringBootApplicationTest {
    protected String getEndpointGettingEntityById() {
        return "/api/v1/albums/{id}";
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

        checkEntityExistByIdAndExpectedJsonString(
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
}
