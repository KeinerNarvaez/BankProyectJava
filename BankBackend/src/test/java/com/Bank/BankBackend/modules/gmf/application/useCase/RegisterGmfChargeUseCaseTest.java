package com.Bank.BankBackend.modules.gmf.application.useCase;

import com.Bank.BankBackend.modules.gmf.domain.model.GmfHistory;
import com.Bank.BankBackend.modules.gmf.domain.model.GmfOperationType;
import com.Bank.BankBackend.modules.gmf.domain.repository.GmfHistoryRepository;
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
class RegisterGmfChargeUseCaseTest {

    @Mock
    private GmfHistoryRepository gmfHistoryRepository;

    @InjectMocks
    private RegisterGmfChargeUseCase registerGmfChargeUseCase;

    @Test
    void shouldRegisterGmfChargeSuccessfully() {

        Integer transactionId = 100;
        Integer accountId = 10;
        BigDecimal amount = new BigDecimal("400");

        registerGmfChargeUseCase.register(
                transactionId,
                accountId,
                amount
        );

        ArgumentCaptor<GmfHistory> captor =
                ArgumentCaptor.forClass(GmfHistory.class);

        verify(gmfHistoryRepository)
                .save(captor.capture());

        GmfHistory savedHistory = captor.getValue();

        assertEquals(
                transactionId,
                savedHistory.getTransactionId()
        );

        assertEquals(
                accountId,
                savedHistory.getAccountId()
        );

        assertEquals(
                0,
                amount.compareTo(savedHistory.getAmount())
        );

        assertEquals(
                GmfOperationType.CHARGE,
                savedHistory.getOperationType()
        );

        assertNotNull(savedHistory.getCreatedAt());
    }
}