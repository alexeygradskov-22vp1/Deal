package ru.gav.creditbank.deal.entity.client;

import jakarta.persistence.*;
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
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID clientId;
    
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Column(name = "marital_status")
    @Convert(converter = MaritalStatusConverter.class)
    private MaritalStatus maritalStatus;

    @Column(name = "dependent_amount")
    private Integer dependentAmount;

    @JdbcTypeCode(SqlTypes.JSON)
    private Passport passport;

    @JdbcTypeCode(SqlTypes.JSON)
    private Employment employment;

    @Column(name = "account_number")
    private String accountNumber;

}
