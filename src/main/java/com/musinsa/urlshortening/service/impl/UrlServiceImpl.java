package com.musinsa.urlshortening.service.impl;

import com.musinsa.urlshortening.config.Base62;
import com.musinsa.urlshortening.domain.dto.request.UrlShorteningRequestDto;
import com.musinsa.urlshortening.domain.dto.response.url.UrlShorteningResponseDto;
import com.musinsa.urlshortening.domain.entity.Url;
import com.musinsa.urlshortening.domain.exception.UrlNotFoundException;
import com.musinsa.urlshortening.repository.UrlRepository;
import com.musinsa.urlshortening.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlServiceImpl implements UrlService {

    @Autowired
    private Base62 base62;

    @Autowired
    private UrlRepository urlRepository;

    /**
     * URL Shortening 생성
     * @param urlShorteningRequestDto Url Shortening 요청 객체
     * @param request HttpServletRequest
     * @return Shortening URL Response 객체
     */
    @Override
    public UrlShorteningResponseDto create(final UrlShorteningRequestDto urlShorteningRequestDto, final HttpServletRequest request) {
        String hostUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
        Url searchUrl = urlRepository.findByOriginUrl(urlShorteningRequestDto.getUrl())
                .orElse(null);

        if (searchUrl != null) {
            return UrlShorteningResponseDto.builder()
                    .url(hostUrl + base62.encoding(searchUrl.getId()))
                    .build();
        }

        return Optional.of(
                urlRepository.save(Url.builder()
                        .originUrl(urlShorteningRequestDto.getUrl())
                        .count(0L)
                        .created(LocalDateTime.now())
                        .build()))
                .map(url -> base62.encoding(url.getId()))
                .map(encodingUrl -> UrlShorteningResponseDto.builder()
                        .url(hostUrl + encodingUrl)
                        .build())
                .orElseThrow(UrlNotFoundException::new);
    }

    /**
     * Shortening 된 URL 받아서 원래 URL 반환
     * @param id shortKey Shortening 된 URL Key
     * @return 원래 URL
     */
    @Override
    public String movePage(final String id) {
        Long index = base62.decoding(id);
        return urlRepository.findById(index)
                .map(url -> {
                    url.countIncrease();
                    return urlRepository.save(url);
                })
                .map(Url::getOriginUrl)
                .orElseThrow(UrlNotFoundException::new);
    }
}
