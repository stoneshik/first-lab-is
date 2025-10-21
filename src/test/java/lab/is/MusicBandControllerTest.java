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
class MusicBandControllerTest extends SpringBootApplicationTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void getEmptyListMusicBand_ReturnsResponseWithStatusOk() throws Exception {
        setupEmptyDb();
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v1/music-bands");
        // when
        mockMvc.perform(requestBuilder)
        // then
        .andExpectAll(
            status().isOk(),
            content().contentTypeCompatibleWith("application/json"),
            content().json("[]")
        );
    }

    @Test
    void getListMusicBand_ReturnsResponseWithStatusOk() throws Exception {
        setupDb();
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v1/music-bands");
        // when
        mockMvc.perform(requestBuilder)
        // then
        .andExpectAll(
            status().isOk(),
            content().contentTypeCompatibleWith("application/json"),
            content().json("""
                [
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
                            "name": "",
                            "length": 12
                        },
                        "albumsCount": 2,
                        "establishmentDate": "2024-08-03",
                        "studio": {
                            "name": "",
                            "address": ""
                        }
                    },
                    {
                        "id": 2,
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
                ]
            """)
        );
    }

    @Override
    protected void createEntitiesInDb() {
        // TODO Auto-generated method stub
    }
}
