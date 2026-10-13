package com.Bank.BankBackend.modules.transaction.application.useCase;

import com.Bank.BankBackend.modules.transaction.domain.model.Transaction;
import com.Bank.BankBackend.modules.transaction.domain.model.TransactionType;
import com.Bank.BankBackend.modules.transaction.domain.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterWithdrawTransactionUseCaseTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RegisterWithdrawTransactionUseCase registerWithdrawTransactionUseCase;

    @Test
    void shouldRegisterWithdrawTransactionSuccessfully() {

        Integer accountId = 10;
        Integer userId = 2;
        Integer transactionId = 100;

        BigDecimal amount =
                new BigDecimal("50000");

        Transaction savedTransaction =
                Transaction.builder()
                        .transactionId(transactionId)
                        .transactionType(TransactionType.WITHDRAW)
                        .amount(amount)
                        .originAccountId(accountId)
                        .userId(userId)
                        .build();

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(savedTransaction);

        Integer result =
                registerWithdrawTransactionUseCase.register(
                        accountId,
                        amount,
                        userId
                );

        assertEquals(
                transactionId,
                result
        );

        ArgumentCaptor<Transaction> captor =
                ArgumentCaptor.forClass(Transaction.class);

        verify(transactionRepository)
                .save(captor.capture());

        Transaction transactionToSave =
                captor.getValue();

        assertEquals(
                TransactionType.WITHDRAW,
                transactionToSave.getTransactionType()
        );

        assertEquals(
                0,
                amount.compareTo(transactionToSave.getAmount())
        );

        assertEquals(
                accountId,
                transactionToSave.getOriginAccountId()
        );

        assertEquals(
                userId,
                transactionToSave.getUserId()
        );

        assertNotNull(
                transactionToSave.getTransactionDate()
        );
    }
}