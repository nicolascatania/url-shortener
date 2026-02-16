package com.ncatania.urlshortener.application.port.out;

import com.ncatania.urlshortener.domain.model.Url;

import java.util.List;
import java.util.Optional;

/**
 * Contract that defines what I want the repository to do, without specifying how it does it.
 * This allows me to change the implementation of the repository without affecting the rest of the code.
 * For example, I can change from an in-memory repository to a database repository without changing the code that uses the repository.
 */
public interface UrlRepositoryPort {
    List<Url> getUrls();
    Optional<Url> getUrlById(Long id);
    Url saveUrl(Url url);
    void deleteUrlById(Long id);

    List<Url> getUrlsByUserId(Long userId);
}
