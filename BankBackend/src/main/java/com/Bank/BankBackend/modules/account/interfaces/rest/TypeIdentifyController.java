package com.Bank.BankBackend.modules.account.interfaces.rest;

import com.Bank.BankBackend.modules.account.application.dto.request.ActivateTypeIdentifyRequest;
import com.Bank.BankBackend.modules.account.application.dto.request.CreateTypeIdentifyRequest;
import com.Bank.BankBackend.modules.account.application.dto.request.DeactivateTypeIdentifyRequest;
import com.Bank.BankBackend.modules.account.application.port.in.ActivateTypeIdentifyPort;
import com.Bank.BankBackend.modules.account.application.port.in.CreateTypeIdentifyPort;
import com.Bank.BankBackend.modules.account.application.port.in.DeactivateTypeIdentifyPort;
import com.Bank.BankBackend.modules.account.application.port.in.GetTypeIdentifyPort;
import com.Bank.BankBackend.modules.account.domain.model.TypeIdentify;

import com.Bank.BankBackend.shared.interfaces.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/identification-types")
@RequiredArgsConstructor
@Tag(name = "Identification Types", description = "Endpoints de gestión de tipos de identificación: creación y consulta")
public class TypeIdentifyController {

    private final CreateTypeIdentifyPort createTypeIdentifyPort;
    private final GetTypeIdentifyPort getTypeIdentifyPort;
    private final ActivateTypeIdentifyPort activateTypeIdentifyPort;
    private final DeactivateTypeIdentifyPort deactivateTypeIdentifyPort;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear tipos de identificación solo ADMIN")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> create(@Valid @RequestBody CreateTypeIdentifyRequest request) {
        createTypeIdentifyPort.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Tipo de identificación creado correctamente"));
    }

    @PreAuthorize("hasAnyRole('ADVISOR', 'ADMIN')")
    @Operation(summary="Cargar todos los tipos de identificación")
    @GetMapping("/find")
    public ResponseEntity<ApiResponse<List<TypeIdentify>>> findAll() {
        List<TypeIdentify> types = getTypeIdentifyPort.getAllIdentify();
        return ResponseEntity.ok(ApiResponse.ok(types));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Activar tipo de identificación solo ADMIN")
    @PostMapping("/activate")
    public ResponseEntity<ApiResponse<String>> activate(@Valid @RequestBody ActivateTypeIdentifyRequest request) {
        activateTypeIdentifyPort.activate(request);
        return ResponseEntity.ok(ApiResponse.ok("Tipo de identificación activado correctamente"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Desactivar tipo de identificación solo ADMIN")
    @PostMapping("/deactivate")
    public ResponseEntity<ApiResponse<String>> deactivate(@Valid @RequestBody DeactivateTypeIdentifyRequest request) {
        deactivateTypeIdentifyPort.deactivate(request);
        return ResponseEntity.ok(ApiResponse.ok("Tipo de identificación desactivado correctamente"));
    }
}