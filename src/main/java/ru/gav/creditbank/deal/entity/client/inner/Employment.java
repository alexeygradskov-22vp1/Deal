package ru.gav.creditbank.deal.entity.client.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gav.creditbank.deal.entity.client.inner.enums.EmploymentPosition;
import ru.gav.creditbank.deal.entity.client.inner.enums.EmploymentStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employment implements Serializable {

    private UUID employment;

    private EmploymentStatus status;

    private String employerInn;

    private BigDecimal salary;

    private EmploymentPosition position;

    private Integer workExperienceTotal;

    private Integer workExperienceCurrent;
}
