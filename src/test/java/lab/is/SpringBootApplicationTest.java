package lab.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class SpringBootApplicationTest {
    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> postgresSqlContainer = new PostgreSQLContainer<>("postgres:16.4")
            .withReuse(true)
            .withDatabaseName("is_service");

    @Autowired
    protected MockMvc mockMvc;

    @PersistenceContext
    protected EntityManager entityManager;

    @AfterAll
    void stopContainers() {
        if (postgresSqlContainer.isRunning()) {
            postgresSqlContainer.stop();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void setupDb() {
        clearDb();
        createEntitiesInDb();
        forceWritingToDb();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void setupEmptyDb() {
        clearDb();
    }

    private void clearDb() {
        entityManager.createNativeQuery(
            """
            TRUNCATE musicBands RESTART IDENTITY CASCADE
            """
        ).executeUpdate();
        entityManager.clear(); // сбросить кэш при необходимости
    }

    protected void createEntitiesInDb() {
        //TODO
    }

    private void forceWritingToDb() {
        entityManager.flush();
        entityManager.clear();
    }

    protected void checkEntityExistByIdAndEqualExpectedJsonString(
        Long id,
        String expectedJsonString
    ) throws Exception {
        final String endpoint = getEndpointGettingEntityById();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get(endpoint, id);
        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith("application/json"),
                content().json(expectedJsonString)
            );
    }

    protected void checkEntityNotExistsById(Long id) throws Exception {
        final String endpoint = getEndpointGettingEntityById();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get(endpoint, id);
        mockMvc
            .perform(requestBuilder)
            .andExpectAll(
                status().isNotFound()
            );
    }

    protected abstract String getEndpointGettingEntityById();
}
