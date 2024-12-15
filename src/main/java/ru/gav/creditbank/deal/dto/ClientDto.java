package ru.gav.creditbank.deal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;
import ru.gav.creditbank.deal.entity.client.enums.Gender;
import ru.gav.creditbank.deal.entity.client.enums.MaritalStatus;
import ru.gav.creditbank.deal.entity.client.inner.Employment;
import ru.gav.creditbank.deal.entity.client.inner.Passport;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClientDto {
    private UUID clientId;

    private JsonNullable<String> lastName;

    private JsonNullable<String> firstName;

    private JsonNullable<String> middleName;

    private LocalDate birthdate;

    private JsonNullable<String> email;

    private Gender gender;

    private MaritalStatus maritalStatus;

    private Integer dependentAmount;

    private Passport passport;

    private Employment employment;

    private JsonNullable<String> accountNumber;
}
