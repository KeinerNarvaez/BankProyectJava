package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.CreateAccountRequest;
import com.Bank.BankBackend.modules.account.application.port.in.CreateAccountPort;
import com.Bank.BankBackend.modules.account.domain.exception.ClientNotExistException;
import com.Bank.BankBackend.modules.account.domain.exception.ClientNotFoundException;
import com.Bank.BankBackend.modules.account.domain.exception.ClientsNotExistException;
import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.model.TypeAccount;
import com.Bank.BankBackend.modules.account.domain.repository.AccountRepository;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class CreateAccountUseCase implements CreateAccountPort {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Transactional
    @Override
    public void create(Integer userCreationId,CreateAccountRequest request) {
        String accountNumber = generateAccountNumber(request.getTypeAccount());

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(ClientNotExistException::new);

        Account account = Account.builder()
                .accountNumber(accountNumber)
                .typeAccount(request.getTypeAccount())
                .accountStatus(AccountStatus.ACTIVE)
                .balance(BigDecimal.ZERO)
                .availableBalance(BigDecimal.ZERO)
                .gmfExempt(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .clientId(client)
                .userCreationId(userCreationId)
                .build();

        accountRepository.save(account);
    }

    private String generateAccountNumber(TypeAccount type) {
        String prefix = type == TypeAccount.SAVINGS ? "53" : "33";
        String accountNumber;

        do {
            long number = ThreadLocalRandom.current()
                    .nextLong(0,    100_000_000L);

            accountNumber = prefix +String.format("%08d", number);

        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }
}
