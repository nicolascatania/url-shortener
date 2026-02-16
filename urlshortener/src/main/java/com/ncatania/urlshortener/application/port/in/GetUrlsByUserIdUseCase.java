package com.ncatania.urlshortener.application.port.in;

import com.ncatania.urlshortener.application.dto.UrlResponse;

import java.util.List;

public interface GetUrlsByUserIdUseCase {
    List<UrlResponse> getByUserId(Long userId);
}
