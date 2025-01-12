package ru.gav.creditbank.deal.entity.client.inner.enums.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gav.creditbank.deal.entity.client.inner.enums.EmploymentStatus;
import ru.gav.creditbank.deal.exception.supplier.ExceptionSupplier;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Converter
@Component
public class EmploymentStatusConverter implements AttributeConverter<EmploymentStatus, String> {

    private final ExceptionSupplier exceptionSupplier;

    @Override
    public String convertToDatabaseColumn(EmploymentStatus employmentStatus) {
        return Optional.ofNullable(employmentStatus).map(Enum::name).orElse(null);
    }

    @Override
    public EmploymentStatus convertToEntityAttribute(String s) {
        if (Optional.ofNullable(s).isPresent()) {
            return Arrays.
                    stream(EmploymentStatus.values()).
                    filter(v -> v.name().equals(s)).
                    findFirst().
                    orElseThrow(exceptionSupplier.noSuchEnumValueSupplier(EmploymentStatus.class, s));
        }
        return null;
    }
}
