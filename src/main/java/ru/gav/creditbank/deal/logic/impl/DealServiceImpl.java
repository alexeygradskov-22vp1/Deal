package ru.gav.creditbank.deal.logic.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gav.creditbank.deal.dto.ClientDto;
import ru.gav.creditbank.deal.dto.CreditDto;
import ru.gav.creditbank.deal.dto.StatementDto;
import ru.gav.creditbank.deal.entity.credit.enums.CreditStatus;
import ru.gav.creditbank.deal.entity.statement.enums.ApplicationStatus;
import ru.gav.creditbank.deal.entity.statement.inner.StatusHistory;
import ru.gav.creditbank.deal.entity.statement.inner.enums.ChangeType;
import ru.gav.creditbank.deal.exception.supplier.ExceptionSupplier;
import ru.gav.creditbank.deal.logic.DealService;
import ru.gav.creditbank.deal.mappers.Extractor;
import ru.gav.creditbank.deal.service.ClientService;
import ru.gav.creditbank.deal.service.CreditService;
import ru.gav.creditbank.deal.service.StatementService;
import ru.gav.deal.model.FinishRegistrationRequestDto;
import ru.gav.deal.model.LoanOfferDto;
import ru.gav.deal.model.LoanStatementRequestDto;
import ru.gav.deal.model.ScoringDataDto;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {
    private final StatementService statementService;
    private final ClientService clientService;
    private final CreditService creditService;
    private final Extractor extractor;
    @Qualifier("calculatorWebClient")
    private final WebClient webClient;
    private final ExceptionSupplier exceptionSupplier;

    @Value("${links.urls.calculator/offers}")
    private String offersEndpoint;

    @Value("${links.urls.calculator/calc}")
    private String calculatorEndpoint;


    @Override
    @Transactional
    public List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
        ClientDto clientDto = extractor.extractClientFromLoanStatement(loanStatementRequestDto);
        StatementDto statementDto = StatementDto.builder().
                statusHistory(List.of(
                        new StatusHistory(ApplicationStatus.PREAPPROVAL.name(),
                                Instant.now(),
                                ChangeType.AUTOMATIC))).build();
        clientDto = clientService.save(clientDto);
        statementDto.setClient(clientDto);
        statementDto = statementService.save(statementDto);
        StatementDto finalStatementDto = statementDto;
        Stream<LoanOfferDto> loanOfferDtoStream = webClient.post().
                uri(uriBuilder -> uriBuilder.path(offersEndpoint).build())
                .body(BodyInserters.fromValue(loanStatementRequestDto)).
                retrieve().bodyToFlux(LoanOfferDto.class).
                doOnError(e->exceptionSupplier.calculateLoanOffersExceptionSupplier(e.getMessage())).
                filter(Objects::nonNull).map(x -> x.statementId(finalStatementDto.getStatementId())).
                toStream();
        return loanOfferDtoStream.toList();
    }

    @Override
    @Transactional
    public void selectOffer(LoanOfferDto loanOfferDto) {
        StatementDto statementDto = statementService.getOne(loanOfferDto.getStatementId());
        statementDto.setStatus(ApplicationStatus.APPROVED);
        statementDto.getStatusHistory().add(new StatusHistory(
                ApplicationStatus.APPROVED.name(),
                Instant.now(),
                ChangeType.AUTOMATIC));
        statementDto.setAppliedOffer(loanOfferDto);
        statementService.update(statementDto);
    }

    @Override
    @Transactional
    public void finishRegistration(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId) {
        StatementDto statementDto = statementService.getOne(UUID.fromString(statementId));
        ScoringDataDto scoringDataDto =
                extractor.extractScoringDataDtoFromFinishRegistrationDtoAndStatementDto(
                        finishRegistrationRequestDto,
                        statementDto);
        Optional<CreditDto> optionalCreditDto = Optional.ofNullable(webClient.post().
                uri(uriBuilder -> uriBuilder.path(calculatorEndpoint).build()).
                body(BodyInserters.fromValue(scoringDataDto)).
                retrieve().
                bodyToMono(CreditDto.class).
                doOnError(message->exceptionSupplier.scoringDataExceptionSupplier(message.getMessage())).
                block());
        CreditDto creditDto = optionalCreditDto.
                orElseThrow(exceptionSupplier.webClientReturnNullExceptionSupplier(calculatorEndpoint));
        creditDto.setCreditStatus(CreditStatus.CALCULATED);
        creditDto = creditService.save(creditDto);
        statementDto.setCredit(creditDto);
        statementDto.setStatus(ApplicationStatus.CC_APPROVED);
        statementDto.getStatusHistory().add(new StatusHistory(
                ApplicationStatus.CC_APPROVED.name(),
                Instant.now(),
                ChangeType.AUTOMATIC));
        statementService.save(statementDto);
    }
}
