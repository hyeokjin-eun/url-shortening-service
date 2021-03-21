package com.musinsa.urlshortening.service;

import com.musinsa.urlshortening.config.Base62;
import com.musinsa.urlshortening.domain.dto.request.UrlShorteningRequestDto;
import com.musinsa.urlshortening.domain.dto.response.url.UrlShorteningResponseDto;
import com.musinsa.urlshortening.domain.entity.Url;
import com.musinsa.urlshortening.repository.UrlRepository;
import com.musinsa.urlshortening.service.impl.UrlService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DisplayName("URL Service Test")
public class UrlServiceTest extends ServiceBaseConfig{

    @InjectMocks
    private UrlService urlService;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private Base62 base62;

    @Nested
    @DisplayName("URL Shortening Create")
    class create {

        private UrlShorteningRequestDto urlShorteningRequestDto;

        private MockHttpServletRequest mockHttpServletRequest;

        private String host;

        @BeforeEach
        public void setUp() {
            urlShorteningRequestDto = UrlShorteningRequestDto.builder()
                    .url("https://www.musinsa.com")
                    .build();

            mockHttpServletRequest = new MockHttpServletRequest();
            mockHttpServletRequest.setScheme("http");
            mockHttpServletRequest.setServerName("localhost");
            mockHttpServletRequest.setServerPort(28080);
            host = "http://localhost:28080";
        }

        @Test
        @DisplayName("정상 동작 (기존 URL 이 없을 경우)")
        public void createNewTest() {
            //given
            given(base62.encoding(1L)).willReturn("B");
            given(urlRepository.save(any(Url.class))).willReturn(Url.builder()
                    .id(1L)
                    .originUrl("https://www.musinsa.com")
                    .count(0L)
                    .created(LocalDateTime.now())
                    .build());

            //when
            UrlShorteningResponseDto responseDto = urlService.create(urlShorteningRequestDto, mockHttpServletRequest);

            //then
            verify(urlRepository).save(any(Url.class));
            assertThat(responseDto).isNotNull();
        }

        @Test
        @DisplayName("정상 동작 (기존 URL 이 있을 경우)")
        public void createTest() {
            //given
            given(base62.encoding(1L)).willReturn("B");
            given(urlRepository.findByOriginUrl(anyString())).willReturn(Optional.of(Url.builder()
                    .id(1L)
                    .originUrl("https://www.musinsa.com")
                    .count(0L)
                    .build()));

            //when
            UrlShorteningResponseDto responseDto = urlService.create(urlShorteningRequestDto, mockHttpServletRequest);

            //then
            verify(base62).encoding(anyLong());
            verify(urlRepository).findByOriginUrl(anyString());
            assertThat(responseDto).isNotNull();
            assertThat(responseDto.getUrl()).isEqualTo(host + "/B");
        }
    }

    @Nested
    @DisplayName("URL Shortening Move Page")
    class movePage {

        @Test
        @DisplayName("정상 동작")
        public void movePageTest() {
            //given
            given(base62.decoding("B")).willReturn(1L);
            given(urlRepository.findById(1L)).willReturn(Optional.of(Url.builder()
                    .id(1L)
                    .originUrl("https://www.musinsa.com")
                    .count(0L)
                    .created(LocalDateTime.now())
                    .build()));
            given(urlRepository.save(any(Url.class))).willReturn(Url.builder()
                    .id(1L)
                    .originUrl("https://www.musinsa.com")
                    .count(1L)
                    .created(LocalDateTime.now())
                    .build());

            //when
            String url = urlService.movePage("B");

            //then
            verify(base62).decoding(anyString());
            verify(urlRepository).save(any(Url.class));
            verify(urlRepository).findById(anyLong());
            assertThat(url).isNotEmpty();
            assertThat(url).isEqualTo("https://www.musinsa.com");
        }
    }
}
