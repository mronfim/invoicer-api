package com.mronfim.invoicer.repository;

import java.util.UUID;

import com.mronfim.invoicer.model.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
    UserAccount findByUsername(String username);
    UserAccount findByEmail(String email);
}