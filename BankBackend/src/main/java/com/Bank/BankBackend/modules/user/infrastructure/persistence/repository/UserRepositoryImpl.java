package com.Bank.BankBackend.modules.user.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.user.application.dto.response.UserListResponse;
import com.Bank.BankBackend.modules.user.application.mapper.UserMapper;
import com.Bank.BankBackend.modules.user.domain.model.User;
import com.Bank.BankBackend.modules.user.domain.model.UserStatus;
import com.Bank.BankBackend.modules.user.domain.repository.UserRepository;
import com.Bank.BankBackend.modules.user.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        UserEntity savedEntity = userJpaRepository.save(entity);
        return userMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(Integer userId) {
        return userJpaRepository.findById(userId)
                .map(userMapper::toDomain);
    }
    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository
                .findFirstByEmailOrderByCreatedAtDesc(email)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmailAndUserStatusActive(String email) {
        return userJpaRepository
                .findByEmailAndUserStatus(email, UserStatus.ACTIVE)
                .map(userMapper::toDomain);
    }

    @Override
    public List<UserListResponse> findAll() {
        return userJpaRepository.findAllByUserStatus( UserStatus.ACTIVE)
                .stream()
                .map(user -> new UserListResponse(
                        user.getUserId(),
                        user.getEmail()
                ))
                .toList();
    }

    @Override
    public void deleteById(Integer userId) {
        userJpaRepository.deleteById(userId);
    }
}