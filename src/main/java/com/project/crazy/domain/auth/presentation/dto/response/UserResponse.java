package com.project.crazy.domain.auth.presentation.dto.response;

import com.project.crazy.domain.auth.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @AllArgsConstructor
@Builder
public class UserResponse {

    private String id;
    private String password;
    private String name;
    private Gender gender;
    private int age;

}
