package com.musinsa.urlshortening.config;

import com.musinsa.urlshortening.domain.dto.response.ResponseDto;
import com.musinsa.urlshortening.domain.exception.UrlNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto<String> badRequestException() {
        return ResponseDto.ERROR("잘못된 파라미터 방식입니다.");
    }

    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDto<String> UrlNotFoundException() {
        return ResponseDto.ERROR("URL을 찾을수 없습니다.");
    }
}