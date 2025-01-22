package ru.gav.creditbank.deal.service;

import ru.gav.deal.model.ThemeEnumDto;

import java.util.UUID;

public interface DossierService {
    void sendMessage(ThemeEnumDto theme, UUID statementId);
}
