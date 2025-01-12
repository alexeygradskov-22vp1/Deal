package ru.gav.creditbank.deal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.gav.creditbank.deal.logic.DealService;
import ru.gav.deal.DealApi;
import ru.gav.deal.model.FinishRegistrationRequestDto;
import ru.gav.deal.model.LoanOfferDto;
import ru.gav.deal.model.LoanStatementRequestDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DealController implements DealApi {
    private final DealService dealService;

    @Override
    public ResponseEntity<List<LoanOfferDto>> calculateStatement(LoanStatementRequestDto loanStatementRequestDto) {
        return ResponseEntity.ok(dealService.calculateLoanOffers(loanStatementRequestDto));
    }

    @Override
    public ResponseEntity<Object> finishRegistration(UUID statementId, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        dealService.finishRegistration(finishRegistrationRequestDto, statementId.toString());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Object> selectOffer(LoanOfferDto loanOfferDto) {
        dealService.selectOffer(loanOfferDto);
        return ResponseEntity.ok().build();
    }
}
