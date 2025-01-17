package com.urlshortener.api.controller;

import com.urlshortener.api.model.ApiResponse;
import com.urlshortener.api.model.UrlMapping;
import com.urlshortener.api.exception.UrlNotFoundException;
import com.urlshortener.api.repository.UrlMappingRepository;
import com.urlshortener.api.service.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/shorten")
public class UrlShortenerController {

    private final UrlShortenerService service;
    private final UrlMappingRepository repository;

    public UrlShortenerController(UrlShortenerService service, UrlMappingRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, String>>> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("originalUrl");
        try {
            String shortId = service.shortenUrl(originalUrl);
            Map<String, String> responseData = Map.of("shortUrl", shortId);
            ApiResponse<Map<String, String>> response = new ApiResponse<>(HttpStatus.OK, responseData);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UrlMapping>>> getAllUrls() {
        List<UrlMapping> urls = service.getAllUrls();
        ApiResponse<List<UrlMapping>> response = new ApiResponse<>(HttpStatus.OK, urls);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortId}")
    public ResponseEntity<ApiResponse<Map<String, String>>> getOriginalUrl(@PathVariable String shortId) {
        Optional<String> originalUrl = service.getOriginalUrl(shortId);
        if (originalUrl.isPresent()) {
            Map<String, String> responseData = Map.of("originalUrl", originalUrl.get());
            ApiResponse<Map<String, String>> response = new ApiResponse<>(HttpStatus.OK, responseData);
            return ResponseEntity.ok(response);
        } else {
            throw new UrlNotFoundException("URL not found");
        }
    }

    @DeleteMapping("/{shortId}")
    public ResponseEntity<ApiResponse<Void>> deleteUrl(@PathVariable String shortId) {
        UrlMapping mapping = repository.findByShortId(shortId)
                .orElseThrow(() -> new UrlNotFoundException("URL not found"));
        service.deleteUrl(shortId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/convert/{shortId}")
    public ResponseEntity<ApiResponse<Map<String, String>>> convertUrl(@PathVariable String shortId) {
        Optional<String> originalUrl = service.getOriginalUrl(shortId);
        if (originalUrl.isPresent()) {
            Map<String, String> responseData = Map.of("originalUrl", originalUrl.get());
            ApiResponse<Map<String, String>> response = new ApiResponse<>(HttpStatus.OK, responseData);
            return ResponseEntity.ok(response);
        } else {
            throw new UrlNotFoundException("URL not found");
        }
    }
}
