package ru.gav.creditbank.deal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gav.creditbank.deal.entity.statement.Statement;

import java.util.UUID;

public interface StatementRepository extends JpaRepository<Statement, UUID> {
}