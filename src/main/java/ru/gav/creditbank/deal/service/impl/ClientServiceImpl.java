package ru.gav.creditbank.deal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gav.creditbank.deal.dao.ClientDao;
import ru.gav.creditbank.deal.dto.ClientDto;
import ru.gav.creditbank.deal.entity.client.Client;
import ru.gav.creditbank.deal.mappers.ClientMapper;
import ru.gav.creditbank.deal.service.ClientService;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {
    private final ClientMapper clientMapper;
    private final ClientDao clientDao;

    @Override
    public ClientDto save(ClientDto clientDto) {
        Client client = clientMapper.mapDtoToEntity(clientDto);
        return clientMapper.mapEntityToDto(clientDao.save(client));
    }
}
