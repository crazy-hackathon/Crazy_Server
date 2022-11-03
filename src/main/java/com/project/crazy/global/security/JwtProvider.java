package com.project.crazy.global.security;

import com.project.crazy.domain.auth.entity.User;
import com.project.crazy.domain.auth.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {
    private final UserRepository userRepository;
    private final JwtProperties jwtProperties;

    public String generateAccessToken(Long userId) {
        return generateToken(userId.toString(), jwtProperties.getSecretKey(), jwtProperties.getAccessExp());
    }

    public String generateRefreshToken(Long userId) {
        return generateToken(userId.toString(), jwtProperties.getSecretKey(), jwtProperties.getRefreshExp());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ", "");
        }
        return null;
    }

    @Transactional
    public Authentication authentication(String token) {
        User userDetails = userRepository
                .findById(getTokenSubject(token)).orElseThrow();

        return new UserToken(userDetails);
    }

    public String parseToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(jwtProperties.getSecretKey()))
            return bearerToken.replace(jwtProperties.getSecretKey(), "");
        return null;
    }



    private Claims getTokenBody(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token).getBody();
        } catch (RuntimeException ex) {
            throw new RuntimeException("Token body Exception");
        }
    }

    private Long getTokenSubject(String token) {
        return Long.parseLong(getTokenBody(token).getSubject());
    }

    private String generateToken(String id, String type, Long exp) {
        return Jwts.builder()
                .setSubject(id)
                .claim("type", type)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + exp * 1000))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    public Date getExpiredDate(String token) {
        return getTokenBody(token).getExpiration();
    }

    public LocalDateTime getExpiredTime() {
        return LocalDateTime.now().plusSeconds(jwtProperties.getAccessExp());
    }
}

