package ru.gav.creditbank.deal.entity.credit;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.gav.creditbank.deal.entity.credit.enums.CreditStatus;
import ru.gav.creditbank.deal.entity.credit.enums.converters.CreditStatusConverter;
import ru.gav.creditbank.deal.entity.credit.inner.PaymentScheduleElement;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "credit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "credit_id")
    private UUID creditId;

    private BigDecimal amount;

    private Integer term;

    private BigDecimal monthlyPayment;

    private BigDecimal rate;

    private BigDecimal psk;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<PaymentScheduleElement> paymentSchedule;

    private Boolean insuranceEnabled;

    private Boolean salaryClient;

    @Convert(converter = CreditStatusConverter.class)
    private CreditStatus creditStatus;

}
