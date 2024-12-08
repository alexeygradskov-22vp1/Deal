package ru.gav.creditbank.deal.entity.credit.inner;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentScheduleElement {

    private Integer number;

    private LocalDate date;

    private BigDecimal totalPayment;

    private BigDecimal interestPayment;

    private BigDecimal debtPayment;

    private BigDecimal remainingDebt;
}
