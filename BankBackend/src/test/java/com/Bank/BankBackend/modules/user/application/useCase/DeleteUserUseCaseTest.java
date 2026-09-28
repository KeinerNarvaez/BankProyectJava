package com.Bank.BankBackend.modules.user.application.useCase;

import com.Bank.BankBackend.modules.user.application.dto.request.UserDeleteRequest;
import com.Bank.BankBackend.modules.user.application.dto.response.UserDeleteResponse;
import com.Bank.BankBackend.modules.user.domain.model.User;
import com.Bank.BankBackend.modules.user.domain.model.UserStatus;
import com.Bank.BankBackend.modules.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeleteUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    @Test
    void shouldDeactivateUserSuccessfully() {

        // ARRANGE
        UserDeleteRequest request = new UserDeleteRequest();
        request.setUserId(1);

        User user = User.builder()
                .userId(1)
                .email("user@gmail.com")
                .userStatus(UserStatus.ACTIVE)
                .build();

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        // ACT
        UserDeleteResponse response =
                deleteUserUseCase.delete(request);

        // ASSERT
        assertTrue(response.isValid());
        assertEquals(
                "Usuario eliminado correctamente",
                response.getMessage()
        );

        verify(userRepository).findById(1);
        verify(userRepository).save(org.mockito.ArgumentMatchers.any(User.class));
    }
}