package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.TransferRequest;
import com.Bank.BankBackend.modules.account.application.port.in.TransferPort;
import com.Bank.BankBackend.modules.account.domain.exception.*;
import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.repository.AccountRepository;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import com.Bank.BankBackend.modules.gmf.application.port.in.CalculateGmfPort;
import com.Bank.BankBackend.modules.gmf.application.port.in.RegisterGmfChargePort;

import com.Bank.BankBackend.modules.transaction.application.port.in.RegisterTransferPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferUseCase implements TransferPort {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    private final CalculateGmfPort calculateGmfPort;
    private final RegisterGmfChargePort registerGmfChargePort;

    private final RegisterTransferPort registerTransferPort;

    @Transactional
    @Override
        public void transfer(Integer userId,TransferRequest request) {

        Client client = clientRepository
                .findByIdentifyNumber(request.getIdentifyNumber())
                .orElseThrow(() ->
                        new ClientNotFoundException(
                                request.getIdentifyNumber()
                        )
                );

        Account originAccount = accountRepository
                .findByAccountNumber(request.getOriginAccountNumber())
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                request.getOriginAccountNumber()
                        )
                );

        Account destinationAccount = accountRepository
                .findByAccountNumber(request.getDestinationAccountNumber())
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                request.getDestinationAccountNumber()
                        )
                );

        if (!originAccount.getClientId().getClientId().equals(client.getClientId())) {
            throw new AccountDoesNotBelongToClientException();
        }

        if (originAccount.getAccountStatus() == AccountStatus.CANCELED) {
            throw new AccountCanceledException();
        }

        if (originAccount.getAccountStatus() == AccountStatus.INACTIVE) {
            throw new AccountInactiveException();
        }

        if (destinationAccount.getAccountStatus() == AccountStatus.CANCELED) {
            throw new AccountCanceledException();
        }

        if (destinationAccount.getAccountStatus() == AccountStatus.INACTIVE) {
            throw new AccountInactiveException();
        }

        if (originAccount.getAccountId()
                .equals(destinationAccount.getAccountId())) {
            throw new SameAccountTransferException();
        }

        BigDecimal amount = request.getAmount();

        BigDecimal gmf = calculateGmfPort.calculate(
                originAccount.getGmfExempt(),
                amount
        );

        BigDecimal totalDebit = amount.add(gmf);

        if (originAccount.getAvailableBalance()
                .compareTo(totalDebit) < 0) {
            throw new InsufficientBalanceException();
        }

        Account updatedOrigin = originAccount.toBuilder()
                .balance(
                        originAccount.getBalance()
                                .subtract(totalDebit)
                )
                .availableBalance(
                        originAccount.getAvailableBalance()
                                .subtract(totalDebit)
                )
                .updatedAt(LocalDateTime.now())
                .build();

        Account updatedDestination = destinationAccount.toBuilder()
                .balance(
                        destinationAccount.getBalance()
                                .add(amount)
                )
                .availableBalance(
                        destinationAccount.getAvailableBalance()
                                .add(amount)
                )
                .updatedAt(LocalDateTime.now())
                .build();

        accountRepository.save(updatedOrigin);
        accountRepository.save(updatedDestination);

        Integer transactionId =
                registerTransferPort.registerTransfer(
                        originAccount.getAccountId(),
                        destinationAccount.getAccountId(),
                        amount,
                        userId,
                        request.getDescription()
                );

        if (gmf.compareTo(BigDecimal.ZERO) > 0) {
            registerGmfChargePort.register(
                    transactionId,
                    originAccount.getAccountId(),
                    gmf
            );
        }
    }
}