package ru.gav.creditbank.deal.entity.statement.inner;

import ru.gav.creditbank.deal.entity.statement.inner.enums.ChangeType;

import java.io.Serializable;
import java.time.Instant;

public class StatusHistory implements Serializable {

    private String status;

    private Instant time;

    private ChangeType changeType;
}
