package ua.tqs.tqscovid.api;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.tqs.tqscovid.adapter.APISportsAdapter;
import ua.tqs.tqscovid.adapter.IExternalAPIAdapter;
import ua.tqs.tqscovid.cache.CacheServiceImpl;
import ua.tqs.tqscovid.http.CovIncidenceHttpClient;
import ua.tqs.tqscovid.http.IHttpClient;
import ua.tqs.tqscovid.models.Cases;
import ua.tqs.tqscovid.models.Country;
import ua.tqs.tqscovid.models.DailyStats;
import ua.tqs.tqscovid.models.Deaths;
import ua.tqs.tqscovid.models.Tests;
import ua.tqs.tqscovid.services.CovIncidenceService;

public class ServiceApiSportsIT {
    private IHttpClient httpClient;
    private IExternalAPIAdapter adapter;
    private CovIncidenceService service;
    private CacheServiceImpl cache;

    @BeforeEach
    public void setup() {
        this.httpClient = new CovIncidenceHttpClient();
        this.cache = new CacheServiceImpl();
        this.cache.setExpiracy(5);
        this.service = new CovIncidenceService();
    }

    @Test
    public void getPortugalMay132021_returnsDailyStats() throws URISyntaxException, ParseException, IOException {
        List<DailyStats> expected = new ArrayList<>();
        
        expected.add( new DailyStats(
            LocalDate.parse("2021-05-13"), 
            new Country("Portugal"),
            new Cases(436, 21969, 70, 801961, 82679, 840929),
            new Deaths(1, 1671, 16999), 
            new Tests(1082588, 11011029)));

        assertTrue(expected.equals(this.service.getStatsByDay("2021-05-13", "Portugal")));
    }
}
