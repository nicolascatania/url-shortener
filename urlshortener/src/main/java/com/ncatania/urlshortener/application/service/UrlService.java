package com.ncatania.urlshortener.application.service;

import com.ncatania.urlshortener.application.dto.UrlRequest;
import com.ncatania.urlshortener.application.dto.UrlResponse;
import com.ncatania.urlshortener.application.mapper.UrlMapper;
import com.ncatania.urlshortener.application.port.in.UrlServiceUseCase;
import com.ncatania.urlshortener.application.port.out.UrlRepositoryPort;
import com.ncatania.urlshortener.domain.model.Url;
import com.ncatania.urlshortener.infraestructure.exception.UrlNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UrlService implements UrlServiceUseCase {

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
        Url toSave = Url.create(urlRequest.baseUrl());
        Url saved = repository.saveUrl(toSave);
        return UrlMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteUrlById(Long id) {
        repository.deleteUrlById(id);
    }

}
