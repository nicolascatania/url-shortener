package com.ncatania.urlshortener.application.dto;

import jakarta.validation.constraints.NotBlank;

public record UrlRequest(
        @NotBlank(message = "Base URL cannot be blank")
        String baseUrl
) {
}
