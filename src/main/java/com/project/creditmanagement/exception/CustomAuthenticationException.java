package com.project.creditmanagement.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
public class CustomAuthenticationException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;
}
