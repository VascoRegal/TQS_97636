package ua.tqs.tqscovid.models;

import org.apache.catalina.webresources.Cache;

public class CacheStats {
    private long total, cached, noCacheTime, cacheTime;

    public CacheStats() { }

    public CacheStats(long total, long cache, long noCacheTime, long cacheTime) {
        this.total = total;
        this.cached = cache;
        this.noCacheTime = noCacheTime;
        this.cacheTime = cacheTime;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getCached() {
        return this.cached;
    }

    public void setCached(long cached) {
        this.cached = cached;
    }

    public long getNoCacheTIme() {
        return this.noCacheTime;
    }

    public void setNoCacheTime(long noCacheTime) {
        this.noCacheTime = noCacheTime;
    }

    public long getCacheTime() {
        return this.cacheTime;
    }

    public void setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
    }


}
