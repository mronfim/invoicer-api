package com.mronfim.invoicer.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mronfim.invoicer.model.UserAccount;
import com.mronfim.invoicer.repository.UserAccountRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserAccountRepository userAccountRepository;

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   UserAccountRepository userAccountRepository) {

        this.authenticationManager = authenticationManager;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) 
                                                throws AuthenticationException {
        try {
            UserAccount creds = new ObjectMapper().readValue(req.getInputStream(), UserAccount.class);
            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    creds.getUsername(),
                    creds.getPassword(),
                    new ArrayList<>()
                )
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        User subject = (User) auth.getPrincipal();
        UserAccount acc = userAccountRepository.findByUsername(subject.getUsername());

        String token = JWT.create()
                          .withSubject(subject.getUsername())
                          .withExpiresAt(new Date(System.currentTimeMillis() + JWTConstants.EXPIRATION_TIME))
                          .withClaim("userId", acc.getId().toString())
                          .withClaim("email", acc.getEmail())
                          .sign(HMAC512(JWTConstants.TOKEN_SECRET.getBytes()));

        res.addHeader(JWTConstants.TOKEN_HEADER_STRING, JWTConstants.TOKEN_PREFIX + token);
    }
}