package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.WithdrawAllRequest;
import com.Bank.BankBackend.modules.account.application.dto.response.WithdrawAllResponse;
import com.Bank.BankBackend.modules.account.application.port.in.WithdrawAllPort;
import com.Bank.BankBackend.modules.account.domain.exception.*;
import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.repository.AccountRepository;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import com.Bank.BankBackend.modules.gmf.application.port.in.CalculateGmfPort;
import com.Bank.BankBackend.modules.gmf.application.port.in.RegisterGmfChargePort;
import com.Bank.BankBackend.modules.transaction.application.port.in.RegisterWithdrawTransactionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WithdrawAllUseCase implements WithdrawAllPort {
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    private final CalculateGmfPort calculateGmfPort;
    private final RegisterGmfChargePort registerGmfChargePort;
    private final RegisterWithdrawTransactionPort registerWithdrawTransactionPort;

    @Transactional
    @Override
    public WithdrawAllResponse withdraw(Integer userId, WithdrawAllRequest request) {

        Client client = clientRepository
                .findByIdentifyNumber(request.getIdentifyNumber())
                .orElseThrow(() ->
                        new ClientNotFoundException(
                                request.getIdentifyNumber()
                        )
                );

        Account account = accountRepository
                .findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                request.getAccountNumber()
                        )
                );

        if (!account.getClientId().getClientId().equals(client.getClientId())) {
            throw new AccountDoesNotBelongToClientException();
        }

        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new AccountInactiveException();
        }

        BigDecimal availableBalance = account.getAvailableBalance();

        if (availableBalance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InsufficientBalanceException();
        }

        BigDecimal withdrawAmount =
                calculateGmfPort.calculateMaxWithdrawAmount(
                        account.getGmfExempt(),
                        availableBalance
                );

        BigDecimal gmf =
                calculateGmfPort.calculate(
                        account.getGmfExempt(),
                        withdrawAmount
                );

        Account updatedAccount = account.toBuilder()
                .balance(BigDecimal.ZERO)
                .availableBalance(BigDecimal.ZERO)
                .updatedAt(LocalDateTime.now())
                .build();

        accountRepository.save(updatedAccount);

        Integer transactionId =
                registerWithdrawTransactionPort.register(
                        account.getAccountId(),
                        withdrawAmount,
                        userId
                );

        if (gmf.compareTo(BigDecimal.ZERO) > 0) {

            registerGmfChargePort.register(
                    transactionId,
                    account.getAccountId(),
                    gmf
            );
        }

        return WithdrawAllResponse.builder()
                .withdraw(withdrawAmount)
                .build();
    }
}
