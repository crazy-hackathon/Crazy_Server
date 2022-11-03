package com.project.crazy.domain.auth.entity;

import com.project.crazy.domain.auth.type.Gender;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String password;
    private String name;

    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public User(String userId, String password, String name, int age, Gender gender) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}
