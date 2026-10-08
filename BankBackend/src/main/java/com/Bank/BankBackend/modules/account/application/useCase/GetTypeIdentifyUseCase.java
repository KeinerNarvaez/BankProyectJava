package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.port.in.GetTypeIdentifyPort;
import com.Bank.BankBackend.modules.account.domain.exception.TypeIdentitiesNotExistException;
import com.Bank.BankBackend.modules.account.domain.model.TypeIdentify;
import com.Bank.BankBackend.modules.account.domain.repository.TypeIdentifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GetTypeIdentifyUseCase implements GetTypeIdentifyPort {

    private final TypeIdentifyRepository typeIdentifyRepository;

    @Override
    public List<TypeIdentify> getAllIdentify() {
        List<TypeIdentify> list = typeIdentifyRepository.findAll();
        if(list.isEmpty()){
            throw new TypeIdentitiesNotExistException();
        }
        return list;
    }
}
