package com.Bank.BankBackend.modules.account.interfaces.rest;

import com.Bank.BankBackend.modules.account.application.dto.request.*;
import com.Bank.BankBackend.modules.account.application.dto.response.GetClientResponse;
import com.Bank.BankBackend.modules.account.application.dto.response.GetListClientResponse;
import com.Bank.BankBackend.modules.account.application.port.in.*;

import com.Bank.BankBackend.shared.infrastructure.security.SecurityUser;
import com.Bank.BankBackend.shared.interfaces.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Endpoints de gestión de clientes: creación, consulta, activación y desactivación")
public class ClientController {

    private final CreateClientPort createClientPort;
    private final GetClientPort getClientPort;
    private final ActivateClientPort activateClientPort;
    private final DeactivateClientPort deactivateClientPort;

    @PreAuthorize("hasRole('ADVISOR')")
    @Operation(summary="Crear cliente")
    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(@AuthenticationPrincipal SecurityUser securityUser ,@Valid @RequestBody CreateClientRequest request) {
        createClientPort.create(securityUser.getUserId(),request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Cliente creado correctamente"));
    }

    @PreAuthorize("hasRole('ADVISOR')")
    @Operation(summary="Buscar clientes")
    @PostMapping("/find")
    public ResponseEntity<ApiResponse<GetClientResponse>> getClient(@Valid @RequestBody GetClientRequest request) {
        GetClientResponse response = getClientPort.getClient(request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PreAuthorize("hasRole('ADVISOR')")
    @Operation(summary="Clientes activos")
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<GetListClientResponse>>> getActiveClients() {
        List<GetListClientResponse> clients = getClientPort.getClientActive();
        return ResponseEntity.ok(ApiResponse.ok(clients));
    }

    @PreAuthorize("hasRole('ADVISOR')")
    @Operation(summary="Clientes desactivados")
    @GetMapping("/inactive")
    public ResponseEntity<ApiResponse<List<GetListClientResponse>>> getInactiveClients() {
        List<GetListClientResponse> clients = getClientPort.getClientInactive();
        return ResponseEntity.ok(ApiResponse.ok(clients));
    }

    @PreAuthorize("hasRole('ADVISOR')")
    @Operation(summary="Activar cliente")
    @PostMapping("/activate")
    public ResponseEntity<ApiResponse<String>> activate(@Valid @RequestBody ActivateClientRequest request) {
        activateClientPort.activateClient(request);
        return ResponseEntity.ok(ApiResponse.ok("Cliente activado correctamente"));
    }

    @PreAuthorize("hasRole('ADVISOR')")
    @Operation(summary="Desactivar clientes")
    @PostMapping("/deactivate")
    public ResponseEntity<ApiResponse<String>> deactivate(@Valid @RequestBody DeactivateClientRequest request) {
        deactivateClientPort.deactivateClient(request);
        return ResponseEntity.ok(ApiResponse.ok("Cliente desactivado correctamente"));
    }
}
