package ru.gav.creditbank.deal.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gav.creditbank.deal.dao.ClientDao;
import ru.gav.creditbank.deal.entity.client.Client;
import ru.gav.creditbank.deal.exception.supplier.ExceptionSupplier;
import ru.gav.creditbank.deal.logic.ClientRepository;

@RequiredArgsConstructor
@Service
public class ClientDaoImpl implements ClientDao {

    private final ClientRepository clientRepository;
    private final ExceptionSupplier exceptionSupplier;

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        clientRepository.findById(client.getClientId()).ifPresentOrElse(
                (x) -> clientRepository.save(client),
                () -> exceptionSupplier.noSuchElementInDatabaseExceptionSupplier(client.getClientId().toString()));
        return client;
    }

}
