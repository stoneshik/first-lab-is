package lab.is;

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
class AlbumControllerTest extends SpringBootApplicationTest {
    @Autowired
    MockMvc mockMvc;

    protected String getEndpointGettingEntityById() {
        return "/api/v1/albums/{id}";
    }

    @Test
    void createAlbum_ReturnsResponseWithStatusCreated() throws Exception {
        setupDb();
        final Long id = 4L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": "created album",
                    "length": 1
                }
                """
            );

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isCreated()
            );

        checkEntityExistByIdAndEqualExpectedJsonString(
            mockMvc,
            id,
            """
            {
                "id": 4,
                "name": "created album",
                "length": 1
            }
            """
        );
    }

    @Test
    void createAlbum_ReturnsResponseWithStatusBadRequest() throws Exception {
        setupDb();
        final Long id = 3L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1/music-albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": "",
                    "length": 0
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
    void getAlbumById_ReturnsResponseWithStatusOk() throws Exception {
        setupDb();
        final Long id = 1L;
        checkEntityExistByIdAndEqualExpectedJsonString(
            mockMvc,
            id,
            """
            {
                "id": 1,
                "name": "first album",
                "length": 12
            }
            """
        );
    }

    @Test
    void getAlbumById_ReturnsResponseWithStatusNotFound() throws Exception {
        setupDb();
        final Long id = 100L;
        checkEntityNotExistsById(mockMvc, id);
    }

    @Test
    void putAlbumById_ReturnsResponseWithStatusNoContent() throws Exception {
        setupDb();
        final Long id = 1L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/albums/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": "updated album",
                    "length": 1
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
                "name": "updated album",
                "length": 1
            }
            """
        );
    }

    @Test
    void putAlbumById_ReturnsResponseWithStatusNotFound() throws Exception {
        setupDb();
        final Long id = 100L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/albums/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": "updated album",
                    "length": 1
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
    void putAlbumById_ReturnsResponseWithStatusBadRequest() throws Exception {
        setupDb();
        final Long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/albums/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": "",
                    "length": 0
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
                "name": "first album",
                "length": 12
            }
            """
        );
    }

    @Test
    void deleteAlbumById_ReturnsResponseWithStatusNoContent() throws Exception {
        setupDb();
        final Long id = 3L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/api/v1/albums/{id}", id);

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNoContent()
            );

        checkEntityNotExistsById(mockMvc, id);
    }

    @Test
    void deleteAlbumById_ReturnsResponseWithStatusNotFound() throws Exception {
        setupDb();
        final Long id = 100L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/api/v1/albums/{id}", id);

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNotFound()
            );
        checkEntityNotExistsById(mockMvc, id);
    }

    @Test
    void deleteAlbumById_ReturnsResponseWithStatusConflict() throws Exception {
        setupDb();
        final Long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/api/v1/albums/{id}", id);

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isConflict()
            );

        checkEntityExistByIdAndEqualExpectedJsonString(
            mockMvc,
            id,
            """
            {
                "id": 1,
                "name": "first album",
                "length": 12
            }
            """
        );
    }
}
