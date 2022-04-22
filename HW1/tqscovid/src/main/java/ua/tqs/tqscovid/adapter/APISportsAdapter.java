package ua.tqs.tqscovid.adapter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.tqs.tqscovid.http.IHttpClient;
import ua.tqs.tqscovid.models.Cases;
import ua.tqs.tqscovid.models.Country;
import ua.tqs.tqscovid.models.DailyStats;
import ua.tqs.tqscovid.models.Deaths;
import ua.tqs.tqscovid.models.Tests;
import ua.tqs.tqscovid.utils.ConfigUtils;
import ua.tqs.tqscovid.utils.JsonUtils;

public class APISportsAdapter implements IExternalAPIAdapter {
    
    private static final String BASE_URI = "https://covid-193.p.rapidapi.com"; 
    private static final String JSON_BODY_KEY = "response";
    private String apiKey;

    @Autowired
    private IHttpClient httpClient;

    private Map<String, Object> baseHeaders;

    public APISportsAdapter() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "<ADAPTER> Using APISports Adaper.");
        this.apiKey = ConfigUtils.getPropertyFromConfig("rapidapi.key");

        this.baseHeaders = new HashMap<>();
        baseHeaders.put("X-RapidAPI-Key", this.apiKey);
        
    }

    @Override
    public List<Country> getCountries() throws ParseException, IOException {
        String path = "/countries";
        String uri = APISportsAdapter.BASE_URI + path;
        JSONObject jsonResp = JsonUtils.responseToJson(this.httpClient.doGet(uri, this.baseHeaders));
        List<Country> countires = new ArrayList<>();

        for (Object o: (JSONArray) jsonResp.get(APISportsAdapter.JSON_BODY_KEY)) {
            countires.add(new Country(o.toString()));
        }

        return countires;   
    }

    @Override
    public List<DailyStats> getStatsByCountry(String country) throws ParseException, IOException, URISyntaxException {
        String path = "/history";
        
        URIBuilder uriBuilder = new URIBuilder(APISportsAdapter.BASE_URI + path);
        
        uriBuilder.addParameter("country", country);
        JSONObject jsonResp = JsonUtils.responseToJson(this.httpClient.doGet(uriBuilder.build().toString(), this.baseHeaders));
        List<DailyStats> stats = new ArrayList<>();
        List<String> repeatedData = new ArrayList<>();
        for (Object object: (JSONArray) jsonResp.get(APISportsAdapter.JSON_BODY_KEY)) {
            JSONObject baseObject = (JSONObject) object;
            DailyStats dailyStats = new DailyStats();
            String countryName = baseObject.get("country").toString();
            String day = baseObject.get("day").toString();
            String repetitionKey = countryName + ":" + day;

            if (repeatedData.contains(repetitionKey)) {
                continue;
            }
            dailyStats.setCountry(new Country(baseObject.get("country").toString()));
            
            JSONObject caseObject = (JSONObject) baseObject.get("cases");
            JSONObject deathObject = (JSONObject) baseObject.get("deaths");
            JSONObject testObject = (JSONObject) baseObject.get("tests");

            dailyStats.setCases(this.parseCases(caseObject));
            dailyStats.setDeaths(this.parseDeaths(deathObject));
            dailyStats.setTests(this.parseTests(testObject));

            dailyStats.setDay(LocalDate.parse(baseObject.get("day").toString()));

            stats.add(dailyStats);
            repeatedData.add(repetitionKey);
        }

        return stats;   
    }

    @Override
    public List<DailyStats> getAllStats() throws ParseException, IOException, URISyntaxException {
        return getStatsByCountry("All");
    }

    @Override
    public List<DailyStats> getStatsByDay(LocalDate date, String country) throws URISyntaxException, ParseException, IOException {
        String path = "/history";

        URIBuilder uriBuilder = new URIBuilder(APISportsAdapter.BASE_URI + path);
        if (country != null) {
            uriBuilder.addParameter("country", country);
        } else {
            uriBuilder.addParameter("country", "All");
        }
        if (date != null) {
            uriBuilder.addParameter("day", date.toString());
        }
        
        JSONObject jsonResp = JsonUtils.responseToJson(this.httpClient.doGet(uriBuilder.build().toString(), this.baseHeaders));
        List<DailyStats> stats = new ArrayList<>();
        List<String> countires = new ArrayList<>();

        for (Object object: (JSONArray) jsonResp.get(APISportsAdapter.JSON_BODY_KEY)) {
            JSONObject baseObject = (JSONObject) object;
            DailyStats dailyStats = new DailyStats();
            String countryName = baseObject.get("country").toString();

            if (countires.contains(countryName)) {
                continue;
            }

            dailyStats.setCountry(new Country(countryName));
            
            JSONObject caseObject = (JSONObject) baseObject.get("cases");
            JSONObject deathObject = (JSONObject) baseObject.get("deaths");
            JSONObject testObject = (JSONObject) baseObject.get("tests");

            dailyStats.setCases(this.parseCases(caseObject));
            dailyStats.setDeaths(this.parseDeaths(deathObject));
            dailyStats.setTests(this.parseTests(testObject));

            dailyStats.setDay(LocalDate.parse(baseObject.get("day").toString()));
            
            countires.add(countryName);
            stats.add(dailyStats);
            
        }

        return stats;   
    }

    private Cases parseCases(JSONObject caseObject) {
        Integer balance = Optional.ofNullable(caseObject.get("new")).map(this::retrieveIntFromField).orElse(null);
        Integer active = Optional.ofNullable(caseObject.get("active")).map(this::retrieveIntFromField).orElse(null);
        Integer critical = Optional.ofNullable(caseObject.get("critical")).map(this::retrieveIntFromField).orElse(null);
        Integer recovered = Optional.ofNullable(caseObject.get("recovered")).map(this::retrieveIntFromField).orElse(null);
        Integer per_million = Optional.ofNullable(caseObject.get("1M_pop")).map(this::retrieveIntFromField).orElse(null);
        Integer total = Optional.ofNullable(caseObject.get("total")).map(this::retrieveIntFromField).orElse(null);

        return new Cases(balance, active, critical, recovered, per_million, total);
    }

    private Deaths parseDeaths(JSONObject deathObject) {
        Integer balance = Optional.ofNullable(deathObject.get("new")).map(this::retrieveIntFromField).orElse(null);
        Integer per_million = Optional.ofNullable(deathObject.get("1M_pop")).map(this::retrieveIntFromField).orElse(null);
        Integer total = Optional.ofNullable(deathObject.get("total")).map(this::retrieveIntFromField).orElse(null);

        return new Deaths(balance, per_million, total);
    }

    private Tests parseTests(JSONObject testObject) {
        Integer per_million = Optional.ofNullable(testObject.get("1M_pop")).map(this::retrieveIntFromField).orElse(null);
        Integer total = Optional.ofNullable(testObject.get("total")).map(this::retrieveIntFromField).orElse(null);

        return new Tests(per_million, total);
    }

    private Integer retrieveIntFromField(Object field) {
        try {
            String original = field.toString();
            Integer num = Integer.parseInt(field.toString().replace("+", "").replace("-", ""));
            if (original.contains("-")) {
                num = -num;
            }

            return num;
        } catch(Exception e)  {
            return null;
        }
    }

}
