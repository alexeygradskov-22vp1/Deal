package ru.gav.creditbank.deal.entity.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.gav.creditbank.deal.entity.client.enums.Gender;
import ru.gav.creditbank.deal.entity.client.enums.MaritalStatus;
import ru.gav.creditbank.deal.entity.client.enums.converters.GenderConverter;
import ru.gav.creditbank.deal.entity.client.enums.converters.MaritalStatusConverter;
import ru.gav.creditbank.deal.entity.client.inner.Employment;
import ru.gav.creditbank.deal.entity.client.inner.Passport;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @Column(name = "client_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID clientId;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "gender", nullable = false)
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @NotNull
    @Column(name = "marital_status", nullable = false)
    @Convert(converter = MaritalStatusConverter.class)
    private MaritalStatus maritalStatus;

    @NotNull
    @Column(name = "dependent_amount", nullable = false)
    private Integer dependentAmount;

    @JdbcTypeCode(SqlTypes.JSON)
    private Passport passport;

    @JdbcTypeCode(SqlTypes.JSON)
    private Employment employment;

    @NotNull
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

}
