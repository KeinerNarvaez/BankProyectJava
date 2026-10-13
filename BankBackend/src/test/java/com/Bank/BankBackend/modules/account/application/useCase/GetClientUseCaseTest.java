package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.GetClientRequest;
import com.Bank.BankBackend.modules.account.application.dto.response.GetClientResponse;
import com.Bank.BankBackend.modules.account.application.dto.response.GetListClientResponse;
import com.Bank.BankBackend.modules.account.domain.exception.ClientNotFoundException;
import com.Bank.BankBackend.modules.account.domain.exception.ClientsNotExistException;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetClientUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private GetClientUseCase getClientUseCase;

    @Test
    void shouldReturnActiveClientsSuccessfully() {

        Client client = Client.builder()
                .clientId(1)
                .names("Carlos")
                .lastNames("Perez")
                .identifyNumber(456789123)
                .email("carlos@gmail.com")
                .status(TypeStatus.ACTIVE)
                .build();

        when(clientRepository.findByStatus(TypeStatus.ACTIVE))
                .thenReturn(List.of(client));

        List<GetListClientResponse> result =
                getClientUseCase.getClientActive();

        assertEquals(1, result.size());
        assertEquals("Carlos", result.get(0).getNames());
        assertEquals("Perez", result.get(0).getLastNames());
        assertEquals(456789123, result.get(0).getIdentifyNumber());
        assertEquals("carlos@gmail.com", result.get(0).getEmail());
    }

    @Test
    void shouldThrowExceptionWhenNoActiveClientsExist() {

        when(clientRepository.findByStatus(TypeStatus.ACTIVE))
                .thenReturn(List.of());

        assertThrows(
                ClientsNotExistException.class,
                () -> getClientUseCase.getClientActive()
        );
    }

    @Test
    void shouldReturnInactiveClientsSuccessfully() {

        Client client = Client.builder()
                .clientId(2)
                .names("Laura")
                .lastNames("Gomez")
                .identifyNumber(741852963)
                .email("laura@gmail.com")
                .status(TypeStatus.INACTIVE)
                .build();

        when(clientRepository.findByStatus(TypeStatus.INACTIVE))
                .thenReturn(List.of(client));

        List<GetListClientResponse> result =
                getClientUseCase.getClientInactive();

        assertEquals(1, result.size());
        assertEquals("Laura", result.get(0).getNames());
        assertEquals(TypeStatus.INACTIVE, client.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenNoInactiveClientsExist() {

        when(clientRepository.findByStatus(TypeStatus.INACTIVE))
                .thenReturn(List.of());

        assertThrows(
                ClientsNotExistException.class,
                () -> getClientUseCase.getClientInactive()
        );
    }

    @Test
    void shouldReturnClientSuccessfully() {

        Integer identifyNumber = 369258147;

        GetClientRequest request =
                mock(GetClientRequest.class);

        Client client = Client.builder()
                .clientId(5)
                .names("Andres")
                .lastNames("Lopez")
                .identifyNumber(identifyNumber)
                .email("andres@gmail.com")
                .status(TypeStatus.ACTIVE)
                .userCreation(2)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.of(client));

        GetClientResponse result =
                getClientUseCase.getClient(request);

        assertNotNull(result);
        assertEquals(5, result.getClientId());
        assertEquals("Andres", result.getNames());
        assertEquals("Lopez", result.getLastNames());
        assertEquals(identifyNumber, result.getIdentifyNumber());
        assertEquals("andres@gmail.com", result.getEmail());
        assertEquals(TypeStatus.ACTIVE, result.getStatus());
        assertEquals(2, result.getUserCreation());
    }

    @Test
    void shouldThrowExceptionWhenClientDoesNotExist() {

        GetClientRequest request =
                mock(GetClientRequest.class);

        when(request.getIdentifyNumber())
                .thenReturn(369258147);

        when(clientRepository.findByIdentifyNumber(369258147))
                .thenReturn(Optional.empty());

        assertThrows(
                ClientNotFoundException.class,
                () -> getClientUseCase.getClient(request)
        );
    }
}