package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.DeactivateTypeIdentifyRequest;
import com.Bank.BankBackend.modules.account.application.port.in.DeactivateTypeIdentifyPort;
import com.Bank.BankBackend.modules.account.domain.exception.TypeIdentifyInactiveException;
import com.Bank.BankBackend.modules.account.domain.exception.TypeIdentifyNotFoundException;
import com.Bank.BankBackend.modules.account.domain.model.TypeIdentify;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import com.Bank.BankBackend.modules.account.domain.repository.TypeIdentifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeactivateTypeIdentifyUseCase implements DeactivateTypeIdentifyPort {

    private final TypeIdentifyRepository typeIdentifyRepository;

    @Override
    public void deactivate(DeactivateTypeIdentifyRequest request) {

        TypeIdentify type =
                typeIdentifyRepository.findByName(request.getName())
                        .orElseThrow(TypeIdentifyNotFoundException::new);

        if(type.getStatus().equals(TypeStatus.INACTIVE)){
            throw new TypeIdentifyInactiveException();
        }

        TypeIdentify update = type.toBuilder()
                .status(TypeStatus.INACTIVE)
                .build();

        typeIdentifyRepository.save(update);
    }
}
