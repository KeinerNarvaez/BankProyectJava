package com.Bank.BankBackend.modules.user.application.useCase;

import com.Bank.BankBackend.modules.user.application.dto.response.UserListResponse;
import com.Bank.BankBackend.modules.user.application.port.in.GetUsersPort;
import com.Bank.BankBackend.modules.user.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class GetUsersUseCase implements GetUsersPort {
    private UserRepository userRepository;

    @Override
    public List<UserListResponse> listUsers() {
        return userRepository.findAll();
    }
}
