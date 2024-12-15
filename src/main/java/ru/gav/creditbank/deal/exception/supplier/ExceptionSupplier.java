package ru.gav.creditbank.deal.exception.supplier;

import org.springframework.stereotype.Component;
import ru.gav.creditbank.deal.exception.*;

import java.util.function.Supplier;

@Component
public class ExceptionSupplier {

    public Supplier<NoSuchEnumValue> noSuchEnumValueSupplier(Class e, String value) {
        return () -> new NoSuchEnumValue(String.format(
                "Неверное значение при конвертации значений %s класса со значением %s",
                e.getSimpleName(),
                value));
    }

    public Supplier<WebClientReturnNullException> webClientReturnNullExceptionSupplier(String endpoint) {
        return () -> new WebClientReturnNullException(
                String.format("Вернулся null при запросе к другому МКС по адресу:%s", endpoint));
    }

    public Supplier<NoSuchElementInDatabaseException> noSuchElementInDatabaseExceptionSupplier(String id) {
        return () -> new NoSuchElementInDatabaseException(String.format("В базе данных нет такого элемента с id: %s", id));
    }

    public Supplier<ScoringDataException> scoringDataExceptionSupplier(String message) {
        return () -> new ScoringDataException("Ошибка при скоринге данных: " + message);
    }

    public Supplier<CalculateLoanOffersException> calculateLoanOffersExceptionSupplier(String message){
        return ()->new CalculateLoanOffersException("Произошла ошибка при создании предложений о кредите: "+message);
    }
}
