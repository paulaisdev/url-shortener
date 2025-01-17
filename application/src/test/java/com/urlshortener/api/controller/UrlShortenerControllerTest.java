package com.urlshortener.api.controller;

import com.urlshortener.api.model.UrlMapping;
import com.urlshortener.api.service.UrlShortenerService;
import com.urlshortener.api.repository.UrlMappingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class UrlShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlShortenerService service;

    @MockBean
    private UrlMappingRepository repository;

    @Test
    public void testShortenUrl() throws Exception {
        when(service.shortenUrl("https://example.com")).thenReturn("abcd1234");

        mockMvc.perform(post("/shorten")
                        .contentType("application/json")
                        .content("{\"originalUrl\":\"https://example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.shortUrl").value("abcd1234"));
    }

//    @Test
//    public void testGetOriginalUrl() throws Exception {
//        when(service.getOriginalUrl("abcd1234")).thenReturn(Optional.of("https://example.com"));
//
//        mockMvc.perform(get("/shorten/abcd1234"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.originalUrl").value("https://example.com"));
//
//        when(service.getOriginalUrl("notfound")).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/shorten/notfound"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.error").value("URL not found"));
//    }

//    @Test
//    public void testDeleteUrl() throws Exception {
//        when(repository.findByShortId("abcd1234")).thenReturn(Optional.of(new UrlMapping("abcd1234", "https://example.com")));
//        doNothing().when(service).deleteUrl("abcd1234");
//
//        mockMvc.perform(delete("/shorten/abcd1234"))
//                .andExpect(status().isNoContent());
//
//        when(repository.findByShortId("notfound")).thenReturn(Optional.empty());
//
//        mockMvc.perform(delete("/shorten/notfound"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.error").value("URL not found"));
//    }
}

