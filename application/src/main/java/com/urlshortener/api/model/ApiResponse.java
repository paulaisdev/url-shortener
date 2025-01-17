package com.urlshortener.api.model;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse<T> {
    private HttpStatus status;
    private T data;
    private String error;

    public ApiResponse(HttpStatus status, T data) {
        this.status = status;
        this.data = data;
    }

    public ApiResponse(HttpStatus status, String error) {
        this.status = status;
        this.error = error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    // Utilitário para criar resposta de erro
    public static ApiResponse<Map<String, String>> error(HttpStatus status, String message) {
        Map<String, String> errorData = new HashMap<>();
        errorData.put("error", message);
        return new ApiResponse<>(status, errorData);
    }
}
