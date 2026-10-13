package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.DeactivateAccountRequest;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeactivateAccountUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private DeactivateAccountUseCase deactivateAccountUseCase;

    @Test
    void shouldDeactivateAccountSuccessfully() {

        Integer identifyNumber = 852963741;
        String accountNumber = "5312345678";

        DeactivateAccountRequest request =
                mock(DeactivateAccountRequest.class);

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

        deactivateAccountUseCase.deactivateAccount(request);

        ArgumentCaptor<Account> captor =
                ArgumentCaptor.forClass(Account.class);

        verify(accountRepository)
                .save(captor.capture());

        Account updatedAccount = captor.getValue();

        assertEquals(
                AccountStatus.INACTIVE,
                updatedAccount.getAccountStatus()
        );

        assertNotNull(
                updatedAccount.getUpdatedAt()
        );
    }

    @Test
    void shouldThrowExceptionWhenClientDoesNotExist() {

        DeactivateAccountRequest request =
                mock(DeactivateAccountRequest.class);

        when(request.getIdentifyNumber())
                .thenReturn(852963741);

        when(clientRepository.findByIdentifyNumber(852963741))
                .thenReturn(Optional.empty());

        assertThrows(
                ClientNotFoundException.class,
                () -> deactivateAccountUseCase.deactivateAccount(request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotExist() {

        DeactivateAccountRequest request =
                mock(DeactivateAccountRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(852963741);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(clientRepository.findByIdentifyNumber(852963741))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> deactivateAccountUseCase.deactivateAccount(request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotBelongToClient() {

        DeactivateAccountRequest request =
                mock(DeactivateAccountRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Client owner = Client.builder()
                .clientId(99)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .accountStatus(AccountStatus.ACTIVE)
                .clientId(owner)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(852963741);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(clientRepository.findByIdentifyNumber(852963741))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.of(account));

        assertThrows(
                AccountDoesNotBelongToClientException.class,
                () -> deactivateAccountUseCase.deactivateAccount(request)
        );
    }

    @Test
    void shouldThrowExceptionWhenAccountIsCanceled() {

        DeactivateAccountRequest request =
                mock(DeactivateAccountRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .accountStatus(AccountStatus.CANCELED)
                .clientId(client)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(852963741);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(clientRepository.findByIdentifyNumber(852963741))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.of(account));

        assertThrows(
                AccountAlreadyCancelledException.class,
                () -> deactivateAccountUseCase.deactivateAccount(request)
        );
    }

    @Test
    void shouldThrowExceptionWhenAccountIsAlreadyInactive() {

        DeactivateAccountRequest request =
                mock(DeactivateAccountRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .accountStatus(AccountStatus.INACTIVE)
                .clientId(client)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(852963741);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(clientRepository.findByIdentifyNumber(852963741))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.of(account));

        assertThrows(
                AccountAlreadyInactiveException.class,
                () -> deactivateAccountUseCase.deactivateAccount(request)
        );
    }
}