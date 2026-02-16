package com.ncatania.urlshortener.infraestructure.adapters.out.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "urls")
@AllArgsConstructor
@NoArgsConstructor
public class UrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Url cannot be null")
    private String baseUrl;

    private String shortUrl;

    @Column(nullable = false)
    private Long userId;

}
