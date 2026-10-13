package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.domain.exception.TypeIdentitiesNotExistException;
import com.Bank.BankBackend.modules.account.domain.model.TypeIdentify;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import com.Bank.BankBackend.modules.account.domain.repository.TypeIdentifyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetTypeIdentifyUseCaseTest {

    @Mock
    private TypeIdentifyRepository typeIdentifyRepository;

    @InjectMocks
    private GetTypeIdentifyUseCase getTypeIdentifyUseCase;

    @Test
    void shouldReturnAllTypeIdentifiesSuccessfully() {

        TypeIdentify cc = TypeIdentify.builder()
                .name("CC")
                .status(TypeStatus.ACTIVE)
                .build();

        TypeIdentify ce = TypeIdentify.builder()
                .name("CE")
                .status(TypeStatus.ACTIVE)
                .build();

        when(typeIdentifyRepository.findAll())
                .thenReturn(List.of(cc, ce));

        List<TypeIdentify> result =
                getTypeIdentifyUseCase.getAllIdentify();

        assertEquals(2, result.size());
        assertEquals("CC", result.get(0).getName());
        assertEquals("CE", result.get(1).getName());
    }

    @Test
    void shouldThrowExceptionWhenNoTypeIdentifiesExist() {

        when(typeIdentifyRepository.findAll())
                .thenReturn(List.of());

        assertThrows(
                TypeIdentitiesNotExistException.class,
                () -> getTypeIdentifyUseCase.getAllIdentify()
        );
    }
}