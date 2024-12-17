package ru.gav.creditbank.deal.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.gav.creditbank.deal.entity.credit.Credit;
import ru.gav.creditbank.deal.exception.NoSuchElementInDatabaseException;
import ru.gav.creditbank.deal.repository.CreditRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ContextConfiguration(initializers = {CreditDaoImplTest.Initializer.class})
@Testcontainers
class CreditDaoImplTest {

    @Container
    private static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("test-db")
            .withPassword("test")
            .withUsername("test");
    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private CreditDaoImpl creditDao;

    static {
        container.start();
    }



    @Test
    @Transactional
    void save() {
        creditDao.save(new Credit());
        creditDao.save(new Credit());
        creditDao.save(new Credit());
        creditDao.save(new Credit());
        creditDao.save(new Credit());
        List<Credit> credits = creditRepository.findAll();
        assertEquals(5, credits.size());
    }

    @Test
    @Transactional
    void update() {
        Credit old = creditDao.save(new Credit());
        old.setAmount(new BigDecimal(1000));
        creditDao.update(old);
        creditRepository.findById(old.getCreditId())
                .ifPresent(x -> assertEquals(x.getAmount(), new BigDecimal(1000)));
    }

    @Test
    @Transactional
    void updateIfNotExists() {
        assertThrows(NoSuchElementInDatabaseException.class,
                () -> creditDao.update(Credit.builder().creditId(UUID.randomUUID()).build()));
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