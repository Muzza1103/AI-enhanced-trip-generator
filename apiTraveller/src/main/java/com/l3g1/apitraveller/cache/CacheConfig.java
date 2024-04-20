package com.l3g1.apitraveller.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableCaching
public class CacheConfig {

    // Configuration of the destinationSuggestion cache
    @Bean
    @Primary
    public CacheManager destinationCacheManager() {
        return new ConcurrentMapCacheManager("destinationSuggestion");
    }

    // Configuration of the tripSuggestion cache
    @Bean
    public CacheManager tripCacheManager() {
        return new ConcurrentMapCacheManager("tripSuggestion");
    }


}