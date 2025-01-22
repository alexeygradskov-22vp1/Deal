package ru.gav.creditbank.deal.service;

import ru.gav.creditbank.deal.dto.ClientDto;

import java.util.UUID;

public interface ClientService {
    ClientDto save(ClientDto clientDto);
}
