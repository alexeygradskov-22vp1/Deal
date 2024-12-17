package ru.gav.creditbank.deal.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gav.creditbank.deal.dao.StatementDao;
import ru.gav.creditbank.deal.dto.StatementDto;
import ru.gav.creditbank.deal.entity.statement.Statement;
import ru.gav.creditbank.deal.exception.NoSuchElementInDatabaseException;
import ru.gav.creditbank.deal.mappers.StatementMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatementServiceImplTest {

    private Statement statement;
    private StatementDto statementDto;

    @Mock
    private StatementDao statementDao;
    @Mock
    private StatementMapper statementMapper;
    @InjectMocks
    private StatementServiceImpl statementService;

    @BeforeAll
    public void init(){
        try (InputStream statementIs = Statement.class.getResourceAsStream("/test/data/statement.json");
             InputStream statementDtoIS = StatementDto.class.getResourceAsStream("/test/data/statement-dto.json");) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JsonNullableModule());
            objectMapper.registerModule(new JavaTimeModule());
            statementDto = objectMapper.readValue(statementDtoIS, StatementDto.class);
            statement = objectMapper.readValue(statementIs, Statement.class);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Test
    void save() {
        StatementServiceImpl statementServiceWithMocks = new StatementServiceImpl(statementDao,statementMapper);
        doReturn(statement).when(statementMapper).mapDtoToEntity(statementDto);
        doReturn(statement).when(statementDao).save(statement);
        doReturn(statementDto).when(statementMapper).mapEntityToDto(statement);
        StatementDto saved = statementServiceWithMocks.save(statementDto);
        assertEquals(statementDto, saved);
    }

    @Test
    void update() {
        doReturn(statement).when(statementDao).getOne(statement.getStatementId());
        doReturn(statement).when(statementMapper).mapDtoToEntity(statementDto);
        doReturn(statement).when(statementDao).update(statement);
        doReturn(statementDto).when(statementMapper).mapEntityToDto(statement);
        assertDoesNotThrow(()->statementService.update(statementDto));
    }

    @Test
    void updateNotExists() {
        StatementServiceImpl statementServiceWithMocks = new StatementServiceImpl(statementDao,statementMapper);
        doThrow(NoSuchElementInDatabaseException.class).when(statementDao).getOne(any(UUID.class));
        assertThrows(NoSuchElementInDatabaseException.class, ()->statementServiceWithMocks.update(statementDto));
    }

    @Test
    void getOne() {
        doReturn(statement).when(statementDao).getOne(statement.getStatementId());
        doReturn(statementDto).when(statementMapper).mapEntityToDto(statement);
        assertEquals(statementDto, statementService.getOne(statement.getStatementId()));
    }

    @Test
    void getNotExists() {
        StatementServiceImpl statementServiceWithMocks = new StatementServiceImpl(statementDao,statementMapper);
        doThrow(NoSuchElementInDatabaseException.class).when(statementDao).getOne(any(UUID.class));
        assertThrows(NoSuchElementInDatabaseException.class, ()->statementServiceWithMocks.update(statementDto));
    }
}