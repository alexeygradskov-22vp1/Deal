package ru.gav.creditbank.deal.entity.client.inner.enums.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import ru.gav.creditbank.deal.entity.client.inner.enums.EmploymentStatus;
import ru.gav.creditbank.deal.exception.supplier.ExceptionSupplier;

import java.util.Arrays;

@RequiredArgsConstructor
@Converter
public class EmploymentStatusConverter implements AttributeConverter<EmploymentStatus, String> {

    private final ExceptionSupplier exceptionSupplier;

    @Override
    public String convertToDatabaseColumn(EmploymentStatus employmentStatus) {
        return employmentStatus.name();
    }

    @Override
    public EmploymentStatus convertToEntityAttribute(String s) {
        return Arrays.
                stream(EmploymentStatus.values()).
                filter(v -> v.name().equals(s)).
                findFirst().
                orElseThrow(exceptionSupplier.noSuchEnumValueSupplier(EmploymentStatus.class, s));
    }
}
