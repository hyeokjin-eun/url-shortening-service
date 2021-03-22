package com.musinsa.urlshortening.controller.restController;

import com.musinsa.urlshortening.domain.dto.request.UrlShorteningRequestDto;
import com.musinsa.urlshortening.domain.dto.response.ResponseDto;
import com.musinsa.urlshortening.domain.dto.response.url.UrlShorteningResponseDto;
import com.musinsa.urlshortening.service.UrlService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("rest/url")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    /**
     * URL Shortening 생성 API
     * @param urlShorteningRequestDto Url Shortening 요청 객체
     * @param request HttpServletRequest
     * @return Shortening URL Response 객체
     */
    @PostMapping("")
    public ResponseDto<UrlShorteningResponseDto> create(@RequestBody @Valid final UrlShorteningRequestDto urlShorteningRequestDto, final HttpServletRequest request) {
        return ResponseDto.OK(urlService.create(urlShorteningRequestDto, request));
    }
}
