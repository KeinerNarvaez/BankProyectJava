package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.ActivateTypeIdentifyRequest;
import com.Bank.BankBackend.modules.account.domain.exception.TypeIdentifyActiveException;
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
class ActivateTypeIdentifyUseCaseTest {

    @Mock
    private TypeIdentifyRepository typeIdentifyRepository;

    @InjectMocks
    private ActivateTypeIdentifyUseCase activateTypeIdentifyUseCase;

    @Test
    void shouldActivateTypeIdentifySuccessfully() {

        // ARRANGE
        String name = "CC";

        ActivateTypeIdentifyRequest request =
                mock(ActivateTypeIdentifyRequest.class);

        TypeIdentify typeIdentify = TypeIdentify.builder()
                .name(name)
                .status(TypeStatus.INACTIVE)
                .build();

        when(request.getName())
                .thenReturn(name);

        when(typeIdentifyRepository.findByName(name))
                .thenReturn(Optional.of(typeIdentify));

        // ACT
        activateTypeIdentifyUseCase.activate(request);

        // ASSERT
        ArgumentCaptor<TypeIdentify> captor =
                ArgumentCaptor.forClass(TypeIdentify.class);

        verify(typeIdentifyRepository)
                .save(captor.capture());

        TypeIdentify updatedType = captor.getValue();

        assertEquals(
                TypeStatus.ACTIVE,
                updatedType.getStatus()
        );

        assertEquals(
                name,
                updatedType.getName()
        );
    }

    @Test
    void shouldThrowExceptionWhenTypeIdentifyDoesNotExist() {

        // ARRANGE
        String name = "CC";

        ActivateTypeIdentifyRequest request =
                mock(ActivateTypeIdentifyRequest.class);

        when(request.getName())
                .thenReturn(name);

        when(typeIdentifyRepository.findByName(name))
                .thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(
                TypeIdentifyNotFoundException.class,
                () -> activateTypeIdentifyUseCase.activate(request)
        );

        verify(typeIdentifyRepository, never())
                .save(any(TypeIdentify.class));
    }

    @Test
    void shouldThrowExceptionWhenTypeIdentifyIsAlreadyActive() {

        // ARRANGE
        String name = "CC";

        ActivateTypeIdentifyRequest request =
                mock(ActivateTypeIdentifyRequest.class);

        TypeIdentify typeIdentify = TypeIdentify.builder()
                .name(name)
                .status(TypeStatus.ACTIVE)
                .build();

        when(request.getName())
                .thenReturn(name);

        when(typeIdentifyRepository.findByName(name))
                .thenReturn(Optional.of(typeIdentify));

        // ACT & ASSERT
        assertThrows(
                TypeIdentifyActiveException.class,
                () -> activateTypeIdentifyUseCase.activate(request)
        );

        verify(typeIdentifyRepository, never())
                .save(any(TypeIdentify.class));
    }
}