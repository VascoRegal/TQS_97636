package ua.tqs.tqscovid.cache;

import java.util.Optional;

public interface ICacheService<K, V> {
    public Optional<V> get(K key);
    public void put(K key, V value);
    public void pop(String key);
}
