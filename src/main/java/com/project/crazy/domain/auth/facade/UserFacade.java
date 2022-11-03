package com.project.crazy.domain.auth.facade;

import com.project.crazy.domain.auth.entity.User;
import com.project.crazy.domain.auth.exception.UserNotFoundException;
import com.project.crazy.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User queryUser(boolean withPersistence) {
        User withoutPersistencce = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (withPersistence) {
            return userRepository.findById(withoutPersistencce.getId())
                    .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        } else {
            return withoutPersistencce;
        }
    }

    public User queryUser() {
        return queryUser(false);
    }
}