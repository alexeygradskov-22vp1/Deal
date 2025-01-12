package ru.gav.creditbank.deal.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gav.creditbank.deal.dao.StatementDao;
import ru.gav.creditbank.deal.entity.statement.Statement;
import ru.gav.creditbank.deal.exception.supplier.ExceptionSupplier;
import ru.gav.creditbank.deal.repository.StatementRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StatementDaoImpl implements StatementDao {
    private final StatementRepository statementRepository;
    private final ExceptionSupplier exceptionSupplier;

    @Override
    public Statement save(Statement statement) {
        return statementRepository.save(statement);
    }

    @Override
    public Statement update(Statement statement) {
        statementRepository.findById(statement.getStatementId()).orElseThrow(exceptionSupplier.noSuchElementInDatabaseExceptionSupplier(statement.getStatementId().toString()));
        statementRepository.save(statement);
        return statement;
    }

    @Override
    public Statement getOne(UUID statementUuid) {
        return statementRepository.findById(statementUuid).
                orElseThrow(exceptionSupplier.noSuchElementInDatabaseExceptionSupplier(statementUuid.toString()));
    }
}
