package ua.tqs.tqscovid.http;

import java.io.IOException;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class CovIncidenceHttpClient implements IHttpClient {
    @Override
    public String doGet(String url, Map<String, Object> headers) throws IOException, ParseException {
        return null;
    }

}
