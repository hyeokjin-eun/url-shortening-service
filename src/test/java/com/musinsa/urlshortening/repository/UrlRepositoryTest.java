package com.musinsa.urlshortening.repository;

import com.musinsa.urlshortening.domain.entity.Url;
import com.musinsa.urlshortening.domain.exception.UrlNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("URL Repository Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UrlRepositoryTest extends RepositoryBaseConfig {

    @Autowired
    private UrlRepository urlRepository;

    private List<Url> urlSaveList;

    @BeforeAll
    void setUp() {
        String[] urls = new String[]{
                "https://www.musinsa.com/",
                "https://github.com/",
                "https://www.naver.com/",
                "https://www.facebook.com/"
        };

        List<Url> urlTemp = new ArrayList<>();
        for (String url : urls) {
            urlTemp.add(urlRepository.save(Url.builder()
                    .originUrl(url)
                    .count(0L)
                    .created(LocalDateTime.now())
                    .build()));
        }

        urlSaveList = urlTemp;
    }

    @Test
    @DisplayName("Save Test")
    public void saveTest() {
        Url url = Url.builder()
                .originUrl("https://www.instagram.com/")
                .count(0L)
                .created(LocalDateTime.now())
                .build();

        Url saveUrl = urlRepository.save(url);

        assertThat(saveUrl.getId()).isNotNull();
        assertThat(saveUrl.getOriginUrl()).isEqualTo(url.getOriginUrl());
        assertThat(saveUrl.getCount()).isEqualTo(url.getCount());
        assertThat(saveUrl.getCreated()).isNotNull();
    }

    @Test
    @DisplayName("FindById Test")
    public void findByIdTest() {
        Url url = urlRepository.findById(urlSaveList.get(0).getId())
                .orElseThrow(UrlNotFoundException::new);

        assertThat(url.getId()).isEqualTo(urlSaveList.get(0).getId());
        assertThat(url.getOriginUrl()).isEqualTo(urlSaveList.get(0).getOriginUrl());
        assertThat(url.getCount()).isEqualTo(urlSaveList.get(0).getCount());
        assertThat(url.getCreated()).isNotNull();
    }

    @Test
    @DisplayName("FindByOriginUrl Test")
    public void findByOriginUrlTest() {
        Url url = urlRepository.findByOriginUrl(urlSaveList.get(0).getOriginUrl())
                .orElseThrow(UrlNotFoundException::new);

        assertThat(url.getId()).isEqualTo(urlSaveList.get(0).getId());
        assertThat(url.getOriginUrl()).isEqualTo(urlSaveList.get(0).getOriginUrl());
        assertThat(url.getCount()).isEqualTo(urlSaveList.get(0).getCount());
        assertThat(url.getCreated()).isNotNull();
    }
}
