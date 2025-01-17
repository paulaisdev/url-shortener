package com.urlshortener.api.exception;

import com.urlshortener.api.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleUrlNotFoundException(UrlNotFoundException ex) {
        Map<String, String> errorData = Map.of("error", ex.getMessage());  // Garantir que "error" seja preenchido
        ApiResponse<Map<String, String>> response = new ApiResponse<>(HttpStatus.NOT_FOUND, errorData);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorData = Map.of("error", ex.getMessage());
        ApiResponse<Map<String, String>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST, errorData);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
