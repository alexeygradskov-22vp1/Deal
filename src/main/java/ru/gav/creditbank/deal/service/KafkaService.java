package ru.gav.creditbank.deal.service;

import ru.gav.deal.model.EmailMessageDto;

public interface KafkaService {
    void send(EmailMessageDto emailMessageDto);

}
