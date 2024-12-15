package ru.gav.creditbank.deal.entity.statement.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gav.creditbank.deal.entity.statement.inner.enums.ChangeType;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusHistory implements Serializable {

    private String status;

    private Instant time;

    private ChangeType changeType;
}
