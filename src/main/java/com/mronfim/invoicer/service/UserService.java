package com.mronfim.invoicer.service;

import java.util.ArrayList;
import java.util.UUID;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import com.mronfim.invoicer.dto.UserResponseDTO;
import com.mronfim.invoicer.exception.CustomException;
import com.mronfim.invoicer.model.Company;
import com.mronfim.invoicer.model.UserAccount;
import com.mronfim.invoicer.repository.CompanyRepository;
import com.mronfim.invoicer.repository.UserAccountRepository;
import com.mronfim.invoicer.security.CustomUserDetails;
import com.mronfim.invoicer.security.JWTTokenProvider;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    CompanyRepository companyRepository;

    public UserAccount getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAccount currentUser = userAccountRepository.findByUsername(user.getUsername());
        return currentUser;
    }

    public Company doesUserHaveCompany(Long companyId) {
        UserAccount user = getCurrentUser();
        return companyRepository.findByUserIdAndId(user.getId(), companyId).orElseThrow();
    }

    public String signin(String username, String password) {
        System.out.println("Attempting authentication with username: " + username + " and password: " + password);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>()));
            UserResponseDTO user = modelMapper.map(userAccountRepository.findByUsername(username), UserResponseDTO.class);
            return jwtTokenProvider.createToken(user);
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signup(UserAccount newUser) {
        boolean duplicateUsername = userAccountRepository.existsByUsername(newUser.getUsername());
        boolean duplicateEmail = userAccountRepository.existsByEmail(newUser.getEmail());
        // Check if username already exists
        if (duplicateUsername) {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        // Check if email already registered
        } else if (duplicateEmail) {
            throw new CustomException("Account already registered with email", HttpStatus.UNPROCESSABLE_ENTITY);
        // if both username and email are unique
        } else {
            // encrypt user's password
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            // save the user in the db, and get the saved entity (this will include the user's id)
            UserAccount savedUser = userAccountRepository.save(newUser);
            // convert the UserAccount obj into a UserResponseDTO obj for token creation
            UserResponseDTO user = modelMapper.map(savedUser, UserResponseDTO.class);
            return jwtTokenProvider.createToken(user);
        }
    }

    public void delete(String id) {
        userAccountRepository.deleteById(UUID.fromString(id));
    }

    public UserAccount search(String username) {
        UserAccount user = userAccountRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException("The user doesn't exists", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public UserAccount whoami(HttpServletRequest req) {
        return userAccountRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public String refresh(String username) {
        UserResponseDTO user = modelMapper.map(userAccountRepository.findByUsername(username), UserResponseDTO.class);
        return jwtTokenProvider.createToken(user);
    }
}