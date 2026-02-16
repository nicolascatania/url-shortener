package com.ncatania.urlshortener.domain.model;

public record Url (
        Long id,
        String baseUrl,
        String shortUrl
) {

    public static Url create(String baseUrl) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalArgumentException("Base URL cannot be blank");
        }
        // simple validation: try to parse as URI
        try {
            java.net.URI uri = new java.net.URI(baseUrl);
            if (uri.getScheme() == null || uri.getHost() == null) {
                throw new IllegalArgumentException("Invalid URL");
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid URL");
        }
        String shortCode = java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return new Url(null, baseUrl, shortCode);
    }

}
