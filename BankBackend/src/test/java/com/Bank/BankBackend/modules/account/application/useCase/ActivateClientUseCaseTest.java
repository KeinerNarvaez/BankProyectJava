package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.ActivateClientRequest;
import com.Bank.BankBackend.modules.account.domain.exception.ClientAlreadyActiveException;
import com.Bank.BankBackend.modules.account.domain.exception.ClientNotFoundException;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
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
class ActivateClientUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ActivateClientUseCase activateClientUseCase;

    @Test
    void shouldActivateClientSuccessfully() {

        // ARRANGE
        Integer identifyNumber = 456123789;

        ActivateClientRequest request =
                mock(ActivateClientRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .identifyNumber(identifyNumber)
                .status(TypeStatus.INACTIVE)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.of(client));

        // ACT
        activateClientUseCase.activateClient(request);

        // ASSERT
        ArgumentCaptor<Client> captor =
                ArgumentCaptor.forClass(Client.class);

        verify(clientRepository)
                .save(captor.capture());

        Client updatedClient = captor.getValue();

        assertEquals(
                TypeStatus.ACTIVE,
                updatedClient.getStatus()
        );

        assertNotNull(
                updatedClient.getUpdatedAt()
        );
    }

    @Test
    void shouldThrowExceptionWhenClientDoesNotExist() {

        // ARRANGE
        Integer identifyNumber = 456123789;

        ActivateClientRequest request =
                mock(ActivateClientRequest.class);

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(
                ClientNotFoundException.class,
                () -> activateClientUseCase.activateClient(request)
        );

        verify(clientRepository, never())
                .save(any(Client.class));
    }

    @Test
    void shouldThrowExceptionWhenClientIsAlreadyActive() {

        // ARRANGE
        Integer identifyNumber = 456123789;

        ActivateClientRequest request =
                mock(ActivateClientRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .identifyNumber(identifyNumber)
                .status(TypeStatus.ACTIVE)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.of(client));

        // ACT & ASSERT
        assertThrows(
                ClientAlreadyActiveException.class,
                () -> activateClientUseCase.activateClient(request)
        );

        verify(clientRepository, never())
                .save(any(Client.class));
    }
}