package com.musinsa.urlshortening.controller.restController;

import com.musinsa.urlshortening.domain.dto.request.UrlShorteningRequestDto;
import com.musinsa.urlshortening.domain.dto.response.url.UrlShorteningResponseDto;
import com.musinsa.urlshortening.service.impl.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UrlController.class)
@DisplayName("URL Controller Test")
public class UrlControllerTest extends RestControllerBaseConfig {

    @MockBean
    private UrlService urlService;

    @Nested
    @DisplayName("URL Shortening Create")
    class create {

        private UrlShorteningRequestDto urlShorteningRequestDto;

        @BeforeEach
        public void setUp() {
            this.urlShorteningRequestDto = UrlShorteningRequestDto.builder()
                    .url("https://www.musinsa.com")
                    .build();
        }

        @Test
        @DisplayName("정상 동작")
        public void createTest() throws Exception {
            //given
            given(urlService.create(any(), any())).willReturn(UrlShorteningResponseDto.builder()
                    .url("http://localhost:28080/B")
                    .build());

            //when
            mockMvc.perform(post("/rest/url")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(urlShorteningRequestDto)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.url").value("http://localhost:28080/B"));

            //then
            verify(urlService).create(any(UrlShorteningRequestDto.class), any(HttpServletRequest.class));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @CsvSource({"test.com/sdfsjdkf"})
        @DisplayName("URL 파라미터 검증")
        public void createTestUrlParameterTest(String url) throws Exception {
            urlShorteningRequestDto.setUrl(url);
            mockMvc.perform(post("/rest/url")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(urlShorteningRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.resultCode").value("ERROR"));
        }
    }
}
