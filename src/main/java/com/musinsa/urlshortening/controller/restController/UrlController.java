package com.musinsa.urlshortening.controller.restController;

import com.musinsa.urlshortening.domain.dto.request.UrlShorteningRequestDto;
import com.musinsa.urlshortening.domain.dto.response.ResponseDto;
import com.musinsa.urlshortening.domain.dto.response.url.UrlShorteningResponseDto;
import com.musinsa.urlshortening.service.UrlService;
import com.musinsa.urlshortening.service.impl.UrlServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("")
    public ResponseDto<UrlShorteningResponseDto> create(@RequestBody @Valid final UrlShorteningRequestDto urlShorteningRequestDto, final HttpServletRequest request) {
        return ResponseDto.OK(urlService.create(urlShorteningRequestDto, request));
    }
}
