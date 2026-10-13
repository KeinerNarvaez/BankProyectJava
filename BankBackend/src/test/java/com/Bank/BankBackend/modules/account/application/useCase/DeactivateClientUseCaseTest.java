package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.DeactivateClientRequest;
import com.Bank.BankBackend.modules.account.domain.exception.AccountRemainsActiveException;
import com.Bank.BankBackend.modules.account.domain.exception.ClientNotFoundException;
import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import com.Bank.BankBackend.modules.account.domain.repository.AccountRepository;
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
class DeactivateClientUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private DeactivateClientUseCase deactivateClientUseCase;

    @Test
    void shouldDeactivateClientSuccessfully() {

        Integer identifyNumber = 741852963;

        DeactivateClientRequest request =
                mock(DeactivateClientRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .status(TypeStatus.ACTIVE)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(identifyNumber);

        when(clientRepository.findByIdentifyNumber(identifyNumber))
                .thenReturn(Optional.of(client));

        when(accountRepository
                .existsByClientId_ClientIdAndAccountStatus(
                        1,
                        AccountStatus.ACTIVE
                ))
                .thenReturn(false);

        deactivateClientUseCase.deactivateClient(request);

        ArgumentCaptor<Client> captor =
                ArgumentCaptor.forClass(Client.class);

        verify(clientRepository)
                .save(captor.capture());

        Client updatedClient = captor.getValue();

        assertEquals(
                TypeStatus.INACTIVE,
                updatedClient.getStatus()
        );

        assertNotNull(
                updatedClient.getUpdatedAt()
        );
    }

    @Test
    void shouldThrowExceptionWhenClientDoesNotExist() {

        DeactivateClientRequest request =
                mock(DeactivateClientRequest.class);

        when(request.getIdentifyNumber())
                .thenReturn(741852963);

        when(clientRepository.findByIdentifyNumber(741852963))
                .thenReturn(Optional.empty());

        assertThrows(
                ClientNotFoundException.class,
                () -> deactivateClientUseCase.deactivateClient(request)
        );

        verify(clientRepository, never())
                .save(any(Client.class));
    }

    @Test
    void shouldThrowExceptionWhenClientHasActiveAccount() {

        DeactivateClientRequest request =
                mock(DeactivateClientRequest.class);

        Client client = Client.builder()
                .clientId(1)
                .status(TypeStatus.ACTIVE)
                .build();

        when(request.getIdentifyNumber())
                .thenReturn(741852963);

        when(clientRepository.findByIdentifyNumber(741852963))
                .thenReturn(Optional.of(client));

        when(accountRepository
                .existsByClientId_ClientIdAndAccountStatus(
                        1,
                        AccountStatus.ACTIVE
                ))
                .thenReturn(true);

        assertThrows(
                AccountRemainsActiveException.class,
                () -> deactivateClientUseCase.deactivateClient(request)
        );

        verify(clientRepository, never())
                .save(any(Client.class));
    }
}