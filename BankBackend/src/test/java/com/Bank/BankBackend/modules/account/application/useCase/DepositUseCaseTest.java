package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.DepositRequest;
import com.Bank.BankBackend.modules.account.domain.exception.*;
import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.repository.AccountRepository;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import com.Bank.BankBackend.modules.transaction.application.port.in.RegisterDepositTransactionPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepositUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private RegisterDepositTransactionPort registerDepositTransactionPort;

    @InjectMocks
    private DepositUseCase depositUseCase;

    @Test
    void shouldDepositSuccessfully() {

        Integer userId = 2;
        Integer identifyNumber = 456789123;
        String accountNumber = "5312345678";
        BigDecimal amount = new BigDecimal("50000");

        DepositRequest request =
                mock(DepositRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .accountStatus(AccountStatus.ACTIVE)
                .balance(new BigDecimal("100000"))
                .availableBalance(new BigDecimal("100000"))
                .clientId(client)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(request.getAccountNumber())
                .thenReturn(accountNumber);

        when(request.getAmount())
                .thenReturn(amount);

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber(accountNumber))
                .thenReturn(Optional.of(account));

        depositUseCase.deposit(userId, request);

        ArgumentCaptor<Account> captor =
                ArgumentCaptor.forClass(Account.class);

        verify(accountRepository)
                .save(captor.capture());

        Account updatedAccount = captor.getValue();

        assertEquals(
                0,
                new BigDecimal("150000")
                        .compareTo(updatedAccount.getBalance())
        );

        assertEquals(
                0,
                new BigDecimal("150000")
                        .compareTo(updatedAccount.getAvailableBalance())
        );

        verify(registerDepositTransactionPort)
                .register(10, amount, userId);
    }

    @Test
    void shouldThrowExceptionWhenClientDoesNotExist() {

        DepositRequest request =
                mock(DepositRequest.class);

        when(request.getIdentifyNumber())
                .thenReturn(456789123);

        when(clientRepository.findByIdentifyNumber(456789123))
                .thenReturn(Optional.empty());

        assertThrows(
                ClientNotFoundException.class,
                () -> depositUseCase.deposit(2, request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotExist() {

        DepositRequest request =
                mock(DepositRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(456789123);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(clientRepository.findByIdentifyNumber(456789123))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> depositUseCase.deposit(2, request)
        );
    }

    @Test
    void shouldThrowExceptionWhenAccountIsCanceled() {

        DepositRequest request =
                mock(DepositRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountStatus(AccountStatus.CANCELED)
                .clientId(client)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(456789123);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(clientRepository.findByIdentifyNumber(456789123))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.of(account));

        assertThrows(
                AccountCanceledException.class,
                () -> depositUseCase.deposit(2, request)
        );
    }

    @Test
    void shouldThrowExceptionWhenAccountIsInactive() {

        DepositRequest request =
                mock(DepositRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountStatus(AccountStatus.INACTIVE)
                .clientId(client)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(456789123);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(clientRepository.findByIdentifyNumber(456789123))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.of(account));

        assertThrows(
                AccountInactiveException.class,
                () -> depositUseCase.deposit(2, request)
        );
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotBelongToClient() {

        DepositRequest request =
                mock(DepositRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Client owner = Client.builder()
                .clientId(99)
                .build();

        Account account = Account.builder()
                .accountStatus(AccountStatus.ACTIVE)
                .clientId(owner)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(456789123);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(clientRepository.findByIdentifyNumber(456789123))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.of(account));

        assertThrows(
                AccountDoesNotBelongToClientException.class,
                () -> depositUseCase.deposit(2, request)
        );

        verify(registerDepositTransactionPort, never())
                .register(anyInt(), any(BigDecimal.class), anyInt());
    }
}