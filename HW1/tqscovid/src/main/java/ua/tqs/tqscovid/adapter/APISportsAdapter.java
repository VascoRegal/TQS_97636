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
import org.springframework.stereotype.Service;

import ua.tqs.tqscovid.http.IHttpClient;
import ua.tqs.tqscovid.models.Cases;
import ua.tqs.tqscovid.models.Country;
import ua.tqs.tqscovid.models.DailyStats;
import ua.tqs.tqscovid.models.Deaths;
import ua.tqs.tqscovid.models.Tests;
import ua.tqs.tqscovid.utils.ConfigUtils;
import ua.tqs.tqscovid.utils.JsonUtils;

@Service
public class APISportsAdapter implements IExternalAPIAdapter {
    
    private final String baseUri = "https://covid-193.p.rapidapi.com"; 
    private String apiKey;

    private IHttpClient httpClient;

    private Map<String, Object> baseHeaders;

    public APISportsAdapter(IHttpClient httpClient) {
        this.httpClient = httpClient;
        this.apiKey = ConfigUtils.getPropertyFromConfig("rapidapi.key");

        this.baseHeaders = new HashMap<String, Object>();
        baseHeaders.put("X-RapidAPI-Key", this.apiKey);
        
    }

    @Override
    public List<Country> getCountries() throws ParseException, IOException {
        String path = "/countries";
        String uri = this.baseUri + path;
        JSONObject jsonResp = JsonUtils.responseToJson(this.httpClient.doGet(uri, this.baseHeaders));
        List<Country> countires = new ArrayList<Country>();

        for (Object o: (JSONArray) jsonResp.get("response")) {
            countires.add(new Country(o.toString()));
        };

        return countires;   
    }

    @Override
    public List<DailyStats> getStatsByCountry(String country) throws ParseException, IOException, URISyntaxException {
        String path = "/history";
        
        URIBuilder uriBuilder = new URIBuilder(this.baseUri + path);
        
        uriBuilder.addParameter("country", country);
        JSONObject jsonResp = JsonUtils.responseToJson(this.httpClient.doGet(uriBuilder.build().toString(), this.baseHeaders));
        List<DailyStats> stats = new ArrayList<DailyStats>();
        List<String> repeatedData = new ArrayList<>();
        for (Object object: (JSONArray) jsonResp.get("response")) {
            JSONObject baseObject = (JSONObject) object;
            DailyStats dailyStats = new DailyStats();
            String country_name = baseObject.get("country").toString();
            String day = baseObject.get("day").toString();
            String repetitionKey = country_name + ":" + day;

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
        };

        return stats;   
    }

    @Override
    public List<DailyStats> getAllStats() throws ParseException, IOException, URISyntaxException {
        return getStatsByCountry("All");
    }

    @Override
    public List<DailyStats> getStatsByDay(LocalDate date, String country) throws URISyntaxException, ParseException, IOException {
        String path = "/history";

        URIBuilder uriBuilder = new URIBuilder(this.baseUri + path);
        if (country != null) {
            uriBuilder.addParameter("country", country);
        } else {
            uriBuilder.addParameter("country", "All");
        }
        if (date != null) {
            uriBuilder.addParameter("day", date.toString());
        }
        
        JSONObject jsonResp = JsonUtils.responseToJson(this.httpClient.doGet(uriBuilder.build().toString(), this.baseHeaders));
        List<DailyStats> stats = new ArrayList<DailyStats>();
        List<String> countires = new ArrayList<>();

        for (Object object: (JSONArray) jsonResp.get("response")) {
            JSONObject baseObject = (JSONObject) object;
            DailyStats dailyStats = new DailyStats();
            String country_name = baseObject.get("country").toString();

            if (countires.contains(country_name)) {
                continue;
            }

            dailyStats.setCountry(new Country(country_name));
            
            JSONObject caseObject = (JSONObject) baseObject.get("cases");
            JSONObject deathObject = (JSONObject) baseObject.get("deaths");
            JSONObject testObject = (JSONObject) baseObject.get("tests");

            dailyStats.setCases(this.parseCases(caseObject));
            dailyStats.setDeaths(this.parseDeaths(deathObject));
            dailyStats.setTests(this.parseTests(testObject));

            dailyStats.setDay(LocalDate.parse(baseObject.get("day").toString()));
            
            countires.add(country_name);
            stats.add(dailyStats);
            
        };

        return stats;   
    }

    private Cases parseCases(JSONObject caseObject) {
        Integer balance = Optional.ofNullable(caseObject.get("new")).map(n -> retrieveIntFromField(n)).orElse(null);
        Integer active = Optional.ofNullable(caseObject.get("active")).map(n -> retrieveIntFromField(n)).orElse(null);
        Integer critical = Optional.ofNullable(caseObject.get("critical")).map(n -> retrieveIntFromField(n)).orElse(null);
        Integer recovered = Optional.ofNullable(caseObject.get("recovered")).map(n -> retrieveIntFromField(n)).orElse(null);
        Integer per_million = Optional.ofNullable(caseObject.get("1M_pop")).map(n -> retrieveIntFromField(n)).orElse(null);
        Integer total = Optional.ofNullable(caseObject.get("total")).map(n -> retrieveIntFromField(n)).orElse(null);

        return new Cases(balance, active, critical, recovered, per_million, total);
    }

    private Deaths parseDeaths(JSONObject deathObject) {
        Integer balance = Optional.ofNullable(deathObject.get("new")).map(n -> retrieveIntFromField(n)).orElse(null);
        Integer per_million = Optional.ofNullable(deathObject.get("1M_pop")).map(n -> retrieveIntFromField(n)).orElse(null);
        Integer total = Optional.ofNullable(deathObject.get("total")).map(n -> retrieveIntFromField(n)).orElse(null);

        return new Deaths(balance, per_million, total);
    }

    private Tests parseTests(JSONObject testObject) {
        Integer per_million = Optional.ofNullable(testObject.get("1M_pop")).map(n -> retrieveIntFromField(n)).orElse(null);
        Integer total = Optional.ofNullable(testObject.get("total")).map(n -> retrieveIntFromField(n)).orElse(null);

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
