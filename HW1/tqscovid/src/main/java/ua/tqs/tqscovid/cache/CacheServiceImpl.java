package ua.tqs.tqscovid.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import ua.tqs.tqscovid.utils.ConfigUtils;

public class CacheServiceImpl implements ICacheService<String, Object> {
    private long expiracy = Long.parseLong(ConfigUtils.getPropertyFromConfig("cache.expiracy"));
    private Map<String, CacheEntry> map = new HashMap<String, CacheEntry>();

    @Override
    public Optional<Object> get(String key) {
        System.out.println("getting " + key);
        if (exists(key)) {
            System.out.println("\tkey exists  " + key);
            CacheEntry entry = map.get(key);
            if (!isExpired(entry)) {
                System.out.println("\t\tNot Expired  " + key);
                return Optional.of(entry.getValue());
            }
            
            this.pop(key);
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public void put(String key, Object value) {
        System.out.println("putting " + key);
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
