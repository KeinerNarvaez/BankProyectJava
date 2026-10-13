package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.CreateClientRequest;
import com.Bank.BankBackend.modules.account.domain.exception.ClientEmailAlreadyExistsException;
import com.Bank.BankBackend.modules.account.domain.exception.ClientIdentifyNumberAlreadyExistsException;
import com.Bank.BankBackend.modules.account.domain.exception.ClientUnderageException;
import com.Bank.BankBackend.modules.account.domain.exception.TypeIdentifyNotFoundException;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.model.TypeIdentify;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import com.Bank.BankBackend.modules.account.domain.repository.TypeIdentifyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateClientUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private TypeIdentifyRepository typeIdentifyRepository;

    @InjectMocks
    private CreateClientUseCase createClientUseCase;

    @Test
    void shouldCreateClientSuccessfully() {

        Integer userId = 2;
        Integer identifyNumber = 456987123;

        CreateClientRequest request =
                mock(CreateClientRequest.class);

        TypeIdentify typeIdentify = TypeIdentify.builder()
                .name("CC")
                .status(TypeStatus.ACTIVE)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(request.getEmail())
                .thenReturn("cliente@gmail.com");

        when(request.getBirthday())
                .thenReturn(LocalDate.now().minusYears(25));

        when(request.getTypeIdentifyName())
                .thenReturn("CC");

        when(request.getNames())
                .thenReturn("Carlos");

        when(request.getLastNames())
                .thenReturn("Perez");

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.empty());

        when(clientRepository.findByEmail("cliente@gmail.com"))
                .thenReturn(Optional.empty());

        when(typeIdentifyRepository.findByName("CC"))
                .thenReturn(Optional.of(typeIdentify));

        createClientUseCase.create(
                userId,
                request
        );

        ArgumentCaptor<Client> captor =
                ArgumentCaptor.forClass(Client.class);

        verify(clientRepository)
                .save(captor.capture());

        Client savedClient = captor.getValue();

        assertEquals(
                identifyNumber,
                savedClient.getIdentifyNumber()
        );

        assertEquals(
                "cliente@gmail.com",
                savedClient.getEmail()
        );

        assertEquals(
                "Carlos",
                savedClient.getNames()
        );

        assertEquals(
                "Perez",
                savedClient.getLastNames()
        );

        assertEquals(
                TypeStatus.ACTIVE,
                savedClient.getStatus()
        );

        assertEquals(
                typeIdentify,
                savedClient.getTypeIdentify()
        );

        assertEquals(
                userId,
                savedClient.getUserCreation()
        );

        assertNotNull(
                savedClient.getCreatedAt()
        );

        assertNotNull(
                savedClient.getUpdatedAt()
        );
    }

    @Test
    void shouldThrowExceptionWhenClientIsUnderage() {

        CreateClientRequest request =
                mock(CreateClientRequest.class);

        when(request.getBirthday())
                .thenReturn(LocalDate.now().minusYears(17));

        assertThrows(
                ClientUnderageException.class,
                () -> createClientUseCase.create(
                        2,
                        request
                )
        );

        verify(clientRepository, never())
                .save(any(Client.class));
    }

    @Test
    void shouldThrowExceptionWhenIdentifyNumberAlreadyExists() {

        Integer identifyNumber = 456987123;

        CreateClientRequest request =
                mock(CreateClientRequest.class);

        Client existingClient =
                Client.builder()
                        .clientId(1)
                        .identifyNumber(identifyNumber)
                        .build();

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(request.getEmail())
                .thenReturn("cliente@gmail.com");

        when(request.getBirthday())
                .thenReturn(LocalDate.now().minusYears(25));

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.of(existingClient));

        assertThrows(
                ClientIdentifyNumberAlreadyExistsException.class,
                () -> createClientUseCase.create(
                        2,
                        request
                )
        );

        verify(clientRepository, never())
                .save(any(Client.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        Integer identifyNumber = 456987123;

        CreateClientRequest request =
                mock(CreateClientRequest.class);

        Client existingClient =
                Client.builder()
                        .clientId(1)
                        .email("cliente@gmail.com")
                        .build();

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(request.getEmail())
                .thenReturn("cliente@gmail.com");

        when(request.getBirthday())
                .thenReturn(LocalDate.now().minusYears(25));

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.empty());

        when(clientRepository.findByEmail("cliente@gmail.com"))
                .thenReturn(Optional.of(existingClient));

        assertThrows(
                ClientEmailAlreadyExistsException.class,
                () -> createClientUseCase.create(
                        2,
                        request
                )
        );

        verify(clientRepository, never())
                .save(any(Client.class));
    }

    @Test
    void shouldThrowExceptionWhenTypeIdentifyDoesNotExist() {

        Integer identifyNumber = 456987123;

        CreateClientRequest request =
                mock(CreateClientRequest.class);

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(request.getEmail())
                .thenReturn("cliente@gmail.com");

        when(request.getBirthday())
                .thenReturn(LocalDate.now().minusYears(25));

        when(request.getTypeIdentifyName())
                .thenReturn("CC");

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.empty());

        when(clientRepository.findByEmail("cliente@gmail.com"))
                .thenReturn(Optional.empty());

        when(typeIdentifyRepository.findByName("CC"))
                .thenReturn(Optional.empty());

        assertThrows(
                TypeIdentifyNotFoundException.class,
                () -> createClientUseCase.create(
                        2,
                        request
                )
        );

        verify(clientRepository, never())
                .save(any(Client.class));
    }
}