package ua.geocoding.app;

import java.io.IOException;

public interface ISimpleHTTPClient {
    public String doHttpGet(String coords) throws IOException;
}
