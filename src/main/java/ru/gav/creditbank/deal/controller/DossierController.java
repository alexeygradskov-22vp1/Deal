package ru.gav.creditbank.deal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.gav.creditbank.deal.service.DossierService;
import ru.gav.deal.DossierApi;
import ru.gav.deal.model.EmailMessageDto;
import ru.gav.deal.model.ThemeEnumDto;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DossierController implements DossierApi {
    private final DossierService dossierService;

    @Override
    public ResponseEntity<Void> code(UUID statementId) {
        dossierService.sendMessage(ThemeEnumDto.SEN_SES, statementId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> send(UUID statementId) {
        dossierService.sendMessage(ThemeEnumDto.SEND_DOCUMENTS, statementId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> sign(UUID statementId) {
        dossierService.sendMessage(ThemeEnumDto.CREDIT_ISSUED, statementId);
        return ResponseEntity.ok().build();
    }
}
