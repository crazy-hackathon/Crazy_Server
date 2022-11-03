package com.project.crazy.domain.auth.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class SignInRequest {

    private String id;
    private String password;

}
