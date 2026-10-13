package com.Bank.BankBackend.modules.transaction.application.useCase;

import com.Bank.BankBackend.modules.transaction.domain.model.Movement;
import com.Bank.BankBackend.modules.transaction.domain.model.MovementType;
import com.Bank.BankBackend.modules.transaction.domain.model.Transaction;
import com.Bank.BankBackend.modules.transaction.domain.model.TransactionType;
import com.Bank.BankBackend.modules.transaction.domain.repository.MovementRepository;
import com.Bank.BankBackend.modules.transaction.domain.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterTransferUseCaseTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private MovementRepository movementRepository;

    @InjectMocks
    private RegisterTransferUseCase registerTransferUseCase;

    @Test
    void shouldRegisterTransferSuccessfully() {

        Integer originAccountId = 10;
        Integer destinationAccountId = 20;
        Integer userId = 2;
        Integer transactionId = 100;

        BigDecimal amount =
                new BigDecimal("50000");

        String description =
                "Transferencia de prueba";

        Transaction savedTransaction =
                Transaction.builder()
                        .transactionId(transactionId)
                        .transactionType(TransactionType.TRANSFER)
                        .amount(amount)
                        .originAccountId(originAccountId)
                        .destinationAccountId(destinationAccountId)
                        .userId(userId)
                        .description(description)
                        .build();

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(savedTransaction);

        Integer result =
                registerTransferUseCase.registerTransfer(
                        originAccountId,
                        destinationAccountId,
                        amount,
                        userId,
                        description
                );

        assertEquals(
                transactionId,
                result
        );

        ArgumentCaptor<Transaction> transactionCaptor =
                ArgumentCaptor.forClass(Transaction.class);

        verify(transactionRepository)
                .save(transactionCaptor.capture());

        Transaction transactionToSave =
                transactionCaptor.getValue();

        assertEquals(
                TransactionType.TRANSFER,
                transactionToSave.getTransactionType()
        );

        assertEquals(
                originAccountId,
                transactionToSave.getOriginAccountId()
        );

        assertEquals(
                destinationAccountId,
                transactionToSave.getDestinationAccountId()
        );

        assertEquals(
                userId,
                transactionToSave.getUserId()
        );

        assertEquals(
                description,
                transactionToSave.getDescription()
        );

        assertEquals(
                0,
                amount.compareTo(transactionToSave.getAmount())
        );

        assertNotNull(
                transactionToSave.getTransactionDate()
        );

        ArgumentCaptor<Movement> movementCaptor =
                ArgumentCaptor.forClass(Movement.class);

        verify(movementRepository, times(2))
                .save(movementCaptor.capture());

        List<Movement> movements =
                movementCaptor.getAllValues();

        Movement debitMovement =
                movements.get(0);

        Movement creditMovement =
                movements.get(1);

        assertEquals(
                MovementType.DEBIT,
                debitMovement.getMovementType()
        );

        assertEquals(
                originAccountId,
                debitMovement.getAccountId()
        );

        assertEquals(
                savedTransaction,
                debitMovement.getTransactionId()
        );

        assertEquals(
                0,
                amount.compareTo(debitMovement.getAmount())
        );

        assertEquals(
                description,
                debitMovement.getDescription()
        );

        assertNotNull(
                debitMovement.getCreatedAt()
        );

        assertEquals(
                MovementType.CREDIT,
                creditMovement.getMovementType()
        );

        assertEquals(
                destinationAccountId,
                creditMovement.getAccountId()
        );

        assertEquals(
                savedTransaction,
                creditMovement.getTransactionId()
        );

        assertEquals(
                0,
                amount.compareTo(creditMovement.getAmount())
        );

        assertEquals(
                description,
                creditMovement.getDescription()
        );

        assertNotNull(
                creditMovement.getCreatedAt()
        );
    }
}