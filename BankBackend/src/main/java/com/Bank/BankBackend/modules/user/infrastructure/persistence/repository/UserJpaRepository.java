package com.Bank.BankBackend.modules.user.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.user.domain.model.UserStatus;
import com.Bank.BankBackend.modules.user.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmailAndUserStatus(String email, UserStatus userStatus);
    Optional <UserEntity> findFirstByEmailOrderByCreatedAtDesc(String email);
    List<UserEntity> findAllByUserStatus(UserStatus userStatus);
}