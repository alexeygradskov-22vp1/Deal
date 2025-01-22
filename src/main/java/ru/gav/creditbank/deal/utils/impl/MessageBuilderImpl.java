package ru.gav.creditbank.deal.utils.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gav.creditbank.deal.utils.MessageBuilder;
import ru.gav.deal.model.EmailMessageDto;
import ru.gav.deal.model.ThemeEnumDto;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class MessageBuilderImpl implements MessageBuilder {
    @Override
    public EmailMessageDto build(String address, ThemeEnumDto theme, UUID statementId, String text) {
        return new EmailMessageDto()
                .address(address)
                .theme(theme)
                .statementId(statementId)
                .text(text);
    }
}
