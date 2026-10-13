package com.Bank.BankBackend.modules.account.interfaces.rest;

import com.Bank.BankBackend.modules.account.application.dto.request.ActivateTypeIdentifyRequest;
import com.Bank.BankBackend.modules.account.application.dto.request.CreateTypeIdentifyRequest;
import com.Bank.BankBackend.modules.account.application.dto.request.DeactivateTypeIdentifyRequest;
import com.Bank.BankBackend.modules.account.application.port.in.ActivateTypeIdentifyPort;
import com.Bank.BankBackend.modules.account.application.port.in.CreateTypeIdentifyPort;
import com.Bank.BankBackend.modules.account.application.port.in.DeactivateTypeIdentifyPort;
import com.Bank.BankBackend.modules.account.application.port.in.GetTypeIdentifyPort;
import com.Bank.BankBackend.modules.account.domain.model.TypeIdentify;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import com.Bank.BankBackend.shared.interfaces.ApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TypeIdentifyControllerTest {

    @Mock
    private CreateTypeIdentifyPort createTypeIdentifyPort;

    @Mock
    private GetTypeIdentifyPort getTypeIdentifyPort;

    @Mock
    private ActivateTypeIdentifyPort activateTypeIdentifyPort;

    @Mock
    private DeactivateTypeIdentifyPort deactivateTypeIdentifyPort;

    @InjectMocks
    private TypeIdentifyController typeIdentifyController;

    @Test
    void shouldCreateTypeIdentifySuccessfully() {

        CreateTypeIdentifyRequest request =
                mock(CreateTypeIdentifyRequest.class);

        ResponseEntity<ApiResponse<String>> response =
                typeIdentifyController.create(request);

        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        verify(createTypeIdentifyPort)
                .create(request);
    }

    @Test
    void shouldFindAllTypeIdentifiesSuccessfully() {

        TypeIdentify cc = TypeIdentify.builder()
                .name("CC")
                .status(TypeStatus.ACTIVE)
                .build();

        TypeIdentify ce = TypeIdentify.builder()
                .name("CE")
                .status(TypeStatus.ACTIVE)
                .build();

        when(getTypeIdentifyPort.getAllIdentify())
                .thenReturn(List.of(cc, ce));

        ResponseEntity<ApiResponse<List<TypeIdentify>>> response =
                typeIdentifyController.findAll();

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        verify(getTypeIdentifyPort)
                .getAllIdentify();
    }

    @Test
    void shouldActivateTypeIdentifySuccessfully() {

        ActivateTypeIdentifyRequest request =
                mock(ActivateTypeIdentifyRequest.class);

        ResponseEntity<ApiResponse<String>> response =
                typeIdentifyController.activate(request);

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        verify(activateTypeIdentifyPort)
                .activate(request);
    }

    @Test
    void shouldDeactivateTypeIdentifySuccessfully() {

        DeactivateTypeIdentifyRequest request =
                mock(DeactivateTypeIdentifyRequest.class);

        ResponseEntity<ApiResponse<String>> response =
                typeIdentifyController.deactivate(request);

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        verify(deactivateTypeIdentifyPort)
                .deactivate(request);
    }
}