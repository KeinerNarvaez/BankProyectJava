package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.CreateTypeIdentifyRequest;
import com.Bank.BankBackend.modules.account.domain.exception.TypeIdentifyNameAlreadyExistsException;
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
class CreateTypeIdentifyUseCaseTest {

    @Mock
    private TypeIdentifyRepository typeIdentifyRepository;

    @InjectMocks
    private CreateTypeIdentifyUseCase createTypeIdentifyUseCase;

    @Test
    void shouldCreateTypeIdentifySuccessfully() {

        CreateTypeIdentifyRequest request =
                mock(CreateTypeIdentifyRequest.class);

        when(request.getName())
                .thenReturn("CC");

        when(request.getDescription())
                .thenReturn("Cedula de ciudadania");

        when(typeIdentifyRepository.findByName("CC"))
                .thenReturn(Optional.empty());

        createTypeIdentifyUseCase.create(request);

        ArgumentCaptor<TypeIdentify> captor =
                ArgumentCaptor.forClass(TypeIdentify.class);

        verify(typeIdentifyRepository)
                .save(captor.capture());

        TypeIdentify savedType = captor.getValue();

        assertEquals("CC", savedType.getName());
        assertEquals(
                "Cedula de ciudadania",
                savedType.getDescription()
        );

        assertEquals(
                TypeStatus.ACTIVE,
                savedType.getStatus()
        );
    }

    @Test
    void shouldThrowExceptionWhenTypeIdentifyAlreadyExists() {

        CreateTypeIdentifyRequest request =
                mock(CreateTypeIdentifyRequest.class);

        when(request.getName())
                .thenReturn("CC");

        when(typeIdentifyRepository.findByName("CC"))
                .thenReturn(Optional.of(
                        TypeIdentify.builder()
                                .name("CC")
                                .build()
                ));

        assertThrows(
                TypeIdentifyNameAlreadyExistsException.class,
                () -> createTypeIdentifyUseCase.create(request)
        );

        verify(typeIdentifyRepository, never())
                .save(any(TypeIdentify.class));
    }
}