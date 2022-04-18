package ua.tqs.tqscovid.services;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import ua.tqs.tqscovid.adapter.IExternalAPIAdapter;
import ua.tqs.tqscovid.cache.ICacheService;
import ua.tqs.tqscovid.models.Country;
import ua.tqs.tqscovid.models.DailyStats;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CovIncidenceService {
    private IExternalAPIAdapter externalAPIAdapter;

    private ICacheService<String, Object> cache;

    public CovIncidenceService(IExternalAPIAdapter externalAPIAdapter, ICacheService<String, Object> cacheService) {
        this.externalAPIAdapter = externalAPIAdapter;
        this.cache = cacheService;
    }

    public List<DailyStats> getAllStats() throws ParseException, IOException, URISyntaxException {
        String cache_key = "/stats";
        Optional<Object> cacheObj = cache.get(cache_key);

        if (cacheObj.isPresent()) {
            return (List<DailyStats>) cacheObj.get();
        }

        List<DailyStats> results = this.externalAPIAdapter.getAllStats();
        cache.put(cache_key, results);
        return results;
    }   

    public List<Country> getCountries() throws URISyntaxException, ParseException, IOException {
        String cache_key = "/countries";
        Optional<Object> cacheObj = cache.get(cache_key);

        if (cacheObj.isPresent()) {
            return (List<Country>) cacheObj.get();
        }

        List<Country> results = this.externalAPIAdapter.getCountries();
        cache.put(cache_key, results);
        return results;
    };

    public List<DailyStats> getStatsByCountry(String country) throws ParseException, IOException, URISyntaxException {
        String cache_key = "/countries?country" + country;
        Optional<Object> cacheObj = cache.get(cache_key);

        if (cacheObj.isPresent()) {
            return (List<DailyStats>) cacheObj.get();
        }

        List<DailyStats> results = this.externalAPIAdapter.getStatsByCountry(country);
        cache.put(cache_key, results);
        return results;
    };

    public List<DailyStats> getStatsByDay(String date, String country) throws URISyntaxException, ParseException, IOException {
        LocalDate ldate;
        try {
            ldate = LocalDate.parse(date);
        } catch(Exception e) {
            return new ArrayList<DailyStats>();
        }

        if (country == null) {
            country = "All";
        }

        String cache_key = "/day?country" + country + "&date=" + date;
        Optional<Object> cacheObj = cache.get(cache_key);

        if (cacheObj.isPresent()) {
            return (List<DailyStats>) cacheObj.get();
        }

        List<DailyStats> results = this.externalAPIAdapter.getStatsByDay(ldate, country);
        cache.put(cache_key, results);
        return results;
    };

}
