package com.Bank.BankBackend.modules.user.application.useCase;

import com.Bank.BankBackend.modules.user.application.dto.request.CreateUserRequest;
import com.Bank.BankBackend.modules.user.application.dto.response.UserResponse;
import com.Bank.BankBackend.modules.user.application.port.in.CreateUserPort;
import com.Bank.BankBackend.modules.user.application.port.out.EmailNotificationPort;
import com.Bank.BankBackend.modules.user.domain.exception.UserEmailAlreadyExistsException;
import com.Bank.BankBackend.modules.user.domain.exception.UserIdAlreadyExistsException;
import com.Bank.BankBackend.modules.user.domain.model.RolType;
import com.Bank.BankBackend.modules.user.domain.model.User;
import com.Bank.BankBackend.modules.user.domain.model.UserActivationToken;
import com.Bank.BankBackend.modules.user.domain.model.UserStatus;
import com.Bank.BankBackend.modules.user.domain.repository.UserActivationTokenRepository;
import com.Bank.BankBackend.modules.user.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase implements CreateUserPort {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailNotificationPort emailNotificationPort;
    private final UserActivationTokenRepository userActivationTokenRepository;

    @Override
    public UserResponse create(CreateUserRequest request) {
        String emailRequest = request.getEmail().toLowerCase();
        if (userRepository.findByEmailAndUserStatusActive(emailRequest).isPresent()) {
            throw new UserEmailAlreadyExistsException(emailRequest);
        }
        //Preparamos los datos para enviar a la información en la database
        User user = userRepository.save(User.builder()
                .email(emailRequest)
                .password(passwordEncoder.encode(request.getPassword()))
                .userStatus(request.getRolType() == RolType.ADMIN ? UserStatus.ACTIVE : UserStatus.INACTIVE)
                .rolType(request.getRolType())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

        String token= UUID.randomUUID().toString().replace("-", "").substring(0, 6);// <- un valor de 0 a 6 el numero de caracteres

        UserActivationToken activationToken =
                UserActivationToken.builder()
                        .userId(user)
                        .token(token)
                        .expire(LocalDateTime.now().plusMinutes(15)) //15 minutos para que el usuario ingrese el código y cambien contraseña
                        .used(false)
                        .createdAt(LocalDateTime.now())
                        .build();

        String emailUser = user.getEmail().toLowerCase();
        userActivationTokenRepository.save(activationToken);
        emailNotificationPort.sendActivationEmail(emailUser,token);

        return UserResponse.builder()
                .userId(user.getUserId())
                .email(emailUser)
                .UserStatus(user.getUserStatus())
                .RolType(user.getRolType())
                .build();
    }
}
