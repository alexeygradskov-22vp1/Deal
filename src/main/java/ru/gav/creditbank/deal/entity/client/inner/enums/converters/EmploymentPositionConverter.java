package ru.gav.creditbank.deal.entity.client.inner.enums.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import ru.gav.creditbank.deal.entity.client.enums.Gender;
import ru.gav.creditbank.deal.entity.client.inner.enums.EmploymentPosition;
import ru.gav.creditbank.deal.exception.supplier.ExceptionSupplier;

import java.util.Arrays;

@RequiredArgsConstructor
@Converter
public class EmploymentPositionConverter implements AttributeConverter<EmploymentPosition, String> {

    private final ExceptionSupplier exceptionSupplier;

    @Override
    public String convertToDatabaseColumn(EmploymentPosition employmentPosition) {
        return employmentPosition.name();
    }

    @Override
    public EmploymentPosition convertToEntityAttribute(String s) {
        return Arrays.
                stream(EmploymentPosition.values()).
                filter(v -> v.name().equals(s)).
                findFirst().
                orElseThrow(exceptionSupplier.noSuchEnumValueSupplier(EmploymentPosition.class, s));
    }
}
