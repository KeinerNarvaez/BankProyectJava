package com.Bank.BankBackend.modules.user.domain.repository;

import com.Bank.BankBackend.modules.user.application.dto.response.UserListResponse;
import com.Bank.BankBackend.modules.user.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Integer userId);
    Optional<User> findByEmailAndUserStatusActive(String email);
    Optional<User> findByEmail(String email);
    List<UserListResponse> findAll();
    void deleteById(Integer userId);

}
