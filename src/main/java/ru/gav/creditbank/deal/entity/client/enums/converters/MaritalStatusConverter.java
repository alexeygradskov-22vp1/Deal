package ru.gav.creditbank.deal.entity.client.enums.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import ru.gav.creditbank.deal.entity.client.enums.MaritalStatus;
import ru.gav.creditbank.deal.exception.supplier.ExceptionSupplier;

import java.util.Arrays;

@Converter
@RequiredArgsConstructor
public class MaritalStatusConverter implements AttributeConverter<MaritalStatus, String> {

    private final ExceptionSupplier exceptionSupplier;

    @Override
    public String convertToDatabaseColumn(MaritalStatus maritalStatus) {
        return maritalStatus.name();
    }

    @Override
    public MaritalStatus convertToEntityAttribute(String s) {
        return Arrays.
                stream(MaritalStatus.values()).
                filter(v->v.name().equals(s)).
                findFirst().
                orElseThrow(exceptionSupplier.noSuchEnumValueSupplier(MaritalStatus.class, s));
    }
}
