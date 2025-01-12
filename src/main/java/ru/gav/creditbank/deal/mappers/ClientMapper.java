package ru.gav.creditbank.deal.mappers;

import org.mapstruct.*;
import ru.gav.creditbank.deal.dto.ClientDto;
import ru.gav.creditbank.deal.entity.client.Client;

@Mapper(uses = { JsonNullableMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    Client mapDtoToEntity(ClientDto clientDto);

    ClientDto mapEntityToDto(Client client);

    @InheritConfiguration
    Client update(ClientDto clientDto, @MappingTarget Client client);
}
