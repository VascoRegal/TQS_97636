package ua.tqs.tqscovid.adapter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import ua.tqs.tqscovid.http.IHttpClient;
import ua.tqs.tqscovid.models.Cases;
import ua.tqs.tqscovid.models.Country;
import ua.tqs.tqscovid.models.DailyStats;
import ua.tqs.tqscovid.models.Deaths;
import ua.tqs.tqscovid.models.Tests;
import ua.tqs.tqscovid.utils.ConfigUtils;
import ua.tqs.tqscovid.utils.JsonUtils;

public class AkashAdapter implements IExternalAPIAdapter {

    private static final String baseUrl = "https://corona-virus-world-and-india-data.p.rapidapi.com/api";
    private String apiKey;

    private JSONObject globalRequest;

    private IHttpClient httpClient;
    private Map<String, Object> baseHeaders;

    public AkashAdapter(IHttpClient httpClient) throws ParseException, IOException {
        this.httpClient = httpClient;
        this.apiKey = ConfigUtils.getPropertyFromConfig("rapidapi.key");

        this.baseHeaders = new HashMap<>();
        baseHeaders.put("X-RapidAPI-Key", this.apiKey);
        this.globalRequest = this.getAndCache();
    }

    @Override
    public List<Country> getCountries() throws URISyntaxException, ParseException, IOException {
        List<Country> countries = new ArrayList<>();
        for (Object o: (JSONArray) this.globalRequest.get("countries_stat")) {
            JSONObject baseObject = (JSONObject) o;
            countries.add(new Country(baseObject.get("countryName").toString()));
        }
        return countries;
    }

    @Override
    public List<DailyStats> getStatsByCountry(String country) throws ParseException, IOException, URISyntaxException {
        List<DailyStats> stats = new ArrayList<>();
        String date = this.globalRequest.get("statistic_taken_at").toString().split(" ")[0];
        for (Object o: (JSONArray) this.globalRequest.get("countries_stat")) {
            JSONObject baseObject = (JSONObject) o;
            String countryName = baseObject.get("countryName").toString();
            if (country.equals(countryName)) {
                stats.add(new DailyStats(LocalDate.parse(date), 
                    new Country(country),
                    parseCases(baseObject),
                    parseDeaths(baseObject),
                    parseTests(baseObject)));

                return stats;
            }
        } 
        return stats;
    }

    @Override
    public List<DailyStats> getAllStats() throws ParseException, IOException, URISyntaxException {
        List<DailyStats> stats = new ArrayList<>();
        String date = this.globalRequest.get("statistic_taken_at").toString().split(" ")[0];
        for (Object o: (JSONArray) this.globalRequest.get("countries_stat")) {
            JSONObject baseObject = (JSONObject) o;
            String countryName = baseObject.get("countryName").toString();
            stats.add(new DailyStats(LocalDate.parse(date), 
                new Country(countryName),
                parseCases(baseObject),
                parseDeaths(baseObject),
                parseTests(baseObject)));
        } 
        return stats;
    }

    @Override
    public List<DailyStats> getStatsByDay(LocalDate date, String country)
            throws URISyntaxException, ParseException, IOException {
        return this.getStatsByCountry(country);
    }

    public JSONObject getAndCache() throws ParseException, IOException {
        return JsonUtils.responseToJson(this.httpClient.doGet(AkashAdapter.baseUrl, this.baseHeaders));
    }

    private Cases parseCases(JSONObject caseObject) {
        Integer balance = Optional.ofNullable(caseObject.get("new_cases")).map(this::retrieveIntFromField).orElse(null);
        Integer active = Optional.ofNullable(caseObject.get("active_cases")).map(this::retrieveIntFromField).orElse(null);
        Integer critical = Optional.ofNullable(caseObject.get("serious_critical")).map(this::retrieveIntFromField).orElse(null);
        Integer recovered = Optional.ofNullable(caseObject.get("total_recovered")).map(this::retrieveIntFromField).orElse(null);
        Integer per_million = Optional.ofNullable(caseObject.get("total_cases_per_1m_population")).map(this::retrieveIntFromField).orElse(null);
        Integer total = Optional.ofNullable(caseObject.get("cases")).map(this::retrieveIntFromField).orElse(null);

        return new Cases(balance, active, critical, recovered, per_million, total);
    }

    private Deaths parseDeaths(JSONObject deathObject) {
        Integer balance = Optional.ofNullable(deathObject.get("new_deaths")).map(this::retrieveIntFromField).orElse(null);
        Integer per_million = Optional.ofNullable(deathObject.get("deaths_per_1m_population")).map(this::retrieveIntFromField).orElse(null);
        Integer total = Optional.ofNullable(deathObject.get("deaths")).map(this::retrieveIntFromField).orElse(null);

        return new Deaths(balance, per_million, total);
    }

    private Tests parseTests(JSONObject testObject) {
        Integer per_million = Optional.ofNullable(testObject.get("tests_per_1m_population")).map(this::retrieveIntFromField).orElse(null);
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
