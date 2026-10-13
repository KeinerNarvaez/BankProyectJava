package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.CancelAccountRequest;
import com.Bank.BankBackend.modules.account.domain.exception.*;
import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.repository.AccountRepository;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
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
class CanceledAccountUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private CanceledAccountUseCase canceledAccountUseCase;

    @Test
    void shouldCancelAccountSuccessfully() {

        // ARRANGE
        Integer identifyNumber = 123456789;
        String accountNumber = "5312345678";

        CancelAccountRequest request =
                mock(CancelAccountRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .accountNumber(accountNumber)
                .accountStatus(AccountStatus.ACTIVE)
                .balance(BigDecimal.ZERO)
                .availableBalance(BigDecimal.ZERO)
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

        // ACT
        canceledAccountUseCase.canceledAccount(request);

        // ASSERT
        ArgumentCaptor<Account> captor =
                ArgumentCaptor.forClass(Account.class);

        verify(accountRepository)
                .save(captor.capture());

        Account updatedAccount = captor.getValue();

        assertEquals(
                AccountStatus.CANCELED,
                updatedAccount.getAccountStatus()
        );

        assertNotNull(
                updatedAccount.getUpdatedAt()
        );
    }

    @Test
    void shouldThrowExceptionWhenClientDoesNotExist() {

        // ARRANGE
        Integer identifyNumber = 123456789;

        CancelAccountRequest request =
                mock(CancelAccountRequest.class);

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(
                ClientNotFoundException.class,
                () -> canceledAccountUseCase.canceledAccount(request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotExist() {

        // ARRANGE
        Integer identifyNumber = 123456789;
        String accountNumber = "5312345678";

        CancelAccountRequest request =
                mock(CancelAccountRequest.class);

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
                () -> canceledAccountUseCase.canceledAccount(request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotBelongToClient() {

        Integer identifyNumber = 123456789;
        String accountNumber = "5312345678";

        CancelAccountRequest request =
                mock(CancelAccountRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Client owner = Client.builder()
                .clientId(99)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .accountStatus(AccountStatus.ACTIVE)
                .balance(BigDecimal.ZERO)
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

        assertThrows(
                AccountDoesNotBelongToClientException.class,
                () -> canceledAccountUseCase.canceledAccount(request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenAccountIsAlreadyCanceled() {

        Integer identifyNumber = 123456789;
        String accountNumber = "5312345678";

        CancelAccountRequest request =
                mock(CancelAccountRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .accountStatus(AccountStatus.CANCELED)
                .balance(BigDecimal.ZERO)
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

        assertThrows(
                AccountAlreadyCancelledException.class,
                () -> canceledAccountUseCase.canceledAccount(request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenAccountBalanceIsNotZero() {

        Integer identifyNumber = 123456789;
        String accountNumber = "5312345678";

        CancelAccountRequest request =
                mock(CancelAccountRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .accountStatus(AccountStatus.ACTIVE)
                .balance(new BigDecimal("1000"))
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

        assertThrows(
                AccountBalanceNotZeroException.class,
                () -> canceledAccountUseCase.canceledAccount(request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));
    }
}