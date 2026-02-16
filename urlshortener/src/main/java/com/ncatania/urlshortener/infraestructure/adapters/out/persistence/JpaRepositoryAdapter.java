package com.ncatania.urlshortener.infraestructure.adapters.out.persistence;

import com.ncatania.urlshortener.application.mapper.UrlMapper;
import com.ncatania.urlshortener.application.port.out.UrlRepositoryPort;
import com.ncatania.urlshortener.domain.model.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaRepositoryAdapter implements UrlRepositoryPort {

    private final SpringDataUrlRepository repository;

    @Override
    public List<Url> getUrls() {
        return repository.findAll().stream().map(UrlMapper::toDomain).toList();
    }

    @Override
    public Optional<Url> getUrlById(Long id) {
        return repository.findById(id).map(UrlMapper::toDomain);
    }

    @Override
    public Url saveUrl(Url url) {
        UrlEntity urlEntity = UrlMapper.toEntity(url);
        UrlEntity saved = repository.save(urlEntity);
        return UrlMapper.toDomain(saved);
    }

    @Override
    public void deleteUrlById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Url> getUrlsByUserId(Long userId) {
        return repository.getUrlsByUserId(userId)
                .stream().map(UrlMapper::toDomain).toList();
    }

    @Override
    public Optional<Url> getUrlByShortCode(String shortUrl) {
        return repository.getUrlByShortUrl(shortUrl);
    }
}
