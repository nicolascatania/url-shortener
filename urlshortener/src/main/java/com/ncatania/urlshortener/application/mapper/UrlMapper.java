package com.ncatania.urlshortener.application.mapper;

import com.ncatania.urlshortener.application.dto.UrlRequest;
import com.ncatania.urlshortener.application.dto.UrlResponse;
import com.ncatania.urlshortener.domain.model.Url;
import com.ncatania.urlshortener.infraestructure.adapters.out.persistence.UrlEntity;

public final class UrlMapper {

    private UrlMapper() {}

    public static Url toDomain(UrlEntity entity) {
        if (entity == null) return null;
        return new Url(entity.getId(), entity.getBaseUrl(), entity.getShortUrl(), entity.getUserId());
    }

    public static UrlEntity toEntity(Url domain) {
        if (domain == null) return null;
        return new UrlEntity(domain.id(), domain.baseUrl(), domain.shortUrl(), domain.userId());
    }

    public static UrlResponse toResponse(Url domain) {
        if (domain == null) return null;
        return new UrlResponse(domain.id(), domain.baseUrl(), domain.shortUrl(), domain.userId());
    }

    public static Url toDomainFromRequest(UrlRequest request) {
        if (request == null) return null;
        // Use domain factory to create validated domain object
        return Url.create(request.baseUrl(), request.userId());
    }
}
