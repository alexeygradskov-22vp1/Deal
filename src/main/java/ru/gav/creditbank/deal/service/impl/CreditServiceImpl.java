package ru.gav.creditbank.deal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gav.creditbank.deal.dao.CreditDao;
import ru.gav.creditbank.deal.dto.CreditDto;
import ru.gav.creditbank.deal.entity.credit.Credit;
import ru.gav.creditbank.deal.mappers.CreditMapper;
import ru.gav.creditbank.deal.service.CreditService;

@RequiredArgsConstructor
@Service
public class CreditServiceImpl implements CreditService {
    private final CreditMapper creditMapper;
    private final CreditDao creditDao;

    @Override
    public CreditDto save(CreditDto creditDto) {
        Credit credit = creditMapper.mapDtoToEntity(creditDto);
        return creditMapper.mapEntityToDto(creditDao.save(credit));
    }
}
