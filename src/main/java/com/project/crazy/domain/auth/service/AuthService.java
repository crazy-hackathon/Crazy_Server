package com.project.crazy.domain.auth.service;

import com.project.crazy.domain.auth.entity.User;
import com.project.crazy.domain.auth.exception.PasswordWrongException;
import com.project.crazy.domain.auth.exception.UserNotFoundException;
import com.project.crazy.domain.auth.presentation.dto.request.SignInRequest;
import com.project.crazy.domain.auth.presentation.dto.request.SignUpRequest;
import com.project.crazy.domain.auth.presentation.dto.response.UserSignInResponse;
import com.project.crazy.domain.auth.repository.UserRepository;
import com.project.crazy.domain.auth.type.Gender;
import com.project.crazy.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signUp(SignUpRequest request) {
        User user = User.builder()
                .userId(request.getId())
                .password(passwordEncoder.encode(request.getPw()))
                .name(request.getName())
                .gender(request.getGender())
                .age(request.getAge())
                .build();

        userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserSignInResponse signIn(SignInRequest request) {
        User user = userRepository.findByUserId(request.getId())
                .orElseThrow(() -> {
                    throw UserNotFoundException.EXCEPTION;
                });

        if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return UserSignInResponse.builder()
                    .name(user.getName())
                    .token(jwtProvider.generateAccessToken(user.getId()))
                    .build();
        } else {
            throw PasswordWrongException.EXCEPTION;
        }

    }

}
