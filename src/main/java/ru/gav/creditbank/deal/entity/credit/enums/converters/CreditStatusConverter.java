package ru.gav.creditbank.deal.entity.credit.enums.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.gav.creditbank.deal.entity.client.inner.enums.EmploymentStatus;
import ru.gav.creditbank.deal.entity.credit.enums.CreditStatus;
import ru.gav.creditbank.deal.exception.supplier.ExceptionSupplier;

import java.util.Arrays;

@Converter
public class CreditStatusConverter implements AttributeConverter<CreditStatus, String> {

    private ExceptionSupplier exceptionSupplier;

    @Override
    public String convertToDatabaseColumn(CreditStatus creditStatus) {
        return creditStatus.name();
    }

    @Override
    public CreditStatus convertToEntityAttribute(String s) {
        return Arrays.
                stream(CreditStatus.values()).
                filter(v -> v.name().equals(s)).
                findFirst().
                orElseThrow(exceptionSupplier.noSuchEnumValueSupplier(CreditStatus.class, s));
    }
}
