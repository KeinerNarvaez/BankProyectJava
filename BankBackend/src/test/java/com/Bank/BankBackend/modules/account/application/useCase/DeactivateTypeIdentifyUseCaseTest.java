package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.DeactivateTypeIdentifyRequest;
import com.Bank.BankBackend.modules.account.domain.exception.TypeIdentifyInactiveException;
import com.Bank.BankBackend.modules.account.domain.exception.TypeIdentifyNotFoundException;
import com.Bank.BankBackend.modules.account.domain.model.TypeIdentify;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import com.Bank.BankBackend.modules.account.domain.repository.TypeIdentifyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeactivateTypeIdentifyUseCaseTest {

    @Mock
    private TypeIdentifyRepository typeIdentifyRepository;

    @InjectMocks
    private DeactivateTypeIdentifyUseCase deactivateTypeIdentifyUseCase;

    @Test
    void shouldDeactivateTypeIdentifySuccessfully() {

        String name = "CC";

        DeactivateTypeIdentifyRequest request =
                mock(DeactivateTypeIdentifyRequest.class);

        TypeIdentify typeIdentify = TypeIdentify.builder()
                .name(name)
                .status(TypeStatus.ACTIVE)
                .build();

        when(request.getName())
                .thenReturn(name);

        when(typeIdentifyRepository.findByName(name))
                .thenReturn(Optional.of(typeIdentify));

        deactivateTypeIdentifyUseCase.deactivate(request);

        ArgumentCaptor<TypeIdentify> captor =
                ArgumentCaptor.forClass(TypeIdentify.class);

        verify(typeIdentifyRepository)
                .save(captor.capture());

        TypeIdentify updatedType = captor.getValue();

        assertEquals(
                TypeStatus.INACTIVE,
                updatedType.getStatus()
        );
    }

    @Test
    void shouldThrowExceptionWhenTypeIdentifyDoesNotExist() {

        DeactivateTypeIdentifyRequest request =
                mock(DeactivateTypeIdentifyRequest.class);

        when(request.getName())
                .thenReturn("CC");

        when(typeIdentifyRepository.findByName("CC"))
                .thenReturn(Optional.empty());

        assertThrows(
                TypeIdentifyNotFoundException.class,
                () -> deactivateTypeIdentifyUseCase.deactivate(request)
        );

        verify(typeIdentifyRepository, never())
                .save(any(TypeIdentify.class));
    }

    @Test
    void shouldThrowExceptionWhenTypeIdentifyIsAlreadyInactive() {

        DeactivateTypeIdentifyRequest request =
                mock(DeactivateTypeIdentifyRequest.class);

        TypeIdentify typeIdentify = TypeIdentify.builder()
                .name("CC")
                .status(TypeStatus.INACTIVE)
                .build();

        when(request.getName())
                .thenReturn("CC");

        when(typeIdentifyRepository.findByName("CC"))
                .thenReturn(Optional.of(typeIdentify));

        assertThrows(
                TypeIdentifyInactiveException.class,
                () -> deactivateTypeIdentifyUseCase.deactivate(request)
        );

        verify(typeIdentifyRepository, never())
                .save(any(TypeIdentify.class));
    }
}