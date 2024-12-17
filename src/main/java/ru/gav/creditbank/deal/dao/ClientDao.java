package ru.gav.creditbank.deal.dao;

import ru.gav.creditbank.deal.entity.client.Client;

public interface ClientDao {
    Client save(Client client);
    Client update(Client client);
}
