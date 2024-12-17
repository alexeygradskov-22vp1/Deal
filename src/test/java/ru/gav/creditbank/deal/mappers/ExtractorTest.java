package ru.gav.creditbank.deal.mappers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gav.creditbank.deal.dto.ClientDto;
import ru.gav.creditbank.deal.dto.StatementDto;
import ru.gav.deal.model.FinishRegistrationRequestDto;
import ru.gav.deal.model.LoanStatementRequestDto;
import ru.gav.deal.model.ScoringDataDto;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExtractorTest {

    private FinishRegistrationRequestDto finishRegistrationRequestDto;
    private StatementDto statementDto;
    private LoanStatementRequestDto loanStatementRequestDto;

    @Autowired
    Extractor extractor;

    @BeforeEach
    public void init() {
        try (InputStream statementDtoStream =
                     StatementDto.class.getResourceAsStream("/test/data/statement-dto.json");
             InputStream finishRegistrationStream =
                     FinishRegistrationRequestDto.class.getResourceAsStream("/test/data/finish-registration-dto.json");
             InputStream loanStatementStream = LoanStatementRequestDto.class.getResourceAsStream("/test/data/loan-statement-request-dto.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.registerModule(new JsonNullableModule());
            loanStatementRequestDto = objectMapper.readValue(loanStatementStream, LoanStatementRequestDto.class);
            statementDto = objectMapper.readValue(statementDtoStream, StatementDto.class);
            finishRegistrationRequestDto = objectMapper.readValue(finishRegistrationStream, FinishRegistrationRequestDto.class);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Test
    public void testExtractScoringDataDtoFromFinishRegistrationDtoAndStatementDto() {
        ScoringDataDto scoringDataDto =
                extractor.extractScoringDataDtoFromFinishRegistrationDtoAndStatementDto(
                        finishRegistrationRequestDto,
                        statementDto);
        //amount
        assertNotNull(scoringDataDto.getAmount());
        //term
        assertNotNull(scoringDataDto.getTerm());
        //firstname, lastname
        assertNotNull(scoringDataDto.getFirstName());
        assertNotNull(scoringDataDto.getLastName());
        //gender
        assertNotNull(scoringDataDto.getGender());
        //birth
        assertNotNull(scoringDataDto.getBirthdate());
        //passport
        assertNotNull(scoringDataDto.getPassportSeries());
        assertNotNull(scoringDataDto.getPassportNumber());
        assertNotNull(scoringDataDto.getPassportIssueDate());
        assertNotNull(scoringDataDto.getPassportIssueBranch());
        //marital
        assertNotNull(scoringDataDto.getMaritalStatus());
        //dependent amount
        assertNotNull(scoringDataDto.getDependentAmount());
        //employment
        assertNotNull(scoringDataDto.getEmployment());
        assertNotNull(scoringDataDto.getEmployment().getEmploymentStatus());
        assertNotNull(scoringDataDto.getEmployment().getEmployerINN());
        assertNotNull(scoringDataDto.getEmployment().getSalary());
        assertNotNull(scoringDataDto.getEmployment().getPosition());
        assertNotNull(scoringDataDto.getEmployment().getWorkExperienceTotal());
        assertNotNull(scoringDataDto.getEmployment().getWorkExperienceCurrent());
        //account number
        assertNotNull(scoringDataDto.getAccountNumber());
        //flags
        assertNotNull(scoringDataDto.getIsInsuranceEnabled());
        assertNotNull(scoringDataDto.getIsSalaryClient());

    }

    @Test
    public void testExtractClientFromLoanStatement() {
        ClientDto clientDto =
                extractor.extractClientFromLoanStatement(
                        loanStatementRequestDto);
        assertTrue(clientDto.getLastName().isPresent());
        assertTrue(clientDto.getFirstName().isPresent());
        assertTrue(clientDto.getMiddleName().isPresent());
        assertNotNull(clientDto.getBirthdate());
        assertTrue(clientDto.getEmail().isPresent());
        assertNotNull(clientDto.getPassport());
        assertNotNull(clientDto.getPassport().getNumber());
        assertNotNull(clientDto.getPassport().getSeries());
    }
}
