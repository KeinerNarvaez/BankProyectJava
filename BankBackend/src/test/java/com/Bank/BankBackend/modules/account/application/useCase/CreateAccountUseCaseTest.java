package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.CreateAccountRequest;
import com.Bank.BankBackend.modules.account.domain.exception.ClientNotExistException;
import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.model.TypeAccount;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAccountUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private CreateAccountUseCase createAccountUseCase;

    @Test
    void shouldCreateSavingsAccountSuccessfully() {

        Integer userCreationId = 2;

        CreateAccountRequest request =
                mock(CreateAccountRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        when(request.getClientId())
                .thenReturn(1);

        when(request.getTypeAccount())
                .thenReturn(TypeAccount.SAVINGS);

        when(clientRepository.findById(1))
                .thenReturn(Optional.of(client));

        when(accountRepository.existsByAccountNumber(anyString()))
                .thenReturn(false);

        createAccountUseCase.create(
                userCreationId,
                request
        );

        ArgumentCaptor<Account> captor =
                ArgumentCaptor.forClass(Account.class);

        verify(accountRepository)
                .save(captor.capture());

        Account savedAccount = captor.getValue();

        assertEquals(
                TypeAccount.SAVINGS,
                savedAccount.getTypeAccount()
        );

        assertEquals(
                AccountStatus.ACTIVE,
                savedAccount.getAccountStatus()
        );

        assertEquals(
                BigDecimal.ZERO,
                savedAccount.getBalance()
        );

        assertEquals(
                BigDecimal.ZERO,
                savedAccount.getAvailableBalance()
        );

        assertFalse(
                savedAccount.getGmfExempt()
        );

        assertEquals(
                client,
                savedAccount.getClientId()
        );

        assertEquals(
                userCreationId,
                savedAccount.getUserCreationId()
        );

        assertNotNull(
                savedAccount.getCreatedAt()
        );

        assertNotNull(
                savedAccount.getUpdatedAt()
        );

        assertNotNull(
                savedAccount.getAccountNumber()
        );

        assertEquals(
                10,
                savedAccount.getAccountNumber().length()
        );

        assertTrue(
                savedAccount.getAccountNumber()
                        .startsWith("53")
        );
    }

    @Test
    void shouldCreateCurrentAccountSuccessfully() {

        Integer userCreationId = 2;

        CreateAccountRequest request =
                mock(CreateAccountRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        when(request.getClientId())
                .thenReturn(1);

        when(request.getTypeAccount())
                .thenReturn(TypeAccount.CURRENT);

        when(clientRepository.findById(1))
                .thenReturn(Optional.of(client));

        when(accountRepository.existsByAccountNumber(anyString()))
                .thenReturn(false);

        createAccountUseCase.create(
                userCreationId,
                request
        );

        ArgumentCaptor<Account> captor =
                ArgumentCaptor.forClass(Account.class);

        verify(accountRepository)
                .save(captor.capture());

        Account savedAccount = captor.getValue();

        assertEquals(
                TypeAccount.CURRENT,
                savedAccount.getTypeAccount()
        );

        assertEquals(
                AccountStatus.ACTIVE,
                savedAccount.getAccountStatus()
        );

        assertNotNull(
                savedAccount.getAccountNumber()
        );

        assertEquals(
                10,
                savedAccount.getAccountNumber().length()
        );

        assertTrue(
                savedAccount.getAccountNumber()
                        .startsWith("33")
        );
    }

    @Test
    void shouldThrowExceptionWhenClientDoesNotExist() {

        CreateAccountRequest request =
                mock(CreateAccountRequest.class);

        when(request.getClientId())
                .thenReturn(99);

        when(request.getTypeAccount())
                .thenReturn(TypeAccount.SAVINGS);

        when(clientRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                ClientNotExistException.class,
                () -> createAccountUseCase.create(
                        2,
                        request
                )
        );

        verify(accountRepository, never())
                .save(any(Account.class));
    }
}