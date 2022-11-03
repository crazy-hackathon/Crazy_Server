package com.project.crazy.global.security;

import com.project.crazy.domain.auth.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UserToken extends UsernamePasswordAuthenticationToken {
    public UserToken(User user) {
        super(user, null, null);
    }
}

