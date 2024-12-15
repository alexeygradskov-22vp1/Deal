package ru.gav.creditbank.deal.logic;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gav.creditbank.deal.entity.client.Client;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}