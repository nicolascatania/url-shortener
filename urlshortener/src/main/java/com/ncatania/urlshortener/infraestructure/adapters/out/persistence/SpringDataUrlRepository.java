package com.ncatania.urlshortener.infraestructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataUrlRepository extends JpaRepository<UrlEntity, Long> {
}
