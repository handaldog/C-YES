package com.cyes.webserver.exception;

import lombok.Getter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Getter
//@EnableWebMvc
public class CustomException extends RuntimeException {
    private final CustomExceptionList exception;

    public CustomException(CustomExceptionList e) {
        super(e.getMessage());
        this.exception = e;
    }
}
