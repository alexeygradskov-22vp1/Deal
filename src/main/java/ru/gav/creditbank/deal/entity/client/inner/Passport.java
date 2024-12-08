package ru.gav.creditbank.deal.entity.client.inner;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Passport implements Serializable {
    private UUID passport;

    private String series;

    private String number;

    private String issueBranch;

    private LocalDate issueDate;
}
