package ua.tqs.tqscovid.api;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.tqs.tqscovid.cache.CacheServiceImpl;

public class CacheTest {
    private CacheServiceImpl cache;
    private long test_expiracy = 2;

    @BeforeEach
    void setup() {
        this.cache = new CacheServiceImpl();
        this.cache.setExpiracy(test_expiracy);
    }

    @Test
    void testExpiracy() throws InterruptedException {
        this.cache.put("chave", "valor");
        Thread.sleep((test_expiracy + 1) * 1000);
        assertTrue(this.cache.get("chave").isEmpty());

        this.cache.put("chave", "valor");
        Thread.sleep((test_expiracy/2) * 1000);
        assertFalse(this.cache.get("chave").isEmpty());
    }


}
