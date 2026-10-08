package com.Bank.BankBackend.modules.gmf.application.useCase;

import com.Bank.BankBackend.modules.gmf.application.port.in.RegisterGmfChargePort;
import com.Bank.BankBackend.modules.gmf.domain.model.GmfHistory;
import com.Bank.BankBackend.modules.gmf.domain.model.GmfOperationType;
import com.Bank.BankBackend.modules.gmf.domain.repository.GmfHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegisterGmfChargeUseCase implements RegisterGmfChargePort {

    private final GmfHistoryRepository gmfHistoryRepository;

    @Override
    public void register(
            Integer transactionId,
            Integer accountId,
            BigDecimal amount
    ) {

        GmfHistory history = GmfHistory.builder()
                .transactionId(transactionId)
                .accountId(accountId)
                .amount(amount)
                .operationType(GmfOperationType.CHARGE)
                .createdAt(LocalDateTime.now())
                .build();

        gmfHistoryRepository.save(history);
    }
}