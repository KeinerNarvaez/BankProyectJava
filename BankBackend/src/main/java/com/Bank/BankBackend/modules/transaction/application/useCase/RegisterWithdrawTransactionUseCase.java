package com.Bank.BankBackend.modules.transaction.application.useCase;

import com.Bank.BankBackend.modules.transaction.application.port.in.RegisterWithdrawTransactionPort;
import com.Bank.BankBackend.modules.transaction.domain.model.Transaction;
import com.Bank.BankBackend.modules.transaction.domain.model.TransactionType;
import com.Bank.BankBackend.modules.transaction.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegisterWithdrawTransactionUseCase implements RegisterWithdrawTransactionPort {

    private final TransactionRepository transactionRepository;
    @Transactional
    @Override
    public Integer register(Integer accountId, BigDecimal amount, Integer userId) {

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.WITHDRAW)
                .amount(amount)
                .transactionDate(LocalDateTime.now())
                .originAccountId(accountId)
                .userId(userId)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return savedTransaction.getTransactionId();
    }
}
