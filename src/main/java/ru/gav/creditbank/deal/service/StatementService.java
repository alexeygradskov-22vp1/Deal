package ru.gav.creditbank.deal.service;

import ru.gav.creditbank.deal.dto.StatementDto;

import java.util.List;
import java.util.UUID;

public interface StatementService {
    StatementDto save(StatementDto statementDto);

    void update(StatementDto statementDto);

    StatementDto getOne(UUID statementUuid);

    List<StatementDto> getAllStatements();
}
