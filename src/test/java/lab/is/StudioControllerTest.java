package lab.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class StudioControllerTest extends SpringBootApplicationTest {
    protected String getEndpointGettingEntityById() {
        return "/api/v1/studios/{id}";
    }

    @Test
    void createStudio_ReturnsResponseWithStatusCreated() throws Exception {
        setupDb();
        final Long id = 4L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1/studios")
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
            id,
            """
            {
                "id": 4,
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
                status().isBadRequest()
            );

        checkEntityNotExistsById(id);
    }

    @Test
    void getStudioById_ReturnsResponseWithStatusOk() throws Exception {
        setupDb();
        final Long id = 1L;
        checkEntityExistByIdAndEqualExpectedJsonString(
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
        checkEntityNotExistsById(id);
    }

    @Test
    void putStudioById_ReturnsResponseWithStatusNoContent() throws Exception {
        setupDb();
        final Long id = 1L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/studios/{id}", id)
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

        checkEntityNotExistsById(id);
    }

    @Test
    void putStudioById_ReturnsResponseWithStatusBadRequest() throws Exception {
        setupDb();
        final Long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/api/v1/studios/{id}", id)
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
        final Long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/api/v1/studios/{id}", id);

        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNoContent()
            );

        checkEntityNotExistsById(id);
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
    }
}
