package ru.gav.creditbank.deal.utils;

import ru.gav.deal.model.EmailMessageDto;
import ru.gav.deal.model.ThemeEnumDto;

import java.util.UUID;

public interface MessageBuilder {

    EmailMessageDto build(String address, ThemeEnumDto theme, UUID statementId, String text);
}
