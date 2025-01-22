package ru.gav.creditbank.deal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.gav.creditbank.deal.service.KafkaService;
import ru.gav.deal.model.EmailMessageDto;

@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, EmailMessageDto> kafkaTemplate;

    @Override
    public void send(EmailMessageDto emailMessageDto) {
        kafkaTemplate.send(emailMessageDto.getTheme().getValue(), emailMessageDto);
    }
}
