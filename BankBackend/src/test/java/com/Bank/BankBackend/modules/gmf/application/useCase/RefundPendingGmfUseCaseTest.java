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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefundPendingGmfUseCaseTest {

    @Mock
    private GmfHistoryRepository gmfHistoryRepository;

    @InjectMocks
    private RefundPendingGmfUseCase refundPendingGmfUseCase;

    @Test
    void shouldRefundPendingGmfSuccessfully() {

        Integer accountId = 10;

        GmfHistory charge1 = GmfHistory.builder()
                .accountId(accountId)
                .amount(new BigDecimal("400"))
                .operationType(GmfOperationType.CHARGE)
                .build();

        GmfHistory charge2 = GmfHistory.builder()
                .accountId(accountId)
                .amount(new BigDecimal("200"))
                .operationType(GmfOperationType.CHARGE)
                .build();

        when(gmfHistoryRepository
                .findPendingChargesByAccountId(accountId))
                .thenReturn(List.of(
                        charge1,
                        charge2
                ));

        BigDecimal result =
                refundPendingGmfUseCase.refund(accountId);

        assertEquals(
                0,
                new BigDecimal("600").compareTo(result)
        );

        ArgumentCaptor<GmfHistory> captor =
                ArgumentCaptor.forClass(GmfHistory.class);

        verify(gmfHistoryRepository, times(2))
                .save(captor.capture());

        List<GmfHistory> savedHistories =
                captor.getAllValues();

        assertEquals(
                GmfOperationType.REFUND,
                savedHistories.get(0).getOperationType()
        );

        assertEquals(
                GmfOperationType.REFUND,
                savedHistories.get(1).getOperationType()
        );

        assertNotNull(
                savedHistories.get(0).getRefundedAt()
        );

        assertNotNull(
                savedHistories.get(1).getRefundedAt()
        );
    }

    @Test
    void shouldReturnZeroWhenThereAreNoPendingCharges() {

        Integer accountId = 10;

        when(gmfHistoryRepository
                .findPendingChargesByAccountId(accountId))
                .thenReturn(List.of());

        BigDecimal result =
                refundPendingGmfUseCase.refund(accountId);

        assertEquals(
                0,
                BigDecimal.ZERO.compareTo(result)
        );

        verify(gmfHistoryRepository, never())
                .save(any(GmfHistory.class));
    }
}