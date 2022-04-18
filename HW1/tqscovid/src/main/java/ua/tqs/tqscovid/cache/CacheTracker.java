package ua.tqs.tqscovid.cache;

import org.springframework.stereotype.Service;

@Service
public class CacheTracker {
    private long totalRequests, cachedRequests, avgNonCacheTime, avgCacheTime;

    public CacheTracker() {
        this.totalRequests = 0;
        this.cachedRequests = 0;
        this.avgNonCacheTime = 0;
        this.avgCacheTime = 0;
    }

    public void trackRequest(boolean cached, long timediff) {
        this.totalRequests += 1;
        if (cached) {
            this.cachedRequests += 1;
            this.avgCacheTime = (this.avgCacheTime + timediff) / this.cachedRequests;
            return;
        }

        this.avgNonCacheTime = (this.avgNonCacheTime + timediff) / (this.totalRequests - this.cachedRequests);
    }

    public long getTotal() {
        return this.totalRequests;
    }

    public long getCached() {
        return this.cachedRequests;
    }

    public long getNotCachedTime() {
        return this.avgNonCacheTime;
    }

    public long getCachedTime() {
        return this.avgCacheTime;
    }

    public String toString() {
        return String.format("[total = %d | cached = %d | avg non cached time = %d | avg cached time = %d]", totalRequests, cachedRequests, avgNonCacheTime, avgCacheTime);
    }
}
