package com.ncatania.urlshortener.infraestructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataUrlRepository extends JpaRepository<UrlEntity, Long> {

    List<UrlEntity> getUrlsByUserId(Long userId);
}
