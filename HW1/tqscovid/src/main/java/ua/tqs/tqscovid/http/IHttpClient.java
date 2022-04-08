package ua.tqs.tqscovid.http;

import java.io.IOException;

import org.json.simple.parser.ParseException;

public interface IHttpClient {
    public String doGet(String url) throws IOException, ParseException;
}
