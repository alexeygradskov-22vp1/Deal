package ru.gav.creditbank.deal.dao;

import ru.gav.creditbank.deal.entity.statement.Statement;

import java.util.List;
import java.util.UUID;

public interface StatementDao {
    Statement save(Statement statement);
    Statement update(Statement statement);
    Statement getOne(UUID statementUuid);
    List<Statement> getAllStatements();
}
