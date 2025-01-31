package ru.gav.creditbank.deal.logic.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gav.creditbank.deal.dto.StatementDto;
import ru.gav.creditbank.deal.logic.AdminService;
import ru.gav.creditbank.deal.service.StatementService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final StatementService statementService;

    @Override
    public StatementDto getById(UUID statementId) {
        return statementService.getOne(statementId);
    }

    @Override
    public List<StatementDto> getAllStatements() {
        return statementService.getAllStatements();
    }
}
