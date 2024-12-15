package ru.gav.creditbank.deal.entity.client.inner.enums.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gav.creditbank.deal.entity.client.inner.enums.EmploymentPosition;
import ru.gav.creditbank.deal.exception.supplier.ExceptionSupplier;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Converter
@Component
public class EmploymentPositionConverter implements AttributeConverter<EmploymentPosition, String> {

    private final ExceptionSupplier exceptionSupplier;

    @Override
    public String convertToDatabaseColumn(EmploymentPosition employmentPosition) {
        return Optional.ofNullable(employmentPosition).map(Enum::name).orElse(null);
    }

    @Override
    public EmploymentPosition convertToEntityAttribute(String s) {
        if (Optional.ofNullable(s).isPresent()) {
            return Arrays.
                    stream(EmploymentPosition.values()).
                    filter(v -> v.name().equals(s)).
                    findFirst().
                    orElseThrow(exceptionSupplier.noSuchEnumValueSupplier(EmploymentPosition.class, s));
        }
        return null;
    }
}
