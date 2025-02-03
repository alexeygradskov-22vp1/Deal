package ru.gav.creditbank.deal.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.gav.creditbank.deal.entity.client.Client;
import ru.gav.creditbank.deal.exception.NoSuchElementInDatabaseException;
import ru.gav.creditbank.deal.repository.ClientRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(initializers = {ClientDaoImplTest.Initializer.class})
@Testcontainers
@ActiveProfiles("test")
@TestPropertySource("/application-test.yaml")
class ClientDaoImplTest {


    @Container
    private static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("test-db")
            .withPassword("test")
            .withUsername("test");
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientDaoImpl clientDao;

    static {
        container.start();
    }


    @Test
    @Transactional
    void save() {
        clientDao.save(new Client());
        clientDao.save(new Client());
        clientDao.save(new Client());
        clientDao.save(new Client());
        clientDao.save(new Client());
        assertEquals(5, clientRepository.findAll().size());
    }

    @Test
    @Transactional
    void update() {
        Client saved = clientDao.save(new Client());
        saved.setAccountNumber("string");
        clientDao.update(saved);
        assertEquals("string", clientRepository.findById(saved.getClientId()).get().getAccountNumber());
    }

    @Test
    @Transactional
    void updateIfNotExists() {
        assertThrows(NoSuchElementInDatabaseException.class, () -> clientDao.update(Client.builder().clientId(UUID.randomUUID()).build()));
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