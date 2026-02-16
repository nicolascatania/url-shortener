package com.ncatania.urlshortener.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UrlRequest(
        @NotBlank(message = "Base URL cannot be blank")
        String baseUrl,
        @NotNull(message = "User ID cannot be null")
        Long userId
) {
}
