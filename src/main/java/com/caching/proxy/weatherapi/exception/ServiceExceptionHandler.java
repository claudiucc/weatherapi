package com.caching.proxy.weatherapi.exception;

import com.caching.proxy.weatherapi.model.exception.ErrorDto;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<ErrorDto> handleRateLimitExceededException(RateLimitExceededException exception) {
        return ResponseEntity
            .status(HttpStatus.TOO_MANY_REQUESTS)
            .body(buildException(exception.getMessage()));
    }

    @ExceptionHandler(ServiceBadRequestException.class)
    public ResponseEntity<ErrorDto> handleBadRequestException(ServiceBadRequestException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(buildException(exception.getMessage()));
    }

    private ErrorDto buildException(String message) {
        return ErrorDto.builder()
            .message(message)
            .timestamp(LocalDateTime.now())
            .build();
    }

}