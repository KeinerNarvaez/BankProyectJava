package com.Bank.BankBackend.modules.account.interfaces.rest;

import com.Bank.BankBackend.modules.account.application.dto.request.*;
import com.Bank.BankBackend.modules.account.application.dto.response.GetClientResponse;
import com.Bank.BankBackend.modules.account.application.dto.response.GetListClientResponse;
import com.Bank.BankBackend.modules.account.application.port.in.*;
import com.Bank.BankBackend.shared.infrastructure.security.SecurityUser;
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
class ClientControllerTest {

    @Mock
    private CreateClientPort createClientPort;

    @Mock
    private GetClientPort getClientPort;

    @Mock
    private ActivateClientPort activateClientPort;

    @Mock
    private DeactivateClientPort deactivateClientPort;

    @Mock
    private SecurityUser securityUser;

    @InjectMocks
    private ClientController clientController;

    @Test
    void shouldCreateClientSuccessfully() {

        CreateClientRequest request =
                mock(CreateClientRequest.class);

        when(securityUser.getUserId())
                .thenReturn(2);

        ResponseEntity<ApiResponse<String>> response =
                clientController.create(
                        securityUser,
                        request
                );

        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        verify(createClientPort)
                .create(2, request);
    }

    @Test
    void shouldGetClientSuccessfully() {

        GetClientRequest request =
                mock(GetClientRequest.class);

        GetClientResponse clientResponse =
                mock(GetClientResponse.class);

        when(getClientPort.getClient(request))
                .thenReturn(clientResponse);

        ResponseEntity<ApiResponse<GetClientResponse>> response =
                clientController.getClient(request);

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        verify(getClientPort)
                .getClient(request);
    }

    @Test
    void shouldGetActiveClientsSuccessfully() {

        GetListClientResponse client =
                mock(GetListClientResponse.class);

        when(getClientPort.getClientActive())
                .thenReturn(List.of(client));

        ResponseEntity<ApiResponse<List<GetListClientResponse>>> response =
                clientController.getActiveClients();

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        verify(getClientPort)
                .getClientActive();
    }

    @Test
    void shouldGetInactiveClientsSuccessfully() {

        GetListClientResponse client =
                mock(GetListClientResponse.class);

        when(getClientPort.getClientInactive())
                .thenReturn(List.of(client));

        ResponseEntity<ApiResponse<List<GetListClientResponse>>> response =
                clientController.getInactiveClients();

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        verify(getClientPort)
                .getClientInactive();
    }

    @Test
    void shouldActivateClientSuccessfully() {

        ActivateClientRequest request =
                mock(ActivateClientRequest.class);

        ResponseEntity<ApiResponse<String>> response =
                clientController.activate(request);

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        verify(activateClientPort)
                .activateClient(request);
    }

    @Test
    void shouldDeactivateClientSuccessfully() {

        DeactivateClientRequest request =
                mock(DeactivateClientRequest.class);

        ResponseEntity<ApiResponse<String>> response =
                clientController.deactivate(request);

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        verify(deactivateClientPort)
                .deactivateClient(request);
    }
}