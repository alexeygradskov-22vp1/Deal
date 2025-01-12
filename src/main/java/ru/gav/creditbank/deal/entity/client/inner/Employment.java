package ru.gav.creditbank.deal.entity.client.inner;

import ru.gav.creditbank.deal.entity.client.inner.enums.EmploymentPosition;
import ru.gav.creditbank.deal.entity.client.inner.enums.EmploymentStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class Employment implements Serializable {

    private UUID employment;

    private EmploymentStatus status;

    private String employerInn;

    private BigDecimal salary;

    private EmploymentPosition position;

    private Integer workExperienceTotal;

    private Integer workExperienceCurrent;
}
