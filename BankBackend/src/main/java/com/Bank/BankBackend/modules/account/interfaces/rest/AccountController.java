package com.Bank.BankBackend.modules.account.interfaces.rest;

import com.Bank.BankBackend.modules.account.application.dto.request.*;
import com.Bank.BankBackend.modules.account.application.dto.response.WithdrawAllResponse;
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

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Endpoints de gestión de cuentas: creación, activación, desactivación, depósitos, retiros y exención de GMF")
public class AccountController {

    private final CreateAccountPort createAccountPort;
    private final ActivateAccountPort activateAccountPort;
    private final DeactivateAccountPort deactivateAccountPort;
    private final CanceledAccountPort canceledAccountPort;
    private final DepositPort depositPort;
    private final WithdrawPort withdrawPort;
    private final WithdrawAllPort withdrawAllPort;
    private final ActivateGmfExemptionPort activateGmfExemptionPort;
    private final DeactivateGmfExemptionPort deactivateGmfExemptionPort;

    @PreAuthorize("hasRole('ADVISOR')")
    @Operation(summary="Crear cuenta")
    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(@AuthenticationPrincipal SecurityUser securityUser,@Valid @RequestBody CreateAccountRequest request) {
        createAccountPort.create(securityUser.getUserId(),request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Cuenta creada correctamente"));
    }

    @PreAuthorize("hasRole('ADVISOR')")
    @Operation(summary="Activar cuenta")
    @PostMapping("/activate")
    public ResponseEntity<ApiResponse<String>> activate(@Valid @RequestBody ActivateAccountRequest request) {
        activateAccountPort.activateAccount(request);
        return ResponseEntity.ok(ApiResponse.ok("Cuenta activada correctamente"));
    }

    @PreAuthorize("hasRole('ADVISOR')")
    @Operation(summary="Desactivar cuenta")
    @PostMapping("/deactivate")
    public ResponseEntity<ApiResponse<String>> canceled(@Valid @RequestBody DeactivateAccountRequest request) {
        deactivateAccountPort.deactivateAccount(request);
        return ResponseEntity.ok(ApiResponse.ok("Cuenta desactivada correctamente"));
    }

    @PreAuthorize("hasRole('ADVISOR')")
    @Operation(summary="Cancelar cuenta")
    @PostMapping("/canceled")
    public ResponseEntity<ApiResponse<String>> canceled(@Valid @RequestBody CancelAccountRequest request) {
        canceledAccountPort.canceledAccount(request);
        return ResponseEntity.ok(ApiResponse.ok("Cuenta cancelada correctamente"));
    }

    @PreAuthorize("hasRole('ADVISOR')")
    @Operation(summary="Depositar dinero en cuenta")
    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse<String>> deposit(@AuthenticationPrincipal SecurityUser securityUser, @Valid @RequestBody DepositRequest request) {
        depositPort.deposit(securityUser.getUserId(),request);
        return ResponseEntity.ok(ApiResponse.ok("Depósito realizado correctamente"));
    }

    @PreAuthorize("hasRole('ADVISOR')")
    @Operation(summary="Retirar dinero de cuenta")
    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<String>> withdraw(@AuthenticationPrincipal SecurityUser securityUser, @Valid @RequestBody WithdrawRequest request) {
        withdrawPort.withdraw(securityUser.getUserId(),request);
        return ResponseEntity.ok(ApiResponse.ok("Retiro realizado correctamente"));
    }

    @PreAuthorize("hasRole('ADVISOR')")
    @Operation(summary="Retirar todo el dinero de la cuenta")
    @PostMapping("/withdrawAll")
    public ResponseEntity<ApiResponse<WithdrawAllResponse>> withdrawAll(@AuthenticationPrincipal SecurityUser securityUser, @Valid @RequestBody WithdrawAllRequest request) {
        WithdrawAllResponse response = withdrawAllPort.withdraw(securityUser.getUserId(),request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }


    @PreAuthorize("hasRole('ADVISOR')")
    @Operation(summary="Dar excepción de GMF")
    @PostMapping("/gmf/exemption/activate")
    public ResponseEntity<ApiResponse<String>> activateGmfExemption(@Valid @RequestBody ActivateGmfExemptionRequest request) {
        activateGmfExemptionPort.activate(request);
        return ResponseEntity.ok(ApiResponse.ok("Excepción de GMF activada correctamente"));
    }

    @PreAuthorize("hasRole('ADVISOR')")
    @Operation(summary="Quitar excepción de GMF")
    @PostMapping("/gmf/exemption/deactivate")
    public ResponseEntity<ApiResponse<String>> deactivateGmfExemption(@Valid @RequestBody DeactivateGmfExemptionRequest request) {
        deactivateGmfExemptionPort.deactivate(request);
        return ResponseEntity.ok(ApiResponse.ok("Excepción de GMF desactivada correctamente"));
    }
}