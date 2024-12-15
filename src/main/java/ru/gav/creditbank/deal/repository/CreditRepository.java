package ru.gav.creditbank.deal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gav.creditbank.deal.entity.credit.Credit;

import java.util.UUID;

public interface CreditRepository extends JpaRepository<Credit, UUID> {
}