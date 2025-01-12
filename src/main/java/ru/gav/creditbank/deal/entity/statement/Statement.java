package ru.gav.creditbank.deal.entity.statement;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.gav.creditbank.deal.entity.client.Client;
import ru.gav.creditbank.deal.entity.credit.Credit;
import ru.gav.creditbank.deal.entity.statement.enums.ApplicationStatus;
import ru.gav.creditbank.deal.entity.statement.enums.ApplicationStatusConverter;
import ru.gav.creditbank.deal.entity.statement.inner.Offer;
import ru.gav.creditbank.deal.entity.statement.inner.StatusHistory;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "statement")
@NoArgsConstructor
@AllArgsConstructor
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "statement_id", nullable = false)
    private UUID statementId;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    @NotNull
    private Client client;

    @OneToOne
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
    @NotNull
    private Credit credit;

    @NotNull
    @Convert(converter = ApplicationStatusConverter.class)
    private ApplicationStatus status;

    @NotNull
    @CreationTimestamp
    private Instant creationDate;

    @NotNull
    @JdbcTypeCode(SqlTypes.JSON)
    private Offer appliedOffer;

    @NotNull
    private Instant signDate;

    @NotNull
    private String sesCode;

    @NotNull
    @JdbcTypeCode(SqlTypes.JSON)
    private List<StatusHistory> statusHistory;

}
