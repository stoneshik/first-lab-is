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
    void createAlbum_ReturnsResponseWithStatusCreated() throws Exception {
        setupDb();
        final Long id = 4L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1/albums")
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

        checkEntityNotExistsById(id);
    }

    @Test
    void getAlbumById_ReturnsResponseWithStatusOk() throws Exception {
        setupDb();
        final Long id = 1L;
        checkEntityExistByIdAndEqualExpectedJsonString(
            id,
            """
            {
                "id": 1,
                "name": "created album",
                "length": 1
            }
            """
        );
    }

    @Test
    void getAlbumById_ReturnsResponseWithStatusNotFound() throws Exception {
        setupDb();
        final Long id = 100L;
        checkEntityNotExistsById(id);
    }

    @Test
    void putAlbumById_ReturnsResponseWithStatusNoContent() throws Exception {
        setupDb();
        final Long id = 1L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/albums/{id}", id)
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

        checkEntityNotExistsById(id);
    }

    @Test
    void putAlbumById_ReturnsResponseWithStatusBadRequest() throws Exception {
        setupDb();
        final Long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/albums/{id}", id)
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
        final Long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/api/v1/albums/{id}", id);

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNoContent()
            );

        checkEntityNotExistsById(id);
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
    }
}
