package com.cyes.webserver.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionResponse {
    private final String code;
    private final String message;

    @Builder
    public ExceptionResponse(HttpStatus status, String code, String message) {
        this.code = code;
        this.message = message;
    }
}
