package com.Bank.BankBackend.modules.user.application.useCase;

import com.Bank.BankBackend.modules.user.application.dto.request.UserDeleteRequest;
import com.Bank.BankBackend.modules.user.application.dto.response.UserDeleteResponse;
import com.Bank.BankBackend.modules.user.application.port.in.UserDeletePort;
import com.Bank.BankBackend.modules.user.domain.exception.UserIdAlreadyExistsException;
import com.Bank.BankBackend.modules.user.domain.model.User;
import com.Bank.BankBackend.modules.user.domain.model.UserStatus;
import com.Bank.BankBackend.modules.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCase implements UserDeletePort {

    private final UserRepository userRepository;

    @Override
    public UserDeleteResponse delete(UserDeleteRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserIdAlreadyExistsException(request.getUserId()));

        User deactivatedUser = user.toBuilder()
                .userStatus(UserStatus.INACTIVE)
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(deactivatedUser);

        return UserDeleteResponse.builder()
                .valid(true)
                .message("Usuario eliminado correctamente")
                .build();
    }
}