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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.tqs.tqscovid.adapter.VACCOVIDAdapter;
import ua.tqs.tqscovid.http.IHttpClient;
import ua.tqs.tqscovid.models.Cases;
import ua.tqs.tqscovid.models.Country;
import ua.tqs.tqscovid.models.DailyStats;
import ua.tqs.tqscovid.models.Deaths;
import ua.tqs.tqscovid.models.Tests;
import ua.tqs.tqscovid.utils.ConfigUtils;

@ExtendWith(MockitoExtension.class)
public class VACCOVIDTest {
    
    @Mock(lenient = true)
    private IHttpClient httpClient;

    private VACCOVIDAdapter vaccovidAdapter;

    @BeforeEach
    void setup() throws IOException, ParseException {
        MockitoAnnotations.initMocks(this);
        String baseurl = "https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com/api";
        Map<String, Object> headers = new HashMap<>();
        headers.put("X-RapidAPI-Key", ConfigUtils.getPropertyFromConfig("rapidapi.key"));

        String countriesResp = "[{\"Country\":\"Afghanistan\",\"ThreeLetterSymbol\":\"afg\"},{\"Country\":\"Albania\",\"ThreeLetterSymbol\":\"alb\"},{\"Country\":\"Algeria\",\"ThreeLetterSymbol\":\"dza\"},{\"Country\":\"Portugal\",\"ThreeLetterSymbol\":\"prt\"}]";
        String portugalStats = "[{\"id\":\"02ac2181-0648-401c-87e9-9c97071f3d06\",\"symbol\":\"PRT\",\"Country\":\"Portugal\",\"Continent\":\"Europe\",\"date\":\"2022-01-30\",\"total_cases\":2611886,\"new_cases\":45335,\"total_deaths\":19856,\"new_deaths\":29,\"total_tests\":0,\"new_tests\":0}]";
        Mockito.when(httpClient.doGet(baseurl + "/npm-covid-data/countries-name-ordered", headers)).thenReturn(countriesResp);
        Mockito.when(httpClient.doGet(baseurl + "/covid-ovid-data/sixmonth/prt", headers)).thenReturn(portugalStats);

        this.vaccovidAdapter = new VACCOVIDAdapter(httpClient);
    }

    @Test
    void countriesResp_ToCountryObjects() throws ParseException, IOException, URISyntaxException {
        Country afghanistan = new Country("Afghanistan");
        Country albania = new Country("Albania");
        Country algeria = new Country("Algeria");
        Country portugal = new Country("Portugal");

        List<Country> countries = new ArrayList<>();
        countries.add(afghanistan);
        countries.add(albania);
        countries.add(algeria);
        countries.add(portugal);

        assertThat(this.vaccovidAdapter.getCountries()).hasSameElementsAs(countries);
    }

    @Test
    void countryStats_ToStatsList() throws ParseException, IOException, URISyntaxException {
        List<DailyStats> stats = new ArrayList<>();

        stats.add(new DailyStats(LocalDate.parse("2022-01-30"), new Country("Portugal"),
                  new Cases(45335, null, null, null, null, 2611886), 
                  new Deaths(29, null, 19856),
                  new Tests(null, 0)));

        assertThat(this.vaccovidAdapter.getStatsByCountry("Portugal")).hasSameElementsAs(stats);
    }
}