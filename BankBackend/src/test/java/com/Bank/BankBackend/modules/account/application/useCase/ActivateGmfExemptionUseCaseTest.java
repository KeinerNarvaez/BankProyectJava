package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.ActivateGmfExemptionRequest;
import com.Bank.BankBackend.modules.account.domain.exception.AccountDoesNotBelongToClientException;
import com.Bank.BankBackend.modules.account.domain.exception.AccountNotFoundException;
import com.Bank.BankBackend.modules.account.domain.exception.ClientAlreadyHasGmfExemptAccountException;
import com.Bank.BankBackend.modules.account.domain.exception.ClientNotFoundException;
import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.repository.AccountRepository;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import com.Bank.BankBackend.modules.gmf.application.port.in.RefundPendingGmfPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivateGmfExemptionUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private RefundPendingGmfPort refundPendingGmfPort;

    @InjectMocks
    private ActivateGmfExemptionUseCase activateGmfExemptionUseCase;

    @Test
    void shouldActivateGmfExemptionSuccessfully() {

        // ARRANGE
        Integer identifyNumber = 321654987;
        String accountNumber = "5312345678";
        BigDecimal refund = new BigDecimal("1200");

        ActivateGmfExemptionRequest request =
                mock(ActivateGmfExemptionRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .accountNumber(accountNumber)
                .balance(new BigDecimal("50000"))
                .availableBalance(new BigDecimal("50000"))
                .gmfExempt(false)
                .clientId(client)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(request.getAccountNumber())
                .thenReturn(accountNumber);

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber(accountNumber))
                .thenReturn(Optional.of(account));

        when(accountRepository.findAllByClientId_ClientId(1))
                .thenReturn(List.of(account));

        when(refundPendingGmfPort.refund(10))
                .thenReturn(refund);

        // ACT
        activateGmfExemptionUseCase.activate(request);

        // ASSERT
        ArgumentCaptor<Account> captor =
                ArgumentCaptor.forClass(Account.class);

        verify(accountRepository)
                .save(captor.capture());

        Account updatedAccount = captor.getValue();

        assertTrue(updatedAccount.getGmfExempt());

        assertEquals(
                0,
                new BigDecimal("51200")
                        .compareTo(updatedAccount.getBalance())
        );

        assertEquals(
                0,
                new BigDecimal("51200")
                        .compareTo(updatedAccount.getAvailableBalance())
        );

        assertNotNull(updatedAccount.getUpdatedAt());

        verify(refundPendingGmfPort)
                .refund(10);
    }

    @Test
    void shouldThrowExceptionWhenClientDoesNotExist() {

        // ARRANGE
        Integer identifyNumber = 321654987;

        ActivateGmfExemptionRequest request =
                mock(ActivateGmfExemptionRequest.class);

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(
                ClientNotFoundException.class,
                () -> activateGmfExemptionUseCase.activate(request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));

        verifyNoInteractions(refundPendingGmfPort);
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotExist() {

        // ARRANGE
        Integer identifyNumber = 321654987;
        String accountNumber = "5312345678";

        ActivateGmfExemptionRequest request =
                mock(ActivateGmfExemptionRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(request.getAccountNumber())
                .thenReturn(accountNumber);

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber(accountNumber))
                .thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(
                AccountNotFoundException.class,
                () -> activateGmfExemptionUseCase.activate(request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));

        verifyNoInteractions(refundPendingGmfPort);
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotBelongToClient() {

        // ARRANGE
        Integer identifyNumber = 321654987;
        String accountNumber = "5312345678";

        ActivateGmfExemptionRequest request =
                mock(ActivateGmfExemptionRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Client owner = Client.builder()
                .clientId(99)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .accountNumber(accountNumber)
                .gmfExempt(false)
                .clientId(owner)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(request.getAccountNumber())
                .thenReturn(accountNumber);

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber(accountNumber))
                .thenReturn(Optional.of(account));

        // ACT & ASSERT
        assertThrows(
                AccountDoesNotBelongToClientException.class,
                () -> activateGmfExemptionUseCase.activate(request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));

        verifyNoInteractions(refundPendingGmfPort);
    }

    @Test
    void shouldThrowExceptionWhenClientAlreadyHasAnotherGmfExemptAccount() {

        // ARRANGE
        Integer identifyNumber = 321654987;
        String accountNumber = "5312345678";

        ActivateGmfExemptionRequest request =
                mock(ActivateGmfExemptionRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account selectedAccount = Account.builder()
                .accountId(10)
                .accountNumber(accountNumber)
                .gmfExempt(false)
                .clientId(client)
                .build();

        Account anotherAccount = Account.builder()
                .accountId(20)
                .gmfExempt(true)
                .clientId(client)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(request.getAccountNumber())
                .thenReturn(accountNumber);

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber(accountNumber))
                .thenReturn(Optional.of(selectedAccount));

        when(accountRepository.findAllByClientId_ClientId(1))
                .thenReturn(List.of(
                        selectedAccount,
                        anotherAccount
                ));

        // ACT & ASSERT
        assertThrows(
                ClientAlreadyHasGmfExemptAccountException.class,
                () -> activateGmfExemptionUseCase.activate(request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));

        verifyNoInteractions(refundPendingGmfPort);
    }
}