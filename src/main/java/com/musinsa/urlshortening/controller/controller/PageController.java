package com.musinsa.urlshortening.controller.controller;

import com.musinsa.urlshortening.service.UrlService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    private final UrlService urlService;

    public PageController(UrlService urlService) {
        this.urlService = urlService;
    }

    /**
     * URL 입력폼 제공 및 결과 출력
     * @return index.html
     */
    @GetMapping("")
    public String index() {
        return "index";
    }

    /**
     * Shortening 된 URL 받아서 원래 URL 로 redirection
     * @param id shortKey Shortening 된 URL Key
     * @return redirection
     */
    @GetMapping("{id}")
    public String movePage(@PathVariable final String id) {
        String url = urlService.movePage(id);
        return "redirect:" + url;
    }
}
