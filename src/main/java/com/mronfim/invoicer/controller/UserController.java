package com.mronfim.invoicer.controller;

import javax.servlet.http.HttpServletRequest;

import com.mronfim.invoicer.dto.CredentialsDTO;
import com.mronfim.invoicer.dto.UserDataDTO;
import com.mronfim.invoicer.dto.UserResponseDTO;
import com.mronfim.invoicer.model.UserAccount;
import com.mronfim.invoicer.repository.UserAccountRepository;
import com.mronfim.invoicer.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/signin")
    public String login(@RequestBody CredentialsDTO credentials) {
        return userService.signin(credentials.getUsername(), credentials.getPassword());
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserDataDTO user) {
        return userService.signup(modelMapper.map(user, UserAccount.class));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        userService.delete(id);
        return id;
    }

    @GetMapping("/{username}")
    public UserResponseDTO search(@PathVariable String username) {
        return modelMapper.map(userService.search(username), UserResponseDTO.class);
    }

    @GetMapping("/me")
    public UserResponseDTO whoami(HttpServletRequest req) {
        return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
    }

    @GetMapping("/refresh")
    public String refresh(HttpServletRequest req) {
        return userService.refresh(req.getRemoteUser());
    }
}