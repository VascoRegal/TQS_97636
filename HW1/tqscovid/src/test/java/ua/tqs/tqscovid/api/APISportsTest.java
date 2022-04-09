package ua.tqs.tqscovid.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.tqs.tqscovid.adapter.APISportsAdapter;
import ua.tqs.tqscovid.http.IHttpClient;
import ua.tqs.tqscovid.models.Cases;
import ua.tqs.tqscovid.models.Country;
import ua.tqs.tqscovid.models.DailyStats;
import ua.tqs.tqscovid.models.Deaths;
import ua.tqs.tqscovid.models.Tests;
import ua.tqs.tqscovid.utils.ConfigUtils;

@ExtendWith(MockitoExtension.class)
public class APISportsTest {
    
    @Mock(lenient = true)
    private IHttpClient httpClient;

    @InjectMocks
    private APISportsAdapter apiSportsAdapter;

    @BeforeEach
    void setup() throws IOException, ParseException {
        String baseurl = "https://covid-193.p.rapidapi.com";
        Map<String, Object> headers = new HashMap<>();
        headers.put("X-RapidAPI-Key", ConfigUtils.getPropertyFromConfig("rapidapi.key"));

        String countriesResp = "{\"get\":\"countries\",\"parameters\":[],\"errors\":[],\"results\":233,\"response\":[\"Afghanistan\",\"Albania\",\"Algeria\",\"Andorra\"]}";
        String portugalStats = "{\"get\":\"history\",\"parameters\":{\"country\":\"Portugal\"},\"errors\":[],\"results\":1551,\"response\":[{\"continent\":\"Europe\",\"country\":\"Portugal\",\"population\":10144258,\"cases\":{\"new\":null,\"active\":null,\"critical\":61,\"recovered\":null,\"1M_pop\":\"361503\",\"total\":3667184},\"deaths\":{\"new\":null,\"1M_pop\":\"2156\",\"total\":21874},\"tests\":{\"1M_pop\":\"3985828\",\"total\":40433267},\"day\":\"2022-04-09\",\"time\":\"2022-04-09T21:45:02+00:00\"}]}";
        Mockito.when(httpClient.doGet(baseurl + "/countries", headers)).thenReturn(countriesResp);
        Mockito.when(httpClient.doGet(baseurl + "/history?country=Portugal", headers)).thenReturn(portugalStats);
        Mockito.when(httpClient.doGet(baseurl + "/history?country=Portugal&day=2022-04-09", headers)).thenReturn(portugalStats);
    }

    @Test
    void countriesResp_ToCountryObjects() throws ParseException, IOException {
        Country afghanistan = new Country("Afghanistan");
        Country albania = new Country("Albania");
        Country algeria = new Country("Algeria");
        Country andorra = new Country("Andorra");

        List<Country> countries = new ArrayList<>();
        countries.add(afghanistan);
        countries.add(albania);
        countries.add(algeria);
        countries.add(andorra);

        assertThat(this.apiSportsAdapter.getCountries()).hasSameElementsAs(countries);
    }

    @Test
    void countryStats_ToStatsList() throws ParseException, IOException, URISyntaxException {
        List<DailyStats> stats = new ArrayList<>();

        stats.add(new DailyStats(LocalDate.parse("2022-04-09"), new Country("Portugal"),
                  new Cases(null, null, 61, null, 361503, 3667184), 
                  new Deaths(null, 2156, 21874),
                  new Tests(3985828, 40433267)));

        assertThat(this.apiSportsAdapter.getStatsByCountry("Portugal")).hasSameElementsAs(stats);
    }

    @Test
    void dayStats_ByDay() throws URISyntaxException, ParseException, IOException {
        List<DailyStats> stats = new ArrayList<>();

        stats.add(new DailyStats(LocalDate.parse("2022-04-09"), new Country("Portugal"),
                  new Cases(null, null, 61, null, 361503, 3667184), 
                  new Deaths(null, 2156, 21874),
                  new Tests(3985828, 40433267)));

        assertThat(this.apiSportsAdapter.getStatsByDay(LocalDate.parse("2022-04-09"), "Portugal")).hasSameElementsAs(stats);
    }
}