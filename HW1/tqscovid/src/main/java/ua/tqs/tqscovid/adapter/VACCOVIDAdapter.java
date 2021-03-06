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
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

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

public class VACCOVIDAdapter implements IExternalAPIAdapter {
    private static final String BASE_URI = "https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com/api"; 
    private String apiKey;

    @Autowired
    private IHttpClient httpClient;

    private Map<String, Object> baseHeaders;
    private Map<String, String> isoMap;

    public VACCOVIDAdapter() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "<ADAPTER> Using VACCOVID Adaper.");
        this.apiKey = ConfigUtils.getPropertyFromConfig("rapidapi.key");

        this.baseHeaders = new HashMap<>();
        baseHeaders.put("X-RapidAPI-Key", this.apiKey);
    }

    @Override
    public List<Country> getCountries() throws URISyntaxException, ParseException, IOException {
        if (this.isoMap == null)
        this.isoMap = this.generateIsoMap();
        return new ArrayList<>(this.isoMap.keySet()).stream().map(n -> new Country(n)).collect(Collectors.toList());
    }

    @Override
    public List<DailyStats> getStatsByCountry(String country) throws ParseException, IOException, URISyntaxException {
        if (this.isoMap == null)
        this.isoMap = this.generateIsoMap();
        String path = "/covid-ovid-data/sixmonth/" + this.isoMap.get(country);
        JSONArray jsonResp = JsonUtils.responseToJsonArray(this.httpClient.doGet(VACCOVIDAdapter.BASE_URI + path, this.baseHeaders));

        List<DailyStats> stats = new ArrayList<>();

        for (Object o: (JSONArray) jsonResp) {
            JSONObject obj = (JSONObject) o;

            stats.add(new DailyStats(LocalDate.parse(obj.get("date").toString()), new Country(country),
                this.parseCases(obj), 
                this.parseDeaths(obj), 
                this.parseTests(obj)));
        }
        return stats;
    }

    @Override
    public List<DailyStats> getAllStats() throws ParseException, IOException, URISyntaxException {
        String path = "/covid-ovid-data/";
        JSONArray jsonResp = JsonUtils.responseToJsonArray(this.httpClient.doGet(VACCOVIDAdapter.BASE_URI + path, this.baseHeaders));

        List<DailyStats> stats = new ArrayList<>();

        for (Object o: (JSONArray) jsonResp) {
            JSONObject obj = (JSONObject) o;

            stats.add(new DailyStats(LocalDate.parse(obj.get("date").toString()), new Country("All"),
                this.parseCases(obj), 
                this.parseDeaths(obj), 
                this.parseTests(obj)));
        }
        return stats;
    }

    @Override
    public List<DailyStats> getStatsByDay(LocalDate date, String country)
            throws URISyntaxException, ParseException, IOException {
        return new ArrayList<>();
    }

    public Map<String, String> generateIsoMap() throws ParseException, IOException {
        String path = "/npm-covid-data/countries-name-ordered";
        Map<String, String> map = new HashMap<>();
        String uri = VACCOVIDAdapter.BASE_URI + path;
        JSONArray jsonResp = JsonUtils.responseToJsonArray(this.httpClient.doGet(uri, this.baseHeaders));
        for (Object o: jsonResp) {
            JSONObject obj = (JSONObject) o;
            map.put(obj.get("Country").toString(), obj.get("ThreeLetterSymbol").toString());
        }
        return map;
    }

    private Cases parseCases(JSONObject caseObject) {
        Integer balance = Optional.ofNullable(caseObject.get("new_cases")).map(this::retrieveIntFromField).orElse(null);
        Integer active = null;
        Integer critical = null;
        Integer recovered = null;
        Integer per_million = null;
        Integer total = Optional.ofNullable(caseObject.get("total_cases")).map(this::retrieveIntFromField).orElse(null);

        return new Cases(balance, active, critical, recovered, per_million, total);
    }

    private Deaths parseDeaths(JSONObject deathObject) {
        Integer balance = Optional.ofNullable(deathObject.get("321")).map(this::retrieveIntFromField).orElse(null);
        Integer per_million = null;
        Integer total = Optional.ofNullable(deathObject.get("total_deaths")).map(this::retrieveIntFromField).orElse(null);

        return new Deaths(balance, per_million, total);
    }

    private Tests parseTests(JSONObject testObject) {
        Integer per_million = null;
        Integer total = Optional.ofNullable(testObject.get("total_tests")).map(this::retrieveIntFromField).orElse(null);

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
