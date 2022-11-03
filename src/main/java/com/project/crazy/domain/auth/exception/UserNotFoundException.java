package com.project.crazy.domain.auth.exception;

import com.project.crazy.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BusinessException {

    public static final UserNotFoundException EXCEPTION = new UserNotFoundException();

    private UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다");
    }
}
