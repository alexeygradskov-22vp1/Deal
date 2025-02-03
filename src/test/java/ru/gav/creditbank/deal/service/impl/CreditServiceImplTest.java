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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.gav.creditbank.deal.dao.CreditDao;
import ru.gav.creditbank.deal.dto.CreditDto;
import ru.gav.creditbank.deal.entity.credit.Credit;
import ru.gav.creditbank.deal.mappers.CreditMapper;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@TestPropertySource("/application-test.yaml")
class CreditServiceImplTest {
    private Credit credit;
    private CreditDto creditDto;
    @Mock
    private CreditDao creditDao;
    @Mock
    private CreditMapper creditMapper;

    @InjectMocks
    private CreditServiceImpl creditService;

    @BeforeAll
    public void init() {
        try (InputStream creditIS = Credit.class.getResourceAsStream("/test/data/credit.json");
             InputStream creditDtoIS = CreditDto.class.getResourceAsStream("/test/data/credit-dto.json");) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JsonNullableModule());
            objectMapper.registerModule(new JavaTimeModule());
            creditDto = objectMapper.readValue(creditDtoIS, CreditDto.class);
            credit = objectMapper.readValue(creditIS, Credit.class);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Test
    void save() {
        doReturn(credit).when(creditMapper).mapDtoToEntity(creditDto);
        doReturn(credit).when(creditDao).save(credit);
        doReturn(creditDto).when(creditMapper).mapEntityToDto(credit);
        assertEquals(creditDto, creditService.save(creditDto));
    }
}