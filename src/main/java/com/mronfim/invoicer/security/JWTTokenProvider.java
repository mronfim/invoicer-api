package com.mronfim.invoicer.security;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import com.mronfim.invoicer.dto.UserResponseDTO;
import com.mronfim.invoicer.exception.InvalidJwtTokenException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTTokenProvider {

    // @Value("${security.jwt.token.secret-key:secret-key}")
    private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3_600_000;

    @Autowired
    private CustomUserDetails myUserDetails;

    // @PostConstruct
    // protected void init() {
    //     secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    // }

    public String createToken(UserResponseDTO user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(JWTConstants.TOKEN_HEADER_STRING);
        if (bearerToken != null && bearerToken.startsWith(JWTConstants.TOKEN_PREFIX)) {
            return bearerToken.replace(JWTConstants.TOKEN_PREFIX, "");
        }
        return null;
    }

    public boolean validateToken(String token) throws InvalidJwtTokenException {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtTokenException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}