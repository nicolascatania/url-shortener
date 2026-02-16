package com.ncatania.urlshortener.infraestructure.adapters.out.persistence;

import com.ncatania.urlshortener.domain.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataUrlRepository extends JpaRepository<UrlEntity, Long> {

    List<UrlEntity> getUrlsByUserId(Long userId);

    Optional<Url> getUrlByShortUrl(String shortUrl);
}
