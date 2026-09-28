package com.Bank.BankBackend.modules.user.application.useCase;

import com.Bank.BankBackend.modules.user.application.dto.request.ChangePasswordRequest;
import com.Bank.BankBackend.modules.user.domain.model.HistoryPassword;
import com.Bank.BankBackend.modules.user.domain.model.User;
import com.Bank.BankBackend.modules.user.domain.repository.HistoryPasswordRepository;
import com.Bank.BankBackend.modules.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChangePasswordUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HistoryPasswordRepository historyPasswordRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ChangePasswordUseCase changePasswordUseCase;

    @Test
    void shouldChangePasswordSuccessfully() {

        // ARRANGE
        Integer userId = 1;

        User user = User.builder()
                .userId(userId)
                .email("user@gmail.com")
                .password("oldEncodedPassword")
                .build();

        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("OldPassword123**");
        request.setNewPassword("NewPassword123**");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword()
        )).thenReturn(true);

        when(historyPasswordRepository.findByUserId(userId))
                .thenReturn(List.of());

        when(passwordEncoder.encode(request.getNewPassword()))
                .thenReturn("newEncodedPassword");

        // ACT
        changePasswordUseCase.changePassword(userId, request);

        // ASSERT
        verify(userRepository).findById(userId);

        verify(historyPasswordRepository)
                .findByUserId(userId);

        verify(userRepository)
                .save(any(User.class));

        verify(historyPasswordRepository)
                .save(any(HistoryPassword.class));
    }
}