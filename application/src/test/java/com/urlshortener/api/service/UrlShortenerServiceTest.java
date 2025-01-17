package com.urlshortener.api.service;

import com.urlshortener.api.repository.UrlMappingRepository;
import com.urlshortener.api.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UrlShortenerServiceTest {

    private final UrlMappingRepository repository = mock(UrlMappingRepository.class);
    private final UrlShortenerService service = new UrlShortenerService(repository);

    @Test
    public void testShortenUrl_ValidUrl() {
        String originalUrl = "https://example.com";
        String shortId = service.shortenUrl(originalUrl);

        assertNotNull(shortId);
        verify(repository, times(1)).save(any());
    }

    @Test
    public void testShortenUrl_InvalidUrl() {
        String invalidUrl = "invalid-url";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.shortenUrl(invalidUrl));
        assertEquals("Invalid URL format: invalid-url", exception.getMessage());
    }
}
