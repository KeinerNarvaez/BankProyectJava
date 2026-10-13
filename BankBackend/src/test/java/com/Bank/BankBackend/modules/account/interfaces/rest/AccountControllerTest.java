package com.Bank.BankBackend.modules.account.interfaces.rest;

import com.Bank.BankBackend.modules.account.application.dto.request.*;
import com.Bank.BankBackend.modules.account.application.dto.response.WithdrawAllResponse;
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

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private CreateAccountPort createAccountPort;

    @Mock
    private ActivateAccountPort activateAccountPort;

    @Mock
    private DeactivateAccountPort deactivateAccountPort;

    @Mock
    private CanceledAccountPort canceledAccountPort;

    @Mock
    private DepositPort depositPort;

    @Mock
    private WithdrawPort withdrawPort;

    @Mock
    private WithdrawAllPort withdrawAllPort;

    @Mock
    private ActivateGmfExemptionPort activateGmfExemptionPort;

    @Mock
    private DeactivateGmfExemptionPort deactivateGmfExemptionPort;

    @Mock
    private SecurityUser securityUser;

    @InjectMocks
    private AccountController accountController;

    @Test
    void shouldCreateAccountSuccessfully() {

        CreateAccountRequest request =
                mock(CreateAccountRequest.class);

        when(securityUser.getUserId())
                .thenReturn(2);

        ResponseEntity<ApiResponse<String>> response =
                accountController.create(
                        securityUser,
                        request
                );

        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        verify(createAccountPort)
                .create(2, request);
    }

    @Test
    void shouldActivateAccountSuccessfully() {

        ActivateAccountRequest request =
                mock(ActivateAccountRequest.class);

        ResponseEntity<ApiResponse<String>> response =
                accountController.activate(request);

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        verify(activateAccountPort)
                .activateAccount(request);
    }

    @Test
    void shouldDeactivateAccountSuccessfully() {

        DeactivateAccountRequest request =
                mock(DeactivateAccountRequest.class);

        ResponseEntity<ApiResponse<String>> response =
                accountController.canceled(request);

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        verify(deactivateAccountPort)
                .deactivateAccount(request);
    }

    @Test
    void shouldCancelAccountSuccessfully() {

        CancelAccountRequest request =
                mock(CancelAccountRequest.class);

        ResponseEntity<ApiResponse<String>> response =
                accountController.canceled(request);

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        verify(canceledAccountPort)
                .canceledAccount(request);
    }

    @Test
    void shouldDepositSuccessfully() {

        DepositRequest request =
                mock(DepositRequest.class);

        when(securityUser.getUserId())
                .thenReturn(2);

        ResponseEntity<ApiResponse<String>> response =
                accountController.deposit(
                        securityUser,
                        request
                );

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        verify(depositPort)
                .deposit(2, request);
    }

    @Test
    void shouldWithdrawSuccessfully() {

        WithdrawRequest request =
                mock(WithdrawRequest.class);

        when(securityUser.getUserId())
                .thenReturn(2);

        ResponseEntity<ApiResponse<String>> response =
                accountController.withdraw(
                        securityUser,
                        request
                );

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        verify(withdrawPort)
                .withdraw(2, request);
    }

    @Test
    void shouldWithdrawAllSuccessfully() {

        WithdrawAllRequest request =
                mock(WithdrawAllRequest.class);

        WithdrawAllResponse withdrawAllResponse =
                WithdrawAllResponse.builder()
                        .withdraw(new BigDecimal("50000"))
                        .build();

        when(securityUser.getUserId())
                .thenReturn(2);

        when(withdrawAllPort.withdraw(
                2,
                request
        )).thenReturn(withdrawAllResponse);

        ResponseEntity<ApiResponse<WithdrawAllResponse>> response =
                accountController.withdrawAll(
                        securityUser,
                        request
                );

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        verify(withdrawAllPort)
                .withdraw(2, request);
    }

    @Test
    void shouldActivateGmfExemptionSuccessfully() {

        ActivateGmfExemptionRequest request =
                mock(ActivateGmfExemptionRequest.class);

        ResponseEntity<ApiResponse<String>> response =
                accountController.activateGmfExemption(request);

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        verify(activateGmfExemptionPort)
                .activate(request);
    }

    @Test
    void shouldDeactivateGmfExemptionSuccessfully() {

        DeactivateGmfExemptionRequest request =
                mock(DeactivateGmfExemptionRequest.class);

        ResponseEntity<ApiResponse<String>> response =
                accountController.deactivateGmfExemption(request);

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        verify(deactivateGmfExemptionPort)
                .deactivate(request);
    }
}