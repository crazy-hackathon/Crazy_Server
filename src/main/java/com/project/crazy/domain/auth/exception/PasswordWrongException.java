package com.project.crazy.domain.auth.exception;

import com.project.crazy.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class PasswordWrongException extends BusinessException {

    public static final PasswordWrongException EXCEPTION = new PasswordWrongException();

    private PasswordWrongException() {
        super(HttpStatus.BAD_REQUEST, "비밀번호가 다릅니다");
    }
}
