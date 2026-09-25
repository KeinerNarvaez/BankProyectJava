package com.Bank.BankBackend.modules.user.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.user.infrastructure.persistence.entity.UserActivationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserActivationTokenJpaRepository extends JpaRepository<UserActivationTokenEntity,Integer> {
    Optional<UserActivationTokenEntity> findByUserId_UserIdAndToken(Integer userId, String token);
}
