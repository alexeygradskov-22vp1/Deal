package ru.gav.creditbank.deal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gav.creditbank.deal.dao.StatementDao;
import ru.gav.creditbank.deal.dto.StatementDto;
import ru.gav.creditbank.deal.entity.statement.Statement;
import ru.gav.creditbank.deal.mappers.StatementMapper;
import ru.gav.creditbank.deal.service.StatementService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {
    private final StatementDao statementDao;
    private final StatementMapper statementMapper;

    @Override
    public StatementDto save(StatementDto statementDto) {
        Statement statement = statementMapper.mapDtoToEntity(statementDto);
        return statementMapper.mapEntityToDto(statementDao.save(statement));
    }

    @Override
    public void update(StatementDto statementDto) {
        Statement statement = statementDao.getOne(statementDto.getStatementId());
        statement = statementMapper.mapDtoToEntity(statementDto);
        statementDao.update(statement);
    }

    @Override
    public StatementDto getOne(UUID statementUuid) {
        return statementMapper.mapEntityToDto(statementDao.getOne(statementUuid));
    }
}
