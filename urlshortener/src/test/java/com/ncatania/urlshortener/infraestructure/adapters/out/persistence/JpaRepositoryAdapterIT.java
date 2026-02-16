package com.ncatania.urlshortener.infraestructure.adapters.out.persistence;

import com.ncatania.urlshortener.application.port.out.UrlRepositoryPort;
import com.ncatania.urlshortener.domain.model.Url;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class JpaRepositoryAdapterIT {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.33"))
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private UrlRepositoryPort repository;

    @Test
    void saveAndGetUrl() {
        Url toSave = Url.create("https://example.org/test");
        Url saved = repository.saveUrl(toSave);

        assertNotNull(saved.id());
        Url fetched = repository.getUrlById(saved.id()).orElseThrow();
        assertEquals(saved.id(), fetched.id());
        assertEquals(saved.baseUrl(), fetched.baseUrl());
        assertEquals(saved.shortUrl(), fetched.shortUrl());
    }
}
