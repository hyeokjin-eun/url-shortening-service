package com.musinsa.urlshortening.domain.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlShorteningRequestDto {

    @NotEmpty
    @URL
    private String url;
}
