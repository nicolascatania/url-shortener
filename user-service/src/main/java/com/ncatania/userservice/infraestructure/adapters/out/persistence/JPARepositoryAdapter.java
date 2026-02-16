package com.ncatania.userservice.infraestructure.adapters.out.persistence;

import com.ncatania.userservice.application.dto.UserRequest;
import com.ncatania.userservice.application.dto.UserResponse;
import com.ncatania.userservice.application.ports.out.UserRepositoryPort;
import com.ncatania.userservice.application.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JPARepositoryAdapter implements UserRepositoryPort {

    private final SpringDataUserAppRepository repository;

    @Override
    public List<UserResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse getById(Long id) {
        return repository.findById(id)
                .map(UserMapper::toResponse)
                .orElse(null);
    }

    @Override
    public UserResponse create(UserRequest user) {
        UserAppEntity entity = UserMapper.toEntity(user);
        UserAppEntity saved = repository.save(entity);
        return UserMapper.toResponse(saved);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
