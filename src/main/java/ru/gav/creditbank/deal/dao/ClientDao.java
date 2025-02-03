package ru.gav.creditbank.deal.dao;

import ru.gav.creditbank.deal.entity.client.Client;

import java.util.UUID;

public interface ClientDao {
    Client save(Client client);

    Client update(Client client);
}
