package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.ActivateAccountRequest;
import com.Bank.BankBackend.modules.account.domain.exception.AccountAlreadyActiveException;
import com.Bank.BankBackend.modules.account.domain.exception.AccountDoesNotBelongToClientException;
import com.Bank.BankBackend.modules.account.domain.exception.AccountNotFoundException;
import com.Bank.BankBackend.modules.account.domain.exception.ClientNotFoundException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivateAccountUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private ActivateAccountUseCase activateAccountUseCase;

    @Test
    void shouldActivateAccountSuccessfully() {

        // ARRANGE
        Integer identifyNumber = 654321987;
        String accountNumber = "5312345678";

        ActivateAccountRequest request =
                mock(ActivateAccountRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .accountNumber(accountNumber)
                .accountStatus(AccountStatus.INACTIVE)
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
        activateAccountUseCase.activateAccount(request);

        // ASSERT
        ArgumentCaptor<Account> captor =
                ArgumentCaptor.forClass(Account.class);

        verify(accountRepository)
                .save(captor.capture());

        Account updatedAccount = captor.getValue();

        assertEquals(
                AccountStatus.ACTIVE,
                updatedAccount.getAccountStatus()
        );

        assertNotNull(
                updatedAccount.getUpdatedAt()
        );
    }

    @Test
    void shouldThrowExceptionWhenClientDoesNotExist() {

        // ARRANGE
        Integer identifyNumber = 654321987;

        ActivateAccountRequest request =
                mock(ActivateAccountRequest.class);

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(
                ClientNotFoundException.class,
                () -> activateAccountUseCase.activateAccount(request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotExist() {

        // ARRANGE
        Integer identifyNumber = 654321987;
        String accountNumber = "5312345678";

        ActivateAccountRequest request =
                mock(ActivateAccountRequest.class);

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
                () -> activateAccountUseCase.activateAccount(request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotBelongToClient() {

        // ARRANGE
        Integer identifyNumber = 654321987;
        String accountNumber = "5312345678";

        ActivateAccountRequest request =
                mock(ActivateAccountRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Client owner = Client.builder()
                .clientId(99)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .accountStatus(AccountStatus.INACTIVE)
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
                () -> activateAccountUseCase.activateAccount(request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenAccountIsAlreadyActive() {

        // ARRANGE
        Integer identifyNumber = 654321987;
        String accountNumber = "5312345678";

        ActivateAccountRequest request =
                mock(ActivateAccountRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .accountStatus(AccountStatus.ACTIVE)
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

        // ACT & ASSERT
        assertThrows(
                AccountAlreadyActiveException.class,
                () -> activateAccountUseCase.activateAccount(request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));
    }
}