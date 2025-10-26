package lab.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class CoordinatesControllerTest extends SpringBootApplicationTest {
    protected String getEndpointGettingEntityById() {
        return "/api/v1/coordinates/{id}";
    }

    @Test
    void createCoordinates_ReturnsResponseWithStatusCreated() throws Exception {
        setupDb();
        final Long id = 4L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1/coordinates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "x": 0.0,
                    "y": 0
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
                "x": 0.0,
                "y": 0
            }
            """
        );
    }

    @Test
    void createCoordinates_ReturnsResponseWithStatusBadRequest() throws Exception {
        setupDb();
        final Long id = 4L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1/coordinates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "x": null,
                    "y": 0
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
    void getCoordinatesById_ReturnsResponseWithStatusOk() throws Exception {
        setupDb();
        final Long id = 1L;
        checkEntityExistByIdAndEqualExpectedJsonString(
            id,
            """
            {
                "id": 1,
                "x": 1.0,
                "y": 2
            }
            """
        );
    }

    @Test
    void getCoordinatesById_ReturnsResponseWithStatusNotFound() throws Exception {
        setupDb();
        final Long id = 100L;
        checkEntityNotExistsById(id);
    }

    @Test
    void putCoordinatesById_ReturnsResponseWithStatusNoContent() throws Exception {
        setupDb();
        final Long id = 1L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/coordinates/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "x": 123.123,
                    "y": 123
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
                "x": 123.123,
                "y": 123
            }
            """
        );
    }

    @Test
    void putCoordinatesById_ReturnsResponseWithStatusNotFound() throws Exception {
        setupDb();
        final Long id = 100L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/coordinates/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "x": 123.123,
                    "y": 123
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
    void putCoordinatesById_ReturnsResponseWithStatusBadRequest() throws Exception {
        setupDb();
        final Long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/coordinates/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "x": null,
                    "y": 0
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
                "x": 1.0,
                "y": 2
            }
            """
        );
    }

    @Test
    void deleteCoordinatesById_ReturnsResponseWithStatusNoContent() throws Exception {
        setupDb();
        final Long id = 3L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/api/v1/coordinates/{id}", id);

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
            .delete("/api/v1/coordinates/{id}", id);

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNotFound()
            );
        checkEntityNotExistsById(id);
    }

    @Test
    void deleteAlbumById_ReturnsResponseWithStatusConflict() throws Exception {
        setupDb();
        final Long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/api/v1/coordinates/{id}", id);

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isConflict()
            );

        checkEntityExistByIdAndEqualExpectedJsonString(
            id,
            """
            {
                "id": 1,
                "x": 1.0,
                "y": 2
            }
            """
        );
    }
}
