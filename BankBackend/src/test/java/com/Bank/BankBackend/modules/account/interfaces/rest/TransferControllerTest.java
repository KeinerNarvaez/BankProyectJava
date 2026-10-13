package com.Bank.BankBackend.modules.account.interfaces.rest;

import com.Bank.BankBackend.modules.account.application.dto.request.TransferRequest;
import com.Bank.BankBackend.modules.account.application.port.in.TransferPort;
import com.Bank.BankBackend.shared.infrastructure.security.SecurityUser;
import com.Bank.BankBackend.shared.interfaces.ApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferControllerTest {

    @Mock
    private TransferPort transferPort;

    @Mock
    private SecurityUser securityUser;

    @InjectMocks
    private TransferController transferController;

    @Test
    void shouldTransferSuccessfully() {

        TransferRequest request =
                mock(TransferRequest.class);

        when(securityUser.getUserId())
                .thenReturn(2);

        ResponseEntity<ApiResponse<String>> response =
                transferController.transfer(
                        securityUser,
                        request
                );

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        verify(transferPort)
                .transfer(2, request);
    }
}