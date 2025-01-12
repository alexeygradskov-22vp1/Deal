package ru.gav.creditbank.deal.entity.client.enums.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gav.creditbank.deal.entity.client.enums.Gender;
import ru.gav.creditbank.deal.exception.supplier.ExceptionSupplier;

import java.util.Arrays;
import java.util.Optional;

@Converter
@RequiredArgsConstructor
@Component
public class GenderConverter implements AttributeConverter<Gender, String> {

    private final ExceptionSupplier exceptionSupplier;

    @Override
    public String convertToDatabaseColumn(Gender gender) {
        return Optional.ofNullable(gender).map(Enum::name).orElse(null);
    }

    @Override
    public Gender convertToEntityAttribute(String s) {
        if (Optional.ofNullable(s).isPresent()) {
            return Arrays.
                    stream(Gender.values()).
                    filter(v -> v.name().equals(s)).
                    findFirst().
                    orElseThrow(exceptionSupplier.noSuchEnumValueSupplier(Gender.class, s));
        }
        return null;
    }
}