package com.ncatania.urlshortener.application.port.in;

import com.ncatania.urlshortener.application.dto.UrlRequest;
import com.ncatania.urlshortener.application.dto.UrlResponse;

public interface UrlServiceUseCase {
        Iterable<UrlResponse> getUrls();
        UrlResponse getUrlById(Long id);
        UrlResponse saveUrl(UrlRequest urlRequest);
        void deleteUrlById(Long id);

        UrlResponse getUrlByShortCode(String shortCode);
}
