package com.ncatania.urlshortener.application.service;

import com.ncatania.urlshortener.application.dto.UrlRequest;
import com.ncatania.urlshortener.application.dto.UrlResponse;
import com.ncatania.urlshortener.domain.model.Url;
import com.ncatania.urlshortener.infraestructure.exception.UrlNotFoundException;
import com.ncatania.urlshortener.application.port.out.UrlRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private UrlRepositoryPort repository;

    @InjectMocks
    private UrlService service;

    @Test
    void saveUrl_shouldReturnSavedResponse() {
        UrlRequest req = new UrlRequest("https://example.com");
        when(repository.saveUrl(any())).thenReturn(new Url(1L, "https://example.com", "fixed123"));

        UrlResponse resp = service.saveUrl(req);

        assertNotNull(resp);
        assertEquals(1L, resp.id());
        assertEquals("https://example.com", resp.baseUrl());
        assertEquals("fixed123", resp.shortUrl());
    }

    @Test
    void getUrlById_notFound_throws() {
        when(repository.getUrlById(2L)).thenReturn(Optional.empty());

        assertThrows(UrlNotFoundException.class, () -> service.getUrlById(2L));
    }
}
