package ru.gav.creditbank.deal.logic;

import ru.gav.deal.model.FinishRegistrationRequestDto;
import ru.gav.deal.model.LoanOfferDto;
import ru.gav.deal.model.LoanStatementRequestDto;

import java.util.List;

public interface DealService {
    List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatementRequestDto);

    void selectOffer(LoanOfferDto loanOfferDto);

    void finishRegistration(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId);
}
