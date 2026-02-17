package com.ncatania.userservice.infraestructure.adapters.out.persistence;

import com.ncatania.userservice.application.ports.out.UserRepositoryPort;
import com.ncatania.userservice.application.mapper.UserMapper;
import com.ncatania.userservice.domain.model.UserApp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JPARepositoryAdapter implements UserRepositoryPort {

    private final SpringDataUserAppRepository repository;

    @Override
    public List<UserApp> getAll() {
        return repository.findAll()
                .stream()
                .map(UserMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<UserApp> getById(Long id) {
        return repository.findById(id)
                .map(UserMapper::toDomain);
    }

    @Override
    public UserApp create(UserApp user) {
        UserAppEntity entity = UserMapper.toEntity(user);
        UserAppEntity saved = repository.save(entity);
        return UserMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
