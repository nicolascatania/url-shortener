package com.ncatania.userservice.application.ports.out;

import com.ncatania.userservice.domain.model.UserApp;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    List<UserApp> getAll();
    Optional<UserApp> getById(Long id);
    UserApp create(UserApp user);
    void deleteById(Long id);
}
