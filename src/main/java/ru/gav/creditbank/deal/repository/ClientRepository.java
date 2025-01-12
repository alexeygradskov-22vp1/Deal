package ru.gav.creditbank.deal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gav.creditbank.deal.entity.client.Client;

import java.util.UUID;
@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
}