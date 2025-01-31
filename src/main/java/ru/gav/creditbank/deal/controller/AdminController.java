package ru.gav.creditbank.deal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.gav.creditbank.deal.dto.StatementDto;
import ru.gav.creditbank.deal.logic.AdminService;
import ru.gav.deal.AdminApi;

import java.util.List;
import java.util.UUID;
@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {
    private final AdminService adminService;

    @Override
    public ResponseEntity<List<StatementDto>> dealAdminStatementGet() {
        return ResponseEntity.ok(adminService.getAllStatements());
    }

    @Override
    public ResponseEntity<StatementDto> dealAdminStatementStatementIdGet(UUID statementId) {
        return ResponseEntity.ok(adminService.getById(statementId));
    }
}
