package ua.tqs.tqscovid.http;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@Service
public class CovIncidenceHttpClient implements IHttpClient {
    @Override
    public String doGet(String url, Map<String, Object> headers) throws IOException, ParseException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "<HTTP> GET  {0} ", url);

        for (Map.Entry<String, Object> entry: headers.entrySet()) {
            request.setHeader(entry.getKey(), entry.getValue().toString());
        }
            
        CloseableHttpResponse response = client.execute(request);
        try {
            HttpEntity entity = response.getEntity();
            String resp = EntityUtils.toString(entity);

            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "<HTTP> {0}", resp);

            return resp;
        } finally {
            if (response != null)
                response.close();
        }
    }

}
