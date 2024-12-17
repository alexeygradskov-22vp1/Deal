package ru.gav.creditbank.deal.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.gav.creditbank.deal.entity.statement.Statement;
import ru.gav.creditbank.deal.entity.statement.enums.ApplicationStatus;
import ru.gav.creditbank.deal.exception.NoSuchElementInDatabaseException;
import ru.gav.creditbank.deal.repository.StatementRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@ContextConfiguration(initializers = {StatementDaoImplTest.Initializer.class})
@Testcontainers
public class StatementDaoImplTest {

    @Container
    private static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("test-db")
            .withPassword("test")
            .withUsername("test");
    @Autowired
    @Qualifier(value = "statementRepository")
    private StatementRepository statementRepository;
    @Autowired
    private StatementDaoImpl statementDao;

    static {
        container.start();
    }



    @Test
    @Transactional
    void save() {
        statementDao.save(new Statement());
        statementDao.save(new Statement());
        statementDao.save(new Statement());
        statementDao.save(new Statement());
        statementDao.save(new Statement());
        assertEquals(5, statementRepository.findAll().size());
    }

    @Test
    @Transactional
    void update() {
        Statement old = statementDao.save(new Statement());
        old.setStatus(ApplicationStatus.CC_APPROVED);
        statementDao.update(old);
        statementRepository.findById(old.getStatementId())
                .ifPresent(x -> assertEquals(x.getStatus(), ApplicationStatus.CC_APPROVED));
    }

    @Test
    @Transactional
    void updateIfNotExists() {
        assertThrows(NoSuchElementInDatabaseException.class,
                () -> statementDao.update(Statement.builder().statementId(UUID.randomUUID()).build()));
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + container.getJdbcUrl(),
                    "spring.datasource.username=" + container.getUsername(),
                    "spring.datasource.password=" + container.getPassword(),
                    "jpa.hibernate.ddl-auto=update",
                    "jpa.hibernate.open-in-view= false",
                    "jpa.hibernate.properties.hibernate.jdbc.lob.non_contextual_creation=true",
                    "jpa.hibernate.properties.hibernate.id.new_generator_mappings= true",
                    "jpa.hibernate.properties.hibernate.dialect= org.hibernate.dialect.PostgreSQLDialect"
            ).applyTo(configurableApplicationContext.getEnvironment());

        }
    }
}
