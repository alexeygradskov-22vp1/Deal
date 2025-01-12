package ru.gav.creditbank.deal.entity.client.enums.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gav.creditbank.deal.entity.client.enums.MaritalStatus;
import ru.gav.creditbank.deal.exception.supplier.ExceptionSupplier;

import java.util.Arrays;
import java.util.Optional;

@Converter
@RequiredArgsConstructor
@Component
public class MaritalStatusConverter implements AttributeConverter<MaritalStatus, String> {

    private final ExceptionSupplier exceptionSupplier;

    @Override
    public String convertToDatabaseColumn(MaritalStatus maritalStatus) {
        return Optional.ofNullable(maritalStatus).map(Enum::name).orElse(null);
    }

    @Override
    public MaritalStatus convertToEntityAttribute(String s) {
        if (Optional.ofNullable(s).isPresent()) {
            return Arrays.
                    stream(MaritalStatus.values()).
                    filter(v -> v.name().equals(s)).
                    findFirst().
                    orElseThrow(exceptionSupplier.noSuchEnumValueSupplier(MaritalStatus.class, s));
        }
        return null;
    }
}
