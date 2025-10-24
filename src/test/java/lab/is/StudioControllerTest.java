package lab.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
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
class StudioControllerTest extends SpringBootApplicationTest {
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void init() {
        setupDb();
    }

    protected String getEndpointGettingEntityById() {
        return "/api/v1/studios/{id}";
    }

    @Test
    void createStudio_ReturnsResponseWithStatusCreated() throws Exception {
        setupDb();
        final Long id = 3L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1/studios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": "created studio",
                    "address": "created studio address"
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
                "id": 3,
                "name": "created studio",
                "address": "created studio address"
            }
            """
        );
    }

    @Test
    void createStudio_ReturnsResponseWithStatusBadRequest() throws Exception {
        setupDb();
        final Long id = 3L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1/studios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": null,
                    "address": null
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
    void getStudioById_ReturnsResponseWithStatusOk() throws Exception {
        setupDb();
        final Long id = 1L;

        checkEntityExistByIdAndEqualExpectedJsonString(
            mockMvc,
            id,
            """
            {
                "id": 1,
                "name": "first studio",
                "address": "first studio address"
            }
            """
        );
    }

    @Test
    void getStudioById_ReturnsResponseWithStatusNotFound() throws Exception {
        setupDb();

        final Long id = 100L;
        checkEntityNotExistsById(mockMvc, id);
    }

    @Test
    void putStudioById_ReturnsResponseWithStatusNoContent() throws Exception {
        setupDb();
        final Long id = 1L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/studios/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": "updated studio",
                    "address": "updated studio address"
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
                "name": "updated studio",
                "address": "updated studio address"
            }
            """
        );
    }

    @Test
    void putStudioById_ReturnsResponseWithStatusNotFound() throws Exception {
        setupDb();
        final Long id = 100L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/studios/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": "updated studio",
                    "address": "updated studio address"
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
    void putStudioById_ReturnsResponseWithStatusBadRequest() throws Exception {
        setupDb();
        final Long id = 1L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/studios/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                    "name": null,
                    "address": null
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
                "name": "first studio",
                "address": "first studio address"
            }
            """
        );
    }

    @Test
    void deleteStudioById_ReturnsResponseWithStatusNoContent() throws Exception {
        setupDb();
        final Long id = 2L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/api/v1/studios/{id}", id);

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNoContent()
            );

        checkEntityNotExistsById(mockMvc, id);
    }

    @Test
    void deleteStudioById_ReturnsResponseWithStatusNotFound() throws Exception {
        setupDb();
        final Long id = 100L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/api/v1/studios/{id}", id);

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNotFound()
            );
        checkEntityNotExistsById(mockMvc, id);
    }

    @Test
    void deleteStudioById_ReturnsResponseWithStatusConflict() throws Exception {
        setupDb();
        final Long id = 1L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/api/v1/studios/{id}", id);

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
                "name": "first studio",
                "address": "first studio address"
            }
            """
        );
    }
}
