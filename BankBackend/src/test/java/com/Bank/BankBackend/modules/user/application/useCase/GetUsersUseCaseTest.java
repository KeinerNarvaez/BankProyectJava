package com.Bank.BankBackend.modules.user.application.useCase;

import com.Bank.BankBackend.modules.user.application.dto.response.UserListResponse;
import com.Bank.BankBackend.modules.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUsersUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUsersUseCase getUsersUseCase;

    @Test
    void shouldReturnAllActiveUsers() {

        UserListResponse user1 =
                new UserListResponse(1, "user1@gmail.com");

        UserListResponse user2 =
                new UserListResponse(2, "user2@gmail.com");

        List<UserListResponse> users = List.of(user1, user2);

        when(userRepository.findAll())
                .thenReturn(users);

        List<UserListResponse> result =
                getUsersUseCase.listUsers();

        assertEquals(2, result.size());
        assertEquals("user1@gmail.com", result.get(0).email());
        assertEquals("user2@gmail.com", result.get(1).email());
    }
}