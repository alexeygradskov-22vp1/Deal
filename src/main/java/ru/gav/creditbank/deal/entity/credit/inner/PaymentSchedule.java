package ru.gav.creditbank.deal.entity.credit.inner;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSchedule implements Serializable {

    private List<PaymentScheduleElement> paymentScheduleEls;
}
