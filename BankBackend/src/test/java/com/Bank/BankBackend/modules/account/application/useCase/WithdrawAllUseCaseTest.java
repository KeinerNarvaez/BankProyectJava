package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.WithdrawAllRequest;
import com.Bank.BankBackend.modules.account.application.dto.response.WithdrawAllResponse;
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
class WithdrawAllUseCaseTest {

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
    private WithdrawAllUseCase withdrawAllUseCase;

    @Test
    void shouldWithdrawAllSuccessfully() {

        Integer userId = 2;
        Integer identifyNumber = 147258369;

        WithdrawAllRequest request =
                mock(WithdrawAllRequest.class);

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

        BigDecimal withdrawAmount =
                new BigDecimal("99601.59");

        BigDecimal gmf =
                new BigDecimal("398.40");

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.of(account));

        when(calculateGmfPort.calculateMaxWithdrawAmount(
                false,
                new BigDecimal("100000")
        )).thenReturn(withdrawAmount);

        when(calculateGmfPort.calculate(
                false,
                withdrawAmount
        )).thenReturn(gmf);

        when(registerWithdrawTransactionPort.register(
                10,
                withdrawAmount,
                userId
        )).thenReturn(100);

        WithdrawAllResponse response =
                withdrawAllUseCase.withdraw(userId, request);

        ArgumentCaptor<Account> captor =
                ArgumentCaptor.forClass(Account.class);

        verify(accountRepository)
                .save(captor.capture());

        Account updatedAccount = captor.getValue();

        assertEquals(
                0,
                BigDecimal.ZERO.compareTo(updatedAccount.getBalance())
        );

        assertEquals(
                0,
                BigDecimal.ZERO.compareTo(updatedAccount.getAvailableBalance())
        );

        assertEquals(
                0,
                withdrawAmount.compareTo(response.getWithdraw())
        );

        verify(registerGmfChargePort)
                .register(100, 10, gmf);
    }

    @Test
    void shouldThrowExceptionWhenAvailableBalanceIsZero() {

        WithdrawAllRequest request =
                mock(WithdrawAllRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountId(10)
                .accountStatus(AccountStatus.ACTIVE)
                .availableBalance(BigDecimal.ZERO)
                .clientId(client)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(147258369);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(clientRepository.findByIdentifyNumber(147258369))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.of(account));

        assertThrows(
                InsufficientBalanceException.class,
                () -> withdrawAllUseCase.withdraw(2, request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenAccountIsCanceled() {

        WithdrawAllRequest request =
                mock(WithdrawAllRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountStatus(AccountStatus.CANCELED)
                .clientId(client)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(147258369);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(clientRepository.findByIdentifyNumber(147258369))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.of(account));

        assertThrows(
                AccountCanceledException.class,
                () -> withdrawAllUseCase.withdraw(2, request)
        );
    }

    @Test
    void shouldThrowExceptionWhenAccountIsInactive() {

        WithdrawAllRequest request =
                mock(WithdrawAllRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account account = Account.builder()
                .accountStatus(AccountStatus.INACTIVE)
                .clientId(client)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(147258369);

        when(request.getAccountNumber())
                .thenReturn("5312345678");

        when(clientRepository.findByIdentifyNumber(147258369))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5312345678"))
                .thenReturn(Optional.of(account));

        assertThrows(
                AccountInactiveException.class,
                () -> withdrawAllUseCase.withdraw(2, request)
        );
    }
}