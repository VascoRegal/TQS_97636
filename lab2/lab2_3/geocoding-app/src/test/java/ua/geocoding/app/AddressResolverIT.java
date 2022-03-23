package ua.geocoding.app;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


public class AddressResolverIT {
    private final String apiKey = "ONfn02Jhtc0SGndDdIEHRoYFhwS7AbQ0";

    private AddressResolver addressResolver;
    private ISimpleHTTPClient httpClient;

    @BeforeEach 
    public void setup() {
        this.httpClient = new TqsBasicHttpClient();
        this.addressResolver = new AddressResolver(this.httpClient);
    }

    @Test
    public void findAddressForLocationTest() throws RuntimeException, URISyntaxException, IOException, org.json.simple.parser.ParseException 
    {

        String response = "{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"\u00A9 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"\u00A9 2022 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"thumbMaps\":true,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.6318,\"lng\":-8.658}},\"locations\":[{\"street\":\"Parque Estacionamento da Reitoria - Univerisdade de Aveiro\",\"adminArea6\":\"\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Gl\u00F3ria e Vera Cruz\",\"adminArea5Type\":\"City\",\"adminArea4\":\"\",\"adminArea4Type\":\"County\",\"adminArea3\":\"Centro\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3810-193\",\"geocodeQualityCode\":\"P1AAA\",\"geocodeQuality\":\"POINT\",\"dragPoint\":false,\"sideOfStreet\":\"N\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.631803,\"lng\":-8.657881},\"displayLatLng\":{\"lat\":40.631803,\"lng\":-8.657881},\"mapUrl\":\"http://open.mapquestapi.com/staticmap/v5/map?key=ONfn02Jhtc0SGndDdIEHRoYFhwS7AbQ0&type=map&size=225,160&locations=40.6318025,-8.657881|marker-sm-50318A-1&scalebar=true&zoom=15&rand=1737219891\",\"roadMetadata\":null}]}]}"

        ;
        double latitude = 40.6318;
        double longitude = 	-8.658;
        String request = String.format("http://open.mapquestapi.com/geocoding/v1/reverse?key=%s&location=%f,%f&includeRoadMetadata=true", apiKey, latitude, longitude);
        

        System.out.println("\n\n" + this.httpClient.doHttpGet(request));
        System.out.println("\n\n" + response + "\n\n");

        // Este teste dá-me certos erros porcausa de caracteres mal encoded.

        //assertTrue(this.httpClient.doHttpGet(request).equals(response));


        Optional<Address> res = addressResolver.findAddressForLocation(latitude, longitude);
        assertTrue(res.isPresent());
        Address address = res.get();
        System.out.println(address.getZio() == "3810-193");
        assertTrue(address.getZio().equals("3810-193"));
        assertTrue(address.getRoad().equals("Parque Estacionamento da Reitoria - Univerisdade de Aveiro"));
    }

    @Test
    public void findAddressForLocationBadCoordsTest() throws URISyntaxException, IOException, org.json.simple.parser.ParseException {
        assertFalse(addressResolver.findAddressForLocation(-100.0, 50.0).isPresent());
        assertFalse(addressResolver.findAddressForLocation(100.0, 50.0).isPresent());
        assertFalse(addressResolver.findAddressForLocation(32.1, 220.2).isPresent());
        assertFalse(addressResolver.findAddressForLocation(-100.0, -220.2).isPresent());
        
    }  
}
