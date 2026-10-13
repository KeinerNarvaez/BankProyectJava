package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.TransferRequest;
import com.Bank.BankBackend.modules.account.domain.exception.*;
import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.repository.AccountRepository;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import com.Bank.BankBackend.modules.gmf.application.port.in.CalculateGmfPort;
import com.Bank.BankBackend.modules.gmf.application.port.in.RegisterGmfChargePort;
import com.Bank.BankBackend.modules.transaction.application.port.in.RegisterTransferPort;
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
class TransferUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CalculateGmfPort calculateGmfPort;

    @Mock
    private RegisterGmfChargePort registerGmfChargePort;

    @Mock
    private RegisterTransferPort registerTransferPort;

    @InjectMocks
    private TransferUseCase transferUseCase;

    @Test
    void shouldTransferSuccessfullyWithGmf() {

        Integer userId = 2;
        Integer identifyNumber = 258369147;
        BigDecimal amount = new BigDecimal("50000");
        BigDecimal gmf = new BigDecimal("200");

        TransferRequest request = mock(TransferRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account origin = Account.builder()
                .accountId(10)
                .accountStatus(AccountStatus.ACTIVE)
                .balance(new BigDecimal("100000"))
                .availableBalance(new BigDecimal("100000"))
                .gmfExempt(false)
                .clientId(client)
                .build();

        Account destination = Account.builder()
                .accountId(20)
                .accountStatus(AccountStatus.ACTIVE)
                .balance(new BigDecimal("30000"))
                .availableBalance(new BigDecimal("30000"))
                .clientId(Client.builder().clientId(2).build())
                .build();

        when(request.getIdentifyNumber()).thenReturn(identifyNumber);
        when(request.getOriginAccountNumber()).thenReturn("5311111111");
        when(request.getDestinationAccountNumber()).thenReturn("3311111111");
        when(request.getAmount()).thenReturn(amount);
        when(request.getDescription()).thenReturn("Transferencia");

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5311111111"))
                .thenReturn(Optional.of(origin));

        when(accountRepository.findByAccountNumber("3311111111"))
                .thenReturn(Optional.of(destination));

        when(calculateGmfPort.calculate(false, amount))
                .thenReturn(gmf);

        when(registerTransferPort.registerTransfer(
                10,
                20,
                amount,
                userId,
                "Transferencia"
        )).thenReturn(100);

        transferUseCase.transfer(userId, request);

        ArgumentCaptor<Account> captor =
                ArgumentCaptor.forClass(Account.class);

        verify(accountRepository, times(2))
                .save(captor.capture());

        List<Account> accounts = captor.getAllValues();

        Account updatedOrigin = accounts.get(0);
        Account updatedDestination = accounts.get(1);

        assertEquals(
                0,
                new BigDecimal("49800")
                        .compareTo(updatedOrigin.getBalance())
        );

        assertEquals(
                0,
                new BigDecimal("80000")
                        .compareTo(updatedDestination.getBalance())
        );

        verify(registerTransferPort).registerTransfer(
                10,
                20,
                amount,
                userId,
                "Transferencia"
        );

        verify(registerGmfChargePort)
                .register(100, 10, gmf);
    }

    @Test
    void shouldThrowExceptionWhenSameAccount() {

        TransferRequest request = mock(TransferRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account origin = Account.builder()
                .accountId(10)
                .accountStatus(AccountStatus.ACTIVE)
                .clientId(client)
                .build();

        Account destination = Account.builder()
                .accountId(10)
                .accountStatus(AccountStatus.ACTIVE)
                .build();

        when(request.getIdentifyNumber()).thenReturn(258369147);
        when(request.getOriginAccountNumber()).thenReturn("5311111111");
        when(request.getDestinationAccountNumber()).thenReturn("5311111111");

        when(clientRepository.findByIdentifyNumber(258369147))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5311111111"))
                .thenReturn(Optional.of(origin))
                .thenReturn(Optional.of(destination));

        assertThrows(
                SameAccountTransferException.class,
                () -> transferUseCase.transfer(2, request)
        );
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsInsufficient() {

        TransferRequest request = mock(TransferRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .build();

        Account origin = Account.builder()
                .accountId(10)
                .accountStatus(AccountStatus.ACTIVE)
                .balance(new BigDecimal("10000"))
                .availableBalance(new BigDecimal("10000"))
                .gmfExempt(false)
                .clientId(client)
                .build();

        Account destination = Account.builder()
                .accountId(20)
                .accountStatus(AccountStatus.ACTIVE)
                .build();

        BigDecimal amount = new BigDecimal("10000");

        when(request.getIdentifyNumber()).thenReturn(258369147);
        when(request.getOriginAccountNumber()).thenReturn("5311111111");
        when(request.getDestinationAccountNumber()).thenReturn("3311111111");
        when(request.getAmount()).thenReturn(amount);

        when(clientRepository.findByIdentifyNumber(258369147))
                .thenReturn(Optional.of(client));

        when(accountRepository.findByAccountNumber("5311111111"))
                .thenReturn(Optional.of(origin));

        when(accountRepository.findByAccountNumber("3311111111"))
                .thenReturn(Optional.of(destination));

        when(calculateGmfPort.calculate(false, amount))
                .thenReturn(new BigDecimal("40"));

        assertThrows(
                InsufficientBalanceException.class,
                () -> transferUseCase.transfer(2, request)
        );

        verify(accountRepository, never())
                .save(any(Account.class));

        verifyNoInteractions(registerTransferPort);
    }
}