package com.project.crazy.domain.auth.presentation;

import com.project.crazy.domain.auth.presentation.dto.request.SignInRequest;
import com.project.crazy.domain.auth.presentation.dto.request.SignUpRequest;
import com.project.crazy.domain.auth.presentation.dto.response.SignUpResponse;
import com.project.crazy.domain.auth.presentation.dto.response.UserSignInResponse;
import com.project.crazy.domain.auth.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @ApiOperation("회원가입")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public SignUpResponse signUp(
            @RequestBody SignUpRequest request
    ) {
        return authService.signUp(request);
    }

    @ApiOperation("로그인")
    @PostMapping("sign-in")
    public UserSignInResponse signIn(
            @RequestBody SignInRequest request
    ) {
        return authService.signIn(request);
    }

}
