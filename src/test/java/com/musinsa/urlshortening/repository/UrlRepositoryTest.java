package com.musinsa.urlshortening.repository;

import com.musinsa.urlshortening.domain.entity.Url;
import com.musinsa.urlshortening.domain.exception.UrlNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("URL Repository Test")
public class UrlRepositoryTest extends RepositoryBaseConfig {

    @Autowired
    private UrlRepository urlRepository;

    @BeforeEach
    void setUp() {
        String[] urls = new String[]{
                "https://www.musinsa.com/",
                "https://github.com/",
                "https://www.naver.com/",
                "https://www.facebook.com/"
        };

        for (String url : urls) {
            Url saveUrl = urlRepository.save(Url.builder()
                    .originUrl(url)
                    .count(0L)
                    .created(LocalDateTime.now())
                    .build());
        }
    }

    @Test
    @DisplayName("Save Test")
    public void saveTest() {
        Url url = urlRepository.save(Url.builder()
                .originUrl("https://www.instagram.com/")
                .count(0L)
                .created(LocalDateTime.now())
                .build());

        assertThat(url.getId()).isEqualTo(5L);
        assertThat(url.getOriginUrl()).isEqualTo("https://www.instagram.com/");
        assertThat(url.getCount()).isEqualTo(0L);
        assertThat(url.getCreated()).isNotNull();
    }

    @Test
    @DisplayName("FindById Test")
    public void findByIdTest() {
        Url url = urlRepository.findById(6L)
                .orElseThrow(UrlNotFoundException::new);

        assertThat(url.getId()).isEqualTo(6L);
        assertThat(url.getOriginUrl()).isEqualTo("https://www.musinsa.com/");
        assertThat(url.getCount()).isEqualTo(0L);
        assertThat(url.getCreated()).isNotNull();
    }

    @Test
    @DisplayName("FindByOriginUrl Test")
    public void findByOriginUrlTest() {
        Url url = urlRepository.findByOriginUrl("https://www.musinsa.com/")
                .orElseThrow(UrlNotFoundException::new);

        assertThat(url.getId()).isEqualTo(10L);
        assertThat(url.getOriginUrl()).isEqualTo("https://www.musinsa.com/");
        assertThat(url.getCount()).isEqualTo(0L);
        assertThat(url.getCreated()).isNotNull();
    }
}
