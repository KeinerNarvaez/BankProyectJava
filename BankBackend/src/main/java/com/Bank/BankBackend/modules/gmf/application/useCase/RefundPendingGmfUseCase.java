package com.Bank.BankBackend.modules.gmf.application.useCase;

import com.Bank.BankBackend.modules.gmf.application.port.in.RefundPendingGmfPort;
import com.Bank.BankBackend.modules.gmf.domain.model.GmfHistory;
import com.Bank.BankBackend.modules.gmf.domain.model.GmfOperationType;
import com.Bank.BankBackend.modules.gmf.domain.repository.GmfHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RefundPendingGmfUseCase implements RefundPendingGmfPort {

    private final GmfHistoryRepository gmfHistoryRepository;

    @Transactional
    @Override
    public BigDecimal refund(Integer accountId) {

        List<GmfHistory> pendingCharges = gmfHistoryRepository.findPendingChargesByAccountId(accountId);

        BigDecimal totalRefund = pendingCharges.stream()
                .map(GmfHistory::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pendingCharges.forEach(history -> {

            GmfHistory updatedHistory = history.toBuilder()
                    .operationType(GmfOperationType.REFUND)
                    .refundedAt(LocalDateTime.now())
                    .build();

            gmfHistoryRepository.save(updatedHistory);
        });

        return totalRefund;
    }
}