package ru.gav.creditbank.deal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gav.creditbank.deal.entity.credit.enums.CreditStatus;
import ru.gav.creditbank.deal.entity.credit.inner.PaymentScheduleElement;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreditDto {
    private UUID creditId;

    private BigDecimal amount;

    private Integer term;

    private BigDecimal monthlyPayment;

    private BigDecimal rate;

    private BigDecimal psk;

    private List<PaymentScheduleElement> paymentSchedule;

    private Boolean isInsuranceEnabled;

    private Boolean isSalaryClient;

    private CreditStatus creditStatus;
}
