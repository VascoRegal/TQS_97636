package ua.tqs.tqscovid.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.tqs.tqscovid.http.CovIncidenceHttpClient;
import ua.tqs.tqscovid.http.IHttpClient;

public class HttpClientImplTest {
    
    private IHttpClient httpClient;

    @BeforeEach
    void setup() {
        this.httpClient = new CovIncidenceHttpClient();
    }

    @Test
    void doGet_RightResults() {
        
    }
}
