package ru.gav.creditbank.deal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gav.creditbank.deal.dto.ClientDto;
import ru.gav.creditbank.deal.dto.StatementDto;
import ru.gav.creditbank.deal.service.DossierService;
import ru.gav.creditbank.deal.service.KafkaService;
import ru.gav.creditbank.deal.service.StatementService;
import ru.gav.creditbank.deal.utils.MessageBuilder;
import ru.gav.deal.model.EmailMessageDto;
import ru.gav.deal.model.ThemeEnumDto;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DossierServiceImpl implements DossierService {

    private static final Map<ThemeEnumDto, String> themeMessages =
            Map.of(
                    ThemeEnumDto.FINISH_REGISTRATION, "Завершите оформление",
                    ThemeEnumDto.CREATE_DOCUMENTS, "Перейти к оформлению документов",
                    ThemeEnumDto.SEND_DOCUMENTS, "Отправка документов",
                    ThemeEnumDto.SEN_SES, "Отправка SES",
                    ThemeEnumDto.CREDIT_ISSUED, "Кредит одобрен",
                    ThemeEnumDto.STATEMENT_DENIED, "Заявка отклонена");
    private final StatementService statementService;
    private final MessageBuilder messageBuilder;
    private final KafkaService kafkaService;

    @Override
    public void sendMessage(ThemeEnumDto theme, UUID statementId) {
        StatementDto statementDto = statementService.getOne(statementId);
        EmailMessageDto emailMessageDto =
                messageBuilder.build(
                        statementDto.getClient().getEmail().get(),
                        theme,
                        statementId,
                        themeMessages.get(theme));
        kafkaService.send(emailMessageDto);
    }
}
