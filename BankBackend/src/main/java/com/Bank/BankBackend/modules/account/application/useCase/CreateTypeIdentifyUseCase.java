package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.CreateTypeIdentifyRequest;
import com.Bank.BankBackend.modules.account.application.port.in.CreateTypeIdentifyPort;
import com.Bank.BankBackend.modules.account.domain.exception.TypeIdentifyNameAlreadyExistsException;
import com.Bank.BankBackend.modules.account.domain.model.TypeIdentify;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import com.Bank.BankBackend.modules.account.domain.repository.TypeIdentifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTypeIdentifyUseCase implements CreateTypeIdentifyPort {

    private final TypeIdentifyRepository typeIdentifyRepository;

    @Override
    public void create(CreateTypeIdentifyRequest request) {
        if(typeIdentifyRepository.findByName(request.getName()).isPresent()){
            throw new TypeIdentifyNameAlreadyExistsException();
        }

        TypeIdentify typeIdentify = TypeIdentify.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(TypeStatus.ACTIVE)
                .build();

        typeIdentifyRepository.save(typeIdentify);
    }
}
