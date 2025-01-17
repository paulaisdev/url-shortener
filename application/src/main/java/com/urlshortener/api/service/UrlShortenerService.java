package com.urlshortener.api.service;

import com.urlshortener.api.model.UrlMapping;
import com.urlshortener.api.repository.UrlMappingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlShortenerService {

    private final UrlMappingRepository repository;

    public UrlShortenerService(UrlMappingRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public String shortenUrl(String originalUrl) {
        validateUrl(originalUrl);
        String shortId = UUID.randomUUID().toString().substring(0, 8);
        UrlMapping mapping = new UrlMapping(shortId, originalUrl);
        repository.save(mapping);
        return shortId;
    }

    public Optional<String> getOriginalUrl(String shortId) {
        return repository.findByShortId(shortId).map(UrlMapping::getOriginalUrl);
    }

    public List<UrlMapping> getAllUrls() {
        return repository.findAll();
    }

    public void deleteUrl(String shortId) {
        UrlMapping mapping = repository.findByShortId(shortId)
                .orElseThrow(() -> new IllegalArgumentException("URL not found"));
        repository.delete(mapping);
    }


    private void validateUrl(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL format: " + url);
        }
    }
}