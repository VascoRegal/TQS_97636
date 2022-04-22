package ua.tqs.tqscovid.api;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.tqs.tqscovid.http.CovIncidenceHttpClient;
import ua.tqs.tqscovid.http.IHttpClient;
import ua.tqs.tqscovid.utils.JsonUtils;

public class HttpClientImplTest {
    
    private IHttpClient httpClient;

    @BeforeEach
    void setup() {
        this.httpClient = new CovIncidenceHttpClient();
    }

    @Test
    void doGet_RightResultsAndReturnsJSON() throws IOException, ParseException {
        String uri = "https://jsonplaceholder.typicode.com/todos/1";

        String get = this.httpClient.doGet(uri, new HashMap<>());

        JSONObject obj = JsonUtils.responseToJson(get);
        assertTrue(obj.get("userId").equals(1L));
        assertTrue(obj.get("title").equals("delectus aut autem"));
    }
}
