package com.musinsa.urlshortening.service;

import com.musinsa.urlshortening.domain.dto.request.UrlShorteningRequestDto;
import com.musinsa.urlshortening.domain.dto.response.url.UrlShorteningResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface UrlService {
    UrlShorteningResponseDto create(final UrlShorteningRequestDto urlShorteningRequestDto, final HttpServletRequest httpServletRequest);

    String movePage(final String id);
}
