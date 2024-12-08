package ru.gav.creditbank.deal.entity.statement.inner;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class Offer implements Serializable {

    private UUID statementId;

    private BigDecimal requestedAmount;

    private BigDecimal totalAmount;

    private Integer term;

    private BigDecimal monthlyPayment;

    private BigDecimal rate;

    private Boolean isInsuranceEnabled;

    private Boolean isSalaryClient;

}
