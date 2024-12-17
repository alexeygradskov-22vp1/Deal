package ru.gav.creditbank.deal.mappers;

import org.mapstruct.*;
import ru.gav.creditbank.deal.dto.CreditDto;
import ru.gav.creditbank.deal.entity.credit.Credit;

@Mapper(uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreditMapper {

    @Mappings(value = {
            @Mapping(source = "isInsuranceEnabled", target = "insuranceEnabled"),
            @Mapping(source = "isSalaryClient", target = "salaryClient")
    })
    Credit mapDtoToEntity(CreditDto creditDto);

    @Mappings(value = {
            @Mapping(source = "insuranceEnabled", target = "isInsuranceEnabled"),
            @Mapping(source = "salaryClient", target = "isSalaryClient")
    })
    CreditDto mapEntityToDto(Credit credit);

    @InheritConfiguration
    Credit update(CreditDto creditDto, @MappingTarget Credit credit);
}


