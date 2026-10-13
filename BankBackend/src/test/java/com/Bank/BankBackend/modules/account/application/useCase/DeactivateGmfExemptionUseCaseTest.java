package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.DeactivateGmfExemptionRequest;
import com.Bank.BankBackend.modules.account.domain.exception.*;
import com.Bank.BankBackend.modules.account.domain.model.Account;
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
class DeactivateGmfExemptionUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private DeactivateGmfExemptionUseCase deactivateGmfExemptionUseCase;

    @Test
    void shouldDeactivateGmfExemptionSuccessfully() {

        Integer identifyNumber = 963741852;
        String accountNumber = "5312345678";

        DeactivateGmfExemptionRequest request =
                mock(DeactivateGmfExemptionRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountId(10)
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
                .thenReturn(Optional.of(account));

        deactivateGmfExemptionUseCase.deactivate(request);

        ArgumentCaptor<Account> captor =
                ArgumentCaptor.forClass(Account.class);

        verify(accountRepository)
                .save(captor.capture());

        Account updatedAccount = captor.getValue();

        assertFalse(
                updatedAccount.getGmfExempt()
        );

        assertNotNull(
                updatedAccount.getUpdatedAt()
        );
    }

    @Test
    void shouldThrowExceptionWhenClientDoesNotExist() {

        DeactivateGmfExemptionRequest request =
                mock(DeactivateGmfExemptionRequest.class);

        when(request.getIdentifyNumber())
                .thenReturn(963741852);

        when(clientRepository.findByIdentifyNumber(963741852))
                .thenReturn(Optional.empty());

        assertThrows(
                ClientNotFoundException.class,
                () -> deactivateGmfExemptionUseCase.deactivate(request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotExist() {

        DeactivateGmfExemptionRequest request =
                mock(DeactivateGmfExemptionRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(963741852);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(clientRepository.findByIdentifyNumber(963741852))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> deactivateGmfExemptionUseCase.deactivate(request)
        );
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotBelongToClient() {

        DeactivateGmfExemptionRequest request =
                mock(DeactivateGmfExemptionRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Client owner = Client.builder()
                .clientId(99)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .gmfExempt(true)
                .clientId(owner)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(963741852);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(clientRepository.findByIdentifyNumber(963741852))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.of(account));

        assertThrows(
                AccountDoesNotBelongToClientException.class,
                () -> deactivateGmfExemptionUseCase.deactivate(request)
        );
    }

    @Test
    void shouldThrowExceptionWhenGmfExemptionIsAlreadyInactive() {

        DeactivateGmfExemptionRequest request =
                mock(DeactivateGmfExemptionRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .gmfExempt(false)
                .clientId(client)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(963741852);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(clientRepository.findByIdentifyNumber(963741852))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.of(account));

        assertThrows(
                AccountGmfAlreadyInactiveException.class,
                () -> deactivateGmfExemptionUseCase.deactivate(request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));
    }
}