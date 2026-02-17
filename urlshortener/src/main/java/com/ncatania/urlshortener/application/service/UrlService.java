package com.ncatania.urlshortener.application.service;

import com.ncatania.urlshortener.application.dto.UrlRequest;
import com.ncatania.urlshortener.application.dto.UrlResponse;
import com.ncatania.urlshortener.application.mapper.UrlMapper;
import com.ncatania.urlshortener.application.port.in.GetUrlsByUserIdUseCase;
import com.ncatania.urlshortener.application.port.in.UrlServiceUseCase;
import com.ncatania.urlshortener.application.port.out.UrlRepositoryPort;
import com.ncatania.urlshortener.domain.model.Url;
import com.ncatania.urlshortener.infraestructure.exception.UrlNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlService implements UrlServiceUseCase, GetUrlsByUserIdUseCase {

    private final UrlRepositoryPort repository;

    @Override
    public List<UrlResponse> getUrls() {
        return repository.getUrls().stream().map(UrlMapper::toResponse).toList();
    }

    @Override
    public UrlResponse getUrlById(Long id) {
        return repository.getUrlById(id)
                .map(UrlMapper::toResponse)
                .orElseThrow(() -> new UrlNotFoundException("Url not found"));
    }

    @Override
    @Transactional
    public UrlResponse saveUrl(UrlRequest urlRequest) {
        Url toSave = Url.create(urlRequest.baseUrl(), urlRequest.userId());
        Url saved = repository.saveUrl(toSave);
        log.info("Saved url: {}", saved);
        return UrlMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteUrlById(Long id) {
        repository.deleteUrlById(id);
    }

    @Override
    public UrlResponse getUrlByShortCode(String shortCode) {
        return repository.getUrlByShortCode(shortCode).map(UrlMapper::toResponse)
                .orElseThrow(() -> new UrlNotFoundException("Url not found"));
    }

    @Override
    public List<UrlResponse> getByUserId(Long userId) {
        return repository.getUrlsByUserId(userId).stream().map(UrlMapper::toResponse).toList();
    }
}
