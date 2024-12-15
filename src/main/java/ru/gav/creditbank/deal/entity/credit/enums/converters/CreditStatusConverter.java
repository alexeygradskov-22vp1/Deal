package ru.gav.creditbank.deal.entity.credit.enums.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gav.creditbank.deal.entity.credit.enums.CreditStatus;
import ru.gav.creditbank.deal.exception.supplier.ExceptionSupplier;

import java.util.Arrays;
import java.util.Optional;

@Converter
@RequiredArgsConstructor
@Component
public class CreditStatusConverter implements AttributeConverter<CreditStatus, String> {

    private final ExceptionSupplier exceptionSupplier;

    @Override
    public String convertToDatabaseColumn(CreditStatus creditStatus) {
        return Optional.ofNullable(creditStatus).map(Enum::name).orElse(null);
    }

    @Override
    public CreditStatus convertToEntityAttribute(String s) {
        if (Optional.ofNullable(s).isPresent()) {
            return Arrays.
                    stream(CreditStatus.values()).
                    filter(v -> v.name().equals(s)).
                    findFirst().
                    orElseThrow(exceptionSupplier.noSuchEnumValueSupplier(CreditStatus.class, s));
        }
        return null;
    }
}
