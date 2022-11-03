package com.project.crazy.domain.auth.presentation.dto.request;

import com.project.crazy.domain.auth.type.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class SignUpRequest {

    private String id;
    private String pw;
    private String name;
    private Gender gender;
    private int age;

}
