package ru.gav.creditbank.deal.mappers;

import org.mapstruct.*;
import ru.gav.creditbank.deal.dto.ClientDto;
import ru.gav.creditbank.deal.dto.StatementDto;
import ru.gav.creditbank.deal.entity.client.inner.Passport;
import ru.gav.creditbank.deal.exception.NotValidData;
import ru.gav.deal.model.FinishRegistrationRequestDto;
import ru.gav.deal.model.LoanStatementRequestDto;
import ru.gav.deal.model.ScoringDataDto;

import java.util.Optional;

@Mapper(uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface Extractor {

    @Mapping(source = "loanStatementRequestDto", target = "passport", qualifiedByName = "passportFromLoanStatement")
    ClientDto extractClientFromLoanStatement(LoanStatementRequestDto loanStatementRequestDto);

    @Named("passportFromLoanStatement")
    default Passport passportFromLoanStatement(LoanStatementRequestDto loanStatementRequestDto) {
        Passport passport = new Passport();
        passport.setNumber(Optional.ofNullable(loanStatementRequestDto.getPassportNumber()).orElseThrow(() ->
                new NotValidData("Номер паспорта не может быть нулевым")));
        passport.setSeries(Optional.ofNullable(loanStatementRequestDto.getPassportSeries()).orElseThrow(() ->
                new NotValidData("Серия паспорта не может быть нулевым")));
        return passport;
    }

    @Mappings(value = {
            @Mapping(source = "fRD.passportIssueBranch", target = "passportIssueBranch"),
            @Mapping(source = "fRD.passportIssueDate", target = "passportIssueDate"),
            @Mapping(source = "cD.client.passport.number", target = "passportNumber"),
            @Mapping(source = "cD.client.passport.series", target = "passportSeries"),
            @Mapping(source = "cD.client.firstName", target = "firstName"),
            @Mapping(source = "cD.client.lastName", target = "lastName"),
            @Mapping(source = "cD.client.middleName", target = "middleName"),
            @Mapping(source = "fRD.employment", target = "employment"),
            @Mapping(source = "cD.client.birthdate", target = "birthdate"),
            @Mapping(source = "cD.appliedOffer.isInsuranceEnabled", target = "isInsuranceEnabled"),
            @Mapping(source = "cD.appliedOffer.isSalaryClient", target = "isSalaryClient"),
            @Mapping(source = "cD.appliedOffer.totalAmount", target = "amount"), //TODO Выяснить какая сумма идет в amount
            @Mapping(source = "cD.appliedOffer.term", target = "term"),
            @Mapping(source = "fRD.gender", target = "gender"),
            @Mapping(source = "fRD.maritalStatus", target = "maritalStatus"),
            @Mapping(source = "fRD.dependentAmount", target = "dependentAmount"),
            @Mapping(source = "fRD.accountNumber", target = "accountNumber")})
    ScoringDataDto extractScoringDataDtoFromFinishRegistrationDtoAndStatementDto(FinishRegistrationRequestDto fRD, StatementDto cD);
}
