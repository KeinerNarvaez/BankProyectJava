package com.Bank.BankBackend.modules.account.interfaces.rest;

import com.Bank.BankBackend.modules.account.application.dto.request.TransferRequest;
import com.Bank.BankBackend.modules.account.application.port.in.TransferPort;

import com.Bank.BankBackend.shared.infrastructure.security.SecurityUser;
import com.Bank.BankBackend.shared.interfaces.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
@Tag(name = "Transfers", description = "Endpoints para realizar transferencias entre cuentas")
public class TransferController {

    private final TransferPort transferPort;

    @PreAuthorize("hasAnyRole('ADVISOR', 'ADMIN')")
    @Operation(summary = "Realizar una transferencia entre cuentas")
    @PostMapping
    public ResponseEntity<ApiResponse<String>> transfer(@AuthenticationPrincipal SecurityUser securityUser, @Valid @RequestBody TransferRequest request) {
        transferPort.transfer(securityUser.getUserId(),request);
        return ResponseEntity.ok(ApiResponse.ok("Transferencia realizada correctamente"));
    }
}