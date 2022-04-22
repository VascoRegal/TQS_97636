package ua.tqs.tqscovid.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.springframework.stereotype.Service;

import ua.tqs.tqscovid.utils.ConfigUtils;

@Service
public class CacheServiceImpl implements ICacheService<String, Object> {
    private long expiracy = Long.parseLong(ConfigUtils.getPropertyFromConfig("cache.expiracy"));
    private Map<String, CacheEntry> map = new HashMap<String, CacheEntry>();

    @Override
    public Optional<Object> get(String key) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "<CACHE>[ GET -> {0} ] Fetching key...", key);
        if (exists(key)) {
            CacheEntry entry = map.get(key);
            if (!isExpired(entry)) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "<CACHE>[ GET ->  {0} ] Found key! Returning cached results...", key);
                return Optional.of(entry.getValue());
            }
            
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "<CACHE>[ GET -> {0} ] Expired key.", key);
            this.pop(key);
            return Optional.empty();
        }

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "<CACHE>[ GET -> {0} ] Not Found", key);
        return Optional.empty();
    }

    @Override
    public void put(String key, Object value) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "<CACHE>[ PUT -> {0} ] Putting key...", key);
        map.put(key, new CacheEntry(value));
        
    }

    @Override
    public void pop(String key) {
        if (exists(key)) {
            map.remove(key);
        }
    }

    public boolean isExpired(CacheEntry ce) {
        return (ce.getTs().getTime() + this.expiracy * 1000) < new Date().getTime();
    }

    public boolean exists(String key) {
        return map.containsKey(key);
    }

    public void setExpiracy(long exp) {
        this.expiracy = exp;
    }

}
