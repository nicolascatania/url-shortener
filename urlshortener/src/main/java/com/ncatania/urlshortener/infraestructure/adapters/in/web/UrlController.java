package com.ncatania.urlshortener.infraestructure.adapters.in.web;

import com.ncatania.urlshortener.application.dto.UrlRequest;
import com.ncatania.urlshortener.application.dto.UrlResponse;
import com.ncatania.urlshortener.application.port.in.GetUrlsByUserIdUseCase;
import com.ncatania.urlshortener.application.port.in.UrlServiceUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/url")
public class UrlController {

    private final UrlServiceUseCase service;
    private final GetUrlsByUserIdUseCase getUrlsByUserIdUseCase;

    @GetMapping
    public ResponseEntity<Iterable<UrlResponse>> findAll() {
        return ResponseEntity.ok(service.getUrls());
    }


    @GetMapping("/{id}")
    public ResponseEntity<UrlResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUrlById(id));
    }

    @PostMapping
    public ResponseEntity<UrlResponse> save(@Valid @RequestBody UrlRequest urlRequest) {
        return ResponseEntity.ok(service.saveUrl(urlRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteUrlById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UrlResponse>> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(getUrlsByUserIdUseCase.getByUserId(userId));
    }
}
