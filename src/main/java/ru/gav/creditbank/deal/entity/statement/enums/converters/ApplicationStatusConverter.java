package ru.gav.creditbank.deal.entity.statement.enums.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gav.creditbank.deal.entity.statement.enums.ApplicationStatus;
import ru.gav.creditbank.deal.exception.supplier.ExceptionSupplier;

import java.util.Arrays;
import java.util.Optional;

@Converter
@RequiredArgsConstructor
@Component
public class ApplicationStatusConverter implements AttributeConverter<ApplicationStatus, String> {

    private final ExceptionSupplier exceptionSupplier;

    @Override
    public String convertToDatabaseColumn(ApplicationStatus applicationStatus) {
        return Optional.ofNullable(applicationStatus).map(Enum::name).orElse(null);
    }

    @Override
    public ApplicationStatus convertToEntityAttribute(String s) {
        if (Optional.ofNullable(s).isPresent()) {
            return Arrays.
                    stream(ApplicationStatus.values()).
                    filter(v -> v.name().equals(s)).
                    findFirst().
                    orElseThrow(exceptionSupplier.noSuchEnumValueSupplier(ApplicationStatus.class, s));
        }
        return null;
    }
}
