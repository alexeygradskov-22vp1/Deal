package ru.gav.creditbank.deal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.gav.creditbank.deal.dto.StatementDto;
import ru.gav.creditbank.deal.logic.DealService;
import ru.gav.deal.model.FinishRegistrationRequestDto;
import ru.gav.deal.model.LoanOfferDto;
import ru.gav.deal.model.LoanStatementRequestDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest()
@ActiveProfiles("test")
@TestPropertySource("/application-test.yaml")
public class DealControllerTest {

    @Mock
    private final DealService dealService = Mockito.mock(DealService.class);

    @InjectMocks
    private DealController dealController;

    private LoanStatementRequestDto loanStatementRequestDto;
    private StatementDto statementDto;
    private List<LoanOfferDto> loanOfferDto;
    private FinishRegistrationRequestDto finishRegistrationRequestDto;

    @BeforeEach
    void mockWebClient() {
        try (InputStream loanStatementRequestStream =
                     LoanStatementRequestDto.class.getResourceAsStream("/test/data/loan-statement-request-dto.json");
             InputStream statementDtoStream = StatementDto.class.getResourceAsStream("/test/data/statement-dto.json");
             InputStream inputStream = LoanOfferDto.class.getResourceAsStream("/test/data/loan-offers-dto.json");
             InputStream finishRegistrationStream =
                     FinishRegistrationRequestDto.class.getResourceAsStream("/test/data/finish-registration-dto.json");) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JsonNullableModule());
            objectMapper.registerModule(new JavaTimeModule());
            loanStatementRequestDto = objectMapper.readValue(loanStatementRequestStream, LoanStatementRequestDto.class);
            statementDto = objectMapper.readValue(statementDtoStream, StatementDto.class);
            loanOfferDto = Arrays.stream(objectMapper.readValue(inputStream, LoanOfferDto[].class)).toList();
            finishRegistrationRequestDto = objectMapper.readValue(finishRegistrationStream, FinishRegistrationRequestDto.class);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Test
    public void testCalculateStatement() {
        doReturn(loanOfferDto).when(dealService).calculateLoanOffers(loanStatementRequestDto);
        assertEquals(loanOfferDto, dealController.calculateStatement(loanStatementRequestDto).getBody());
    }

    @Test
    public void testFinishRegistration() {
        doNothing().when(dealService).finishRegistration(finishRegistrationRequestDto, statementDto.getStatementId().toString());
        dealController.finishRegistration(statementDto.getStatementId(), finishRegistrationRequestDto);
    }

    @Test
    public void testSelectOffer() {
        doNothing().when(dealService).selectOffer(loanOfferDto.get(0));
        dealController.selectOffer(loanOfferDto.get(0));
    }
}
