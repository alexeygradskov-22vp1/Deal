package ru.gav.creditbank.deal.entity.client.enums.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import ru.gav.creditbank.deal.entity.client.enums.Gender;
import ru.gav.creditbank.deal.exception.supplier.ExceptionSupplier;

import java.util.Arrays;

@Converter
@RequiredArgsConstructor
public class GenderConverter implements AttributeConverter<Gender, String> {

    private final ExceptionSupplier exceptionSupplier;

    @Override
    public String convertToDatabaseColumn(Gender gender) {
        return gender.name();
    }

    @Override
    public Gender convertToEntityAttribute(String s) {
        return Arrays.
                stream(Gender.values()).
                filter(v->v.name().equals(s)).
                findFirst().
                orElseThrow(exceptionSupplier.noSuchEnumValueSupplier(Gender.class, s));
    }
}
