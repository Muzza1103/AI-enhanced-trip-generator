package com.l3g1.apitraveller.cache;

import com.l3g1.apitraveller.model.Suggestion;
import com.l3g1.apitraveller.model.Survey;
import com.l3g1.apitraveller.model.TripSuggestion;
import com.l3g1.apitraveller.model.TripSurvey;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {
    //Configuration class for managing caching of destination and trip suggestions.


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

    //Return the list of suggestions for the specific survey if it is in the cache
    public List<List<Suggestion>> getSuggestionFromCache(Survey survey) throws IllegalArgumentException {
        Cache cache = destinationCacheManager().getCache("destinationSuggestion");
        Survey cacheKey = survey;
        Cache.ValueWrapper valueWrapper = cache != null ? cache.get(cacheKey) : null;

        if (valueWrapper != null) {
            return (List<List<Suggestion>>) valueWrapper.get();
        }

        return null;
    }


    //Put the list of suggestions for the specific survey in the cache
    public void putSuggestionInCache(Survey survey, List<Suggestion> newSuggestions) throws IllegalArgumentException {
        Cache cache = destinationCacheManager().getCache("destinationSuggestion");
        Survey cacheKey = survey;

        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache != null ? cache.get(cacheKey) : null;
            List<List<Suggestion>> existingSuggestions = new ArrayList<>();
            if (valueWrapper != null) {
                existingSuggestions = (List<List<Suggestion>>) valueWrapper.get();
            }
            List<List<Suggestion>> mergedSuggestions = mergeSuggestions(existingSuggestions, newSuggestions);
            cache.put(cacheKey, mergedSuggestions);
        }
    }
     // Merges existing suggestions with new suggestions into a single list of lists.

    private List<List<Suggestion>> mergeSuggestions(List<List<Suggestion>> existingSuggestions, List<Suggestion> newSuggestions) {
        List<List<Suggestion>> mergedSuggestions = new ArrayList<>();
        if (existingSuggestions != null) {
            for (List<Suggestion> suggestion : existingSuggestions) {
                mergedSuggestions.add(suggestion);
            }
        }
        if (newSuggestions != null) {
            mergedSuggestions.add(newSuggestions);
        }
        return mergedSuggestions;
    }

    // Reset the cache every 30 minutes
    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void evictObsoleteEntries() {
        Cache cache = destinationCacheManager().getCache("destinationSuggestion");
        if (cache != null) {
            cache.clear();
        }
    }

    //Return the list of suggestions for the specific survey if it is in the cache
    public List<List<TripSuggestion>> getTripSuggestionFromCache(TripSurvey survey) throws IllegalArgumentException {
        Cache cache = tripCacheManager().getCache("tripSuggestion");
        TripSurvey cacheKey = survey;
        Cache.ValueWrapper valueWrapper = cache != null ? cache.get(cacheKey) : null;

        if (valueWrapper != null) {
            return (List<List<TripSuggestion>>) valueWrapper.get();
        }

        return null;
    }

    //Put the list of suggestions for the specific survey in the cache
    public void putTripSuggestionInCache(TripSurvey survey, List<TripSuggestion> newSuggestions) throws IllegalArgumentException {
        Cache cache = tripCacheManager().getCache("tripSuggestion");
        TripSurvey cacheKey = survey;

        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache != null ? cache.get(cacheKey) : null;
            List<List<TripSuggestion>> existingSuggestions = new ArrayList<>();
            if (valueWrapper != null) {
                existingSuggestions = (List<List<TripSuggestion>>) valueWrapper.get();
            }
            List<List<TripSuggestion>> mergedSuggestions = mergeTripSuggestions(existingSuggestions, newSuggestions);
            cache.put(cacheKey, mergedSuggestions);
        }
    }

    // Reset the cache every 30 minutes
    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void evictObsoleteEntriesTrip() {
        Cache cache = tripCacheManager().getCache("tripSuggestion");
        if (cache != null) {
            cache.clear();
        }
    }
    // Merges existing trip suggestions with new trip suggestions into a single list of lists
    private List<List<TripSuggestion>> mergeTripSuggestions(List<List<TripSuggestion>> existingSuggestions, List<TripSuggestion> newSuggestions) {
        List<List<TripSuggestion>> mergedSuggestions = new ArrayList<>();
        if (existingSuggestions != null) {
            for (List<TripSuggestion> listSuggestion : existingSuggestions) {
                mergedSuggestions.add(listSuggestion);
            }
        }
        if (newSuggestions != null) {
            mergedSuggestions.add(newSuggestions);
        }
        return mergedSuggestions;
    }

}