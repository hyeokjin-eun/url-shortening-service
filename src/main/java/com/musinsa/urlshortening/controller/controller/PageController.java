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

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("{id}")
    public String movePage(@PathVariable final String id) {
        String url = urlService.movePage(id);
        return "redirect:" + url;
    }
}
