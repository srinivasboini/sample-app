package com.srini.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Statistics report for an in-memory cache")
public class CacheStatsResponse {

    @Schema(description = "Cache name", example = "products")
    private String cacheName;

    @Schema(description = "Cache hits count", example = "42")
    private long hitCount;

    @Schema(description = "Cache misses count", example = "5")
    private long missCount;

    @Schema(description = "Cache hit rate percentage", example = "89.36%")
    private String hitRate;

    @Schema(description = "Total eviction count", example = "0")
    private long evictionCount;

    @Schema(description = "Estimated number of entries cached", example = "10")
    private long estimatedSize;

    public CacheStatsResponse() {
    }

    public CacheStatsResponse(String cacheName, long hitCount, long missCount, String hitRate, long evictionCount, long estimatedSize) {
        this.cacheName = cacheName;
        this.hitCount = hitCount;
        this.missCount = missCount;
        this.hitRate = hitRate;
        this.evictionCount = evictionCount;
        this.estimatedSize = estimatedSize;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public long getHitCount() {
        return hitCount;
    }

    public void setHitCount(long hitCount) {
        this.hitCount = hitCount;
    }

    public long getMissCount() {
        return missCount;
    }

    public void setMissCount(long missCount) {
        this.missCount = missCount;
    }

    public String getHitRate() {
        return hitRate;
    }

    public void setHitRate(String hitRate) {
        this.hitRate = hitRate;
    }

    public long getEvictionCount() {
        return evictionCount;
    }

    public void setEvictionCount(long evictionCount) {
        this.evictionCount = evictionCount;
    }

    public long getEstimatedSize() {
        return estimatedSize;
    }

    public void setEstimatedSize(long estimatedSize) {
        this.estimatedSize = estimatedSize;
    }
}
