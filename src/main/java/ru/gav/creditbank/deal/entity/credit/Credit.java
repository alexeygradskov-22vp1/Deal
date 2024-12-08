package ru.gav.creditbank.deal.entity.credit;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.gav.creditbank.deal.entity.credit.enums.CreditStatus;
import ru.gav.creditbank.deal.entity.credit.enums.converters.CreditStatusConverter;
import ru.gav.creditbank.deal.entity.credit.inner.PaymentSchedule;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "credit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "credit_id", nullable = false)
    private UUID creditId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Integer term;

    @NotNull
    private BigDecimal monthlyPayment;

    @NotNull
    private BigDecimal rate;

    @NotNull
    private BigDecimal psk;

    @NotNull
    @JdbcTypeCode(SqlTypes.JSON)
    private PaymentSchedule paymentSchedule;

    @NotNull
    private Boolean insuranceEnabled;

    @NotNull
    private Boolean salaryClient;

    @NotNull
    @Convert(converter = CreditStatusConverter.class)
    private CreditStatus creditStatus;

}
