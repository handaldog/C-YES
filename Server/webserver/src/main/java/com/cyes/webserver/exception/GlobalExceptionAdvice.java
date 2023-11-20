package com.cyes.webserver.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ExceptionResponse> customExceptionHandler(CustomException e) {
        log.error("CustomException ::: " + e.getMessage());
        e.printStackTrace();
        return makeResponseEntity(e.getException());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ExceptionResponse> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException ::: " + e.getMessage());
        e.printStackTrace();
        return makeResponseEntity(CustomExceptionList.RUNTIME_EXCEPTION);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException e){
        log.error("RuntimeException ::: " + e.getMessage());
        e.printStackTrace();
        return makeResponseEntity(CustomExceptionList.BINDING_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception e) {

        e.printStackTrace();
        return makeResponseEntity(CustomExceptionList.INTERNAL_SERVER_ERROR);

    }

    private ResponseEntity<ExceptionResponse> makeResponseEntity(CustomExceptionList exceptionType) {
        return ResponseEntity
                .status(exceptionType.getStatus())
                .body(ExceptionResponse.builder()
                        .code(exceptionType.getCode())
                        .message(exceptionType.getMessage())
                        .build());
    }
}
