package ru.gav.creditbank.deal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;
import ru.gav.creditbank.deal.entity.statement.enums.ApplicationStatus;
import ru.gav.creditbank.deal.entity.statement.inner.StatusHistory;
import ru.gav.deal.model.LoanOfferDto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StatementDto {

    private UUID statementId;

    private ClientDto client;

    private CreditDto credit;

    private ApplicationStatus status;

    private Instant creationDate;

    private LoanOfferDto appliedOffer;

    private Instant signDate;

    private JsonNullable<String> sesCode;

    private List<StatusHistory> statusHistory;
}
