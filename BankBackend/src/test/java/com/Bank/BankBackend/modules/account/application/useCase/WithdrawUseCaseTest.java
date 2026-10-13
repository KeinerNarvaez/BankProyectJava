package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.WithdrawRequest;
import com.Bank.BankBackend.modules.account.domain.exception.*;
import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.repository.AccountRepository;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import com.Bank.BankBackend.modules.gmf.application.port.in.CalculateGmfPort;
import com.Bank.BankBackend.modules.gmf.application.port.in.RegisterGmfChargePort;
import com.Bank.BankBackend.modules.transaction.application.port.in.RegisterWithdrawTransactionPort;
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
class WithdrawUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CalculateGmfPort calculateGmfPort;

    @Mock
    private RegisterGmfChargePort registerGmfChargePort;

    @Mock
    private RegisterWithdrawTransactionPort registerWithdrawTransactionPort;

    @InjectMocks
    private WithdrawUseCase withdrawUseCase;

    @Test
    void shouldWithdrawSuccessfullyWithGmf() {

        Integer userId = 2;
        Integer identifyNumber = 951753468;

        BigDecimal amount =
                new BigDecimal("50000");

        BigDecimal gmf =
                new BigDecimal("200");

        WithdrawRequest request =
                mock(WithdrawRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .accountStatus(AccountStatus.ACTIVE)
                .balance(new BigDecimal("100000"))
                .availableBalance(new BigDecimal("100000"))
                .gmfExempt(false)
                .clientId(client)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(request.getAmount())
                .thenReturn(amount);

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.of(account));

        when(calculateGmfPort.calculate(false, amount))
                .thenReturn(gmf);

        when(registerWithdrawTransactionPort.register(
                10,
                amount,
                userId
        )).thenReturn(100);

        withdrawUseCase.withdraw(userId, request);

        ArgumentCaptor<Account> captor =
                ArgumentCaptor.forClass(Account.class);

        verify(accountRepository)
                .save(captor.capture());

        Account updatedAccount =
                captor.getValue();

        assertEquals(
                0,
                new BigDecimal("49800")
                        .compareTo(updatedAccount.getBalance())
        );

        assertEquals(
                0,
                new BigDecimal("49800")
                        .compareTo(updatedAccount.getAvailableBalance())
        );

        verify(registerWithdrawTransactionPort)
                .register(10, amount, userId);

        verify(registerGmfChargePort)
                .register(100, 10, gmf);
    }

    @Test
    void shouldWithdrawWithoutGmfWhenAccountIsExempt() {

        BigDecimal amount =
                new BigDecimal("50000");

        WithdrawRequest request =
                mock(WithdrawRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .accountStatus(AccountStatus.ACTIVE)
                .balance(new BigDecimal("100000"))
                .availableBalance(new BigDecimal("100000"))
                .gmfExempt(true)
                .clientId(client)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(951753468);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(request.getAmount())
                .thenReturn(amount);

        when(clientRepository.findByIdentifyNumber(951753468))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.of(account));

        when(calculateGmfPort.calculate(true, amount))
                .thenReturn(BigDecimal.ZERO);

        when(registerWithdrawTransactionPort.register(
                10,
                amount,
                2
        )).thenReturn(100);

        withdrawUseCase.withdraw(2, request);

        verify(registerGmfChargePort, never())
                .register(
                        anyInt(),
                        anyInt(),
                        any(BigDecimal.class)
                );
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsInsufficient() {

        BigDecimal amount =
                new BigDecimal("100000");

        WithdrawRequest request =
                mock(WithdrawRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .accountStatus(AccountStatus.ACTIVE)
                .balance(new BigDecimal("100000"))
                .availableBalance(new BigDecimal("100000"))
                .gmfExempt(false)
                .clientId(client)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(951753468);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(request.getAmount())
                .thenReturn(amount);

        when(clientRepository.findByIdentifyNumber(951753468))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.of(account));

        when(calculateGmfPort.calculate(false, amount))
                .thenReturn(new BigDecimal("400"));

        assertThrows(
                InsufficientBalanceException.class,
                () -> withdrawUseCase.withdraw(2, request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));

        verifyNoInteractions(
                registerWithdrawTransactionPort,
                registerGmfChargePort
        );
    }

    @Test
    void shouldThrowExceptionWhenAccountIsCanceled() {

        WithdrawRequest request =
                mock(WithdrawRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountStatus(AccountStatus.CANCELED)
                .clientId(client)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(951753468);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(clientRepository.findByIdentifyNumber(951753468))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.of(account));

        assertThrows(
                AccountCanceledException.class,
                () -> withdrawUseCase.withdraw(2, request)
        );
    }

    @Test
    void shouldThrowExceptionWhenAccountIsInactive() {

        WithdrawRequest request =
                mock(WithdrawRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountStatus(AccountStatus.INACTIVE)
                .clientId(client)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(951753468);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(clientRepository.findByIdentifyNumber(951753468))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.of(account));

        assertThrows(
                AccountInactiveException.class,
                () -> withdrawUseCase.withdraw(2, request)
        );
    }
}