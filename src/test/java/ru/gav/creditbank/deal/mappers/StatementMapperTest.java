package ru.gav.creditbank.deal.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gav.creditbank.deal.dto.StatementDto;
import ru.gav.creditbank.deal.entity.statement.Statement;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class StatementMapperTest {
    @Autowired
    StatementMapper statementMapper;

    private StatementDto statementDto;
    private Statement statement;

    @BeforeEach
    public void init() {
        try (InputStream statementDtoIs = StatementDto.class.getResourceAsStream("/test/data/statement-dto.json");
             InputStream statementIS = Statement.class.getResourceAsStream("/test/data/statement.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.registerModule(new JsonNullableModule());
            statementDto = objectMapper.readValue(statementDtoIs, StatementDto.class);
            //init Client
            statement = objectMapper.readValue(statementIS, Statement.class);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @SneakyThrows
    @Test
    public void testMapToEntity() {
        Statement statementTested = statementMapper.mapDtoToEntity(statementDto);
        var valStream = Stream.of(
                Optional.ofNullable(statementTested.getStatementId()),
                Optional.ofNullable(statementTested.getStatus()),
                Optional.ofNullable(statementTested.getClient()),
                Optional.ofNullable(statementTested.getStatusHistory()));
        valStream.forEach(x -> assertTrue(x.isPresent()));
    }

    @SneakyThrows
    @Test
    public void testMapToDto() {
        StatementDto statementDtoTested = statementMapper.mapEntityToDto(statement);
        var valStream = Stream.of(
                Optional.ofNullable(statementDtoTested.getClient()),
                Optional.ofNullable(statementDtoTested.getClient().getLastName().get()),
                Optional.ofNullable(statementDtoTested.getClient().getFirstName().get()),
                Optional.ofNullable(statementDtoTested.getStatementId()),
                Optional.ofNullable(statementDtoTested.getAppliedOffer()),
                Optional.ofNullable(statementDtoTested.getAppliedOffer().getStatementId()),
                Optional.ofNullable(statementDtoTested.getAppliedOffer().getTerm()),
                Optional.ofNullable(statementDtoTested.getStatus()));
        valStream.forEach(x -> assertTrue(x.isPresent()));
    }


    @SneakyThrows
    @Test
    public void testUpdate() {
        Statement updated = statementMapper.update(statementDto, statement);
        //В statementDto данные credit = null, поэтому обновление должно скипнуться, и останутся старые данные
        assertEquals(statement.getCredit(), updated.getCredit());
    }
}
