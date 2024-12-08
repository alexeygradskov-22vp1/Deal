package ru.gav.creditbank.deal.entity.statement.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import ru.gav.creditbank.deal.entity.client.inner.enums.EmploymentStatus;
import ru.gav.creditbank.deal.exception.supplier.ExceptionSupplier;

import java.util.Arrays;

@Converter
@RequiredArgsConstructor
public class ApplicationStatusConverter implements AttributeConverter<ApplicationStatus, String> {

    private ExceptionSupplier exceptionSupplier;

    @Override
    public String convertToDatabaseColumn(ApplicationStatus applicationStatus) {
        return applicationStatus.name();
    }

    @Override
    public ApplicationStatus convertToEntityAttribute(String s) {
        return Arrays.
                stream(ApplicationStatus.values()).
                filter(v -> v.name().equals(s)).
                findFirst().
                orElseThrow(exceptionSupplier.noSuchEnumValueSupplier(ApplicationStatus.class, s));
    }
}
