package com.mronfim.invoicer.service;

import com.mronfim.invoicer.model.UserAccount;
import com.mronfim.invoicer.repository.UserAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserAccountRepository userAccountRepository;

    UserAccount getCurrentUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAccount user = userAccountRepository.findByUsername(username);
        return user;
    }
}