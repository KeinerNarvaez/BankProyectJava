package com.Bank.BankBackend.modules.transaction.application.useCase;

import com.Bank.BankBackend.modules.transaction.application.port.in.RegisterTransferPort;
import com.Bank.BankBackend.modules.transaction.domain.model.Movement;
import com.Bank.BankBackend.modules.transaction.domain.model.MovementType;
import com.Bank.BankBackend.modules.transaction.domain.model.Transaction;
import com.Bank.BankBackend.modules.transaction.domain.model.TransactionType;
import com.Bank.BankBackend.modules.transaction.domain.repository.MovementRepository;
import com.Bank.BankBackend.modules.transaction.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegisterTransferUseCase implements RegisterTransferPort {

    private final TransactionRepository transactionRepository;
    private final MovementRepository movementRepository;

    @Transactional
    @Override
    public Integer registerTransfer(Integer originAccountId, Integer destinationAccountId, BigDecimal amount, Integer userId,String description) {

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.TRANSFER)
                .amount(amount)
                .transactionDate(LocalDateTime.now())
                .originAccountId(originAccountId)
                .description(description)
                .destinationAccountId(destinationAccountId)
                .userId(userId)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        Movement debitMovement = Movement.builder()
                .transactionId(savedTransaction)
                .accountId(originAccountId)
                .movementType(MovementType.DEBIT)
                .description(description)
                .amount(amount)
                .createdAt(LocalDateTime.now())
                .build();

        Movement creditMovement = Movement.builder()
                .transactionId(savedTransaction)
                .accountId(destinationAccountId)
                .movementType(MovementType.CREDIT)
                .description(description)
                .amount(amount)
                .createdAt(LocalDateTime.now())
                .build();

        movementRepository.save(debitMovement);
        movementRepository.save(creditMovement);

        return savedTransaction.getTransactionId();
    }
}
