package ru.gav.creditbank.deal.dao;

import ru.gav.creditbank.deal.entity.credit.Credit;

public interface CreditDao {
    Credit save(Credit credit);
    Credit update(Credit credit);
}
