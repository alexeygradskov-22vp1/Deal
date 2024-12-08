package ru.gav.creditbank.deal.exception.supplier;

import org.springframework.stereotype.Component;
import ru.gav.creditbank.deal.exception.NoSuchEnumValue;

import java.util.function.Supplier;

@Component
public class ExceptionSupplier {

    public Supplier<NoSuchEnumValue> noSuchEnumValueSupplier(Class e, String value){
        return ()->new NoSuchEnumValue( String.format(
                        "Неверное значение при конвертации значений %s класса со значением %s",
                        e.getSimpleName(),
                        value));
    }
}
