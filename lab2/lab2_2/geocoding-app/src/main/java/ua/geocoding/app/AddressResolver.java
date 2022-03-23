package ua.geocoding.app;

import java.util.Optional;
import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Formatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AddressResolver {
    private final String apiKey = "ONfn02Jhtc0SGndDdIEHRoYFhwS7AbQ0";
    private ISimpleHTTPClient httpClient;

    public Optional<Address> findAddressForLocation(double latitude, double longitude) throws ParseException, URISyntaxException, IOException {
        //String apiKey = ConfigUtils.getPropertyFromConfig("mapquest_key");

        if (!this.validCoords(latitude, longitude)) {
            return Optional.empty();
        }

        URIBuilder uriBuilder = new URIBuilder("http://open.mapquestapi.com/geocoding/v1/reverse");
        uriBuilder.addParameter("key", apiKey);
        uriBuilder.addParameter("location", (new Formatter()).format(Locale.US, "%.6f,%.6f", latitude, longitude).toString());
        uriBuilder.addParameter("includeRoadMetadata", "true");


        String apiResponse = this.httpClient.doHttpGet(uriBuilder.build().toString());
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, apiResponse);

        if (apiResponse == null) {
            return Optional.empty();
        }

        // get parts from response till reaching the address
        JSONObject obj = (JSONObject) new JSONParser().parse(apiResponse);

        // get the first element of the results array
        obj = (JSONObject) ((JSONArray) obj.get("results")).get(0);

        if (((JSONArray) obj.get("locations")).isEmpty()) {
            return Optional.empty();
        } else {
            JSONObject address = (JSONObject) ((JSONArray) obj.get("locations")).get(0);
            String road = (String) address.get("street");
            String city = (String) address.get("adminArea5");
            String state = (String) address.get("adminArea3");
            String zip = (String) address.get("postalCode");
            return Optional.of(new Address(road, city, state, zip, null));


        }

    }

    public boolean validCoords(double lat, double lon) {
        if (lat > - 90.0000 && lat < 90.0000 && lon > - 180.0000 && lon < 180.0000) {
            return true;
        }

        return false;
    }
}
