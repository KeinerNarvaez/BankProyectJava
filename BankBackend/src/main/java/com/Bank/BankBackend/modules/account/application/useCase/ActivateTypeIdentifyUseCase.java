package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.ActivateTypeIdentifyRequest;
import com.Bank.BankBackend.modules.account.application.port.in.ActivateTypeIdentifyPort;
import com.Bank.BankBackend.modules.account.domain.exception.TypeIdentifyActiveException;
import com.Bank.BankBackend.modules.account.domain.exception.TypeIdentifyNotFoundException;
import com.Bank.BankBackend.modules.account.domain.model.TypeIdentify;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import com.Bank.BankBackend.modules.account.domain.repository.TypeIdentifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateTypeIdentifyUseCase implements ActivateTypeIdentifyPort {

    private final TypeIdentifyRepository typeIdentifyRepository;

    @Override
    public void activate(ActivateTypeIdentifyRequest request) {

        TypeIdentify type =
                typeIdentifyRepository.findByName(request.getName())
                        .orElseThrow(TypeIdentifyNotFoundException::new);

        if(type.getStatus().equals(TypeStatus.ACTIVE)){
            throw new TypeIdentifyActiveException();
        }

        TypeIdentify update = type.toBuilder()
                .status(TypeStatus.ACTIVE)
                .build();

        typeIdentifyRepository.save(update);
    }
}
