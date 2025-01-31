package ru.gav.creditbank.deal.logic;

import ru.gav.creditbank.deal.dto.StatementDto;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    StatementDto getById(UUID statementId);
    List<StatementDto> getAllStatements();
}
