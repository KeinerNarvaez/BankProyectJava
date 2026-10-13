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
class RegisterDepositTransactionTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RegisterDepositTransaction registerDepositTransaction;

    @Test
    void shouldRegisterDepositTransactionSuccessfully() {

        Integer accountId = 10;
        Integer userId = 2;
        BigDecimal amount = new BigDecimal("50000");

        registerDepositTransaction.register(
                accountId,
                amount,
                userId
        );

        ArgumentCaptor<Transaction> captor =
                ArgumentCaptor.forClass(Transaction.class);

        verify(transactionRepository)
                .save(captor.capture());

        Transaction savedTransaction =
                captor.getValue();

        assertEquals(
                TransactionType.DEPOSIT,
                savedTransaction.getTransactionType()
        );

        assertEquals(
                0,
                amount.compareTo(savedTransaction.getAmount())
        );

        assertEquals(
                accountId,
                savedTransaction.getOriginAccountId()
        );

        assertEquals(
                userId,
                savedTransaction.getUserId()
        );

        assertNotNull(
                savedTransaction.getTransactionDate()
        );
    }
}