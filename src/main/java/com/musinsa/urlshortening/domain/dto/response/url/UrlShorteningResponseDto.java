package com.musinsa.urlshortening.domain.dto.response.url;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlShorteningResponseDto {
    private String url;
}
