package com.srini.demo.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.srini.demo.dto.ApiResponse;
import com.srini.demo.dto.CacheStatsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cache")
@Tag(name = "Cache Management", description = "Monitor performance statistics and manage Caffeine in-memory caches (Admin only)")
@SecurityRequirement(name = "BearerAuth")
public class CacheManagementController {

    private final CacheManager cacheManager;

    public CacheManagementController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @GetMapping("/stats")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get cache performance metrics", description = "Returns hit counts, miss counts, hit rates, eviction counts, and estimated sizes for all active caches")
    public ResponseEntity<ApiResponse<List<CacheStatsResponse>>> getCacheStats() {
        List<CacheStatsResponse> statsList = new ArrayList<>();

        for (String cacheName : cacheManager.getCacheNames()) {
            org.springframework.cache.Cache springCache = cacheManager.getCache(cacheName);
            if (springCache instanceof CaffeineCache caffeineCache) {
                Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
                CacheStats stats = nativeCache.stats();

                double hitRatePercent = stats.hitRate() * 100.0;
                String hitRateFormatted = String.format("%.2f%%", hitRatePercent);

                statsList.add(new CacheStatsResponse(
                        cacheName,
                        stats.hitCount(),
                        stats.missCount(),
                        hitRateFormatted,
                        stats.evictionCount(),
                        nativeCache.estimatedSize()
                ));
            }
        }

        return ResponseEntity.ok(ApiResponse.ok(statsList, "Cache metrics retrieved successfully"));
    }

    @PostMapping("/clear")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Evict cache entries", description = "Clears all cached entries or a specific cache if `cacheName` parameter is provided")
    public ResponseEntity<ApiResponse<String>> clearCache(
            @Parameter(description = "Optional name of the specific cache to clear") @RequestParam(required = false) String cacheName) {

        if (cacheName != null && !cacheName.isBlank()) {
            org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
                return ResponseEntity.ok(ApiResponse.ok("Cache '" + cacheName + "' cleared successfully"));
            } else {
                return ResponseEntity.badRequest().body(ApiResponse.error("Cache '" + cacheName + "' does not exist"));
            }
        }

        for (String name : cacheManager.getCacheNames()) {
            org.springframework.cache.Cache cache = cacheManager.getCache(name);
            if (cache != null) {
                cache.clear();
            }
        }

        return ResponseEntity.ok(ApiResponse.ok("All in-memory caches cleared successfully"));
    }
}
