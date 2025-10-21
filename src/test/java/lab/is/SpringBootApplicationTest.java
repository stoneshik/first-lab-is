package lab.is;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class SpringBootApplicationTest {

    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> postgresSqlContainer = createPostgreSQLContainer();

    protected static PostgreSQLContainer<?> createPostgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:16.4")
            .withReuse(true)
            .withDatabaseName("is_service");
    }

    @PersistenceContext
    protected EntityManager entityManager;

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

    protected abstract void createEntitiesInDb();

    private void forceWritingToDb() {
        entityManager.flush();
        entityManager.clear();
    }

    @AfterAll
    void stopContainers() {
        if (postgresSqlContainer.isRunning()) {
            postgresSqlContainer.stop();
        }
    }
}
