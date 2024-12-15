package ru.gav.creditbank.deal.entity.client.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passport implements Serializable {
    private UUID passport;

    private String series;

    private String number;

    private String issueBranch;

    private LocalDate issueDate;
}
