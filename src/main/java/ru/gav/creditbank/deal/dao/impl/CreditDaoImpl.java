package ru.gav.creditbank.deal.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gav.creditbank.deal.dao.CreditDao;
import ru.gav.creditbank.deal.entity.credit.Credit;
import ru.gav.creditbank.deal.exception.supplier.ExceptionSupplier;
import ru.gav.creditbank.deal.repository.CreditRepository;

@RequiredArgsConstructor
@Service
public class CreditDaoImpl implements CreditDao {

    private final CreditRepository creditRepository;
    private final ExceptionSupplier exceptionSupplier;


    @Override
    public Credit save(Credit credit) {
        return creditRepository.save(credit);
    }

    @Override
    public Credit update(Credit credit) {
        creditRepository.findById(credit.getCreditId()).orElseThrow(exceptionSupplier.noSuchElementInDatabaseExceptionSupplier(credit.getCreditId().toString()));
        creditRepository.save(credit);
        return credit;
    }
}
