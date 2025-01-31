package ru.gav.creditbank.deal.mappers;

import org.mapstruct.*;
import ru.gav.creditbank.deal.dto.StatementDto;
import ru.gav.creditbank.deal.entity.statement.Statement;

import java.util.List;

@Mapper(uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StatementMapper {
    Statement mapDtoToEntity(StatementDto statementDto);

    StatementDto mapEntityToDto(Statement statement);

    @InheritConfiguration
    Statement update(StatementDto statementDto, @MappingTarget Statement statement);

    List<StatementDto> mapEntityListToDtoList(List<Statement> statements);
}
