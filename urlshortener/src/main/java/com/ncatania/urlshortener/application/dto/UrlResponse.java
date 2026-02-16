package com.ncatania.urlshortener.application.dto;

public record UrlResponse(
        Long id,
        String baseUrl,
        String shortUrl
){
}
