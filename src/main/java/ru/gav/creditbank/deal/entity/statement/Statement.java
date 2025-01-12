package ru.gav.creditbank.deal.entity.statement;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.gav.creditbank.deal.entity.client.Client;
import ru.gav.creditbank.deal.entity.credit.Credit;
import ru.gav.creditbank.deal.entity.statement.enums.ApplicationStatus;
import ru.gav.creditbank.deal.entity.statement.enums.converters.ApplicationStatusConverter;
import ru.gav.creditbank.deal.entity.statement.inner.Offer;
import ru.gav.creditbank.deal.entity.statement.inner.StatusHistory;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "statement")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "statement_id")
    private UUID statementId;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
    private Credit credit;

    @Convert(converter = ApplicationStatusConverter.class)
    private ApplicationStatus status;

    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Instant creationDate;

    @JdbcTypeCode(SqlTypes.JSON)
    private Offer appliedOffer;

    private Instant signDate;

    private String sesCode;
    
    @JdbcTypeCode(SqlTypes.JSON)
    private List<StatusHistory> statusHistory;

}
