package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.ActivateGmfExemptionRequest;
import com.Bank.BankBackend.modules.account.application.port.in.ActivateGmfExemptionPort;
import com.Bank.BankBackend.modules.account.domain.exception.*;
import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.repository.AccountRepository;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import com.Bank.BankBackend.modules.gmf.application.port.in.RefundPendingGmfPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivateGmfExemptionUseCase implements ActivateGmfExemptionPort {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final RefundPendingGmfPort refundPendingGmfPort;

    @Override
    public void activate(ActivateGmfExemptionRequest request) {
        Client client = clientRepository
                .findByIdentifyNumber(request.getIdentifyNumber())
                .orElseThrow(() ->
                        new ClientNotFoundException(request.getIdentifyNumber())
                );

        Account account = accountRepository
                .findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() ->
                        new AccountNotFoundException(request.getAccountNumber())
                );

        if (!account.getClientId().getClientId().equals(client.getClientId())) {
            throw new AccountDoesNotBelongToClientException();
        }

        List<Account> accounts = accountRepository.findAllByClientId_ClientId(client.getClientId());

        boolean hasAnotherGmfExemptAccount = accounts.stream()
                .anyMatch(currentAccount ->
                        Boolean.TRUE.equals(currentAccount.getGmfExempt())
                                && !currentAccount.getAccountId().equals(account.getAccountId())
                );

        if (hasAnotherGmfExemptAccount) {
            throw new ClientAlreadyHasGmfExemptAccountException();
        }

        BigDecimal refund = refundPendingGmfPort.refund(account.getAccountId());

        Account updatedAccount = account.toBuilder()
                .gmfExempt(true)
                .balance(account.getBalance().add(refund))
                .availableBalance(account.getAvailableBalance().add(refund))
                .updatedAt(LocalDateTime.now())
                .build();

        accountRepository.save(updatedAccount);
    }
}
