package ua.tqs.tqscovid.api;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ua.tqs.tqscovid.controllers.CovIncidenceController;
import ua.tqs.tqscovid.models.Cases;
import ua.tqs.tqscovid.models.Country;
import ua.tqs.tqscovid.models.DailyStats;
import ua.tqs.tqscovid.models.Deaths;
import ua.tqs.tqscovid.models.Tests;
import ua.tqs.tqscovid.services.CovIncidenceService;

@WebMvcTest(CovIncidenceController.class)
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CovIncidenceService service;

    @BeforeEach
    public void setup() throws Exception {
    }

    @Test
    void wehenGetCountries_thenReturnAllCountries() throws Exception {
        List<Country> countries = new ArrayList<Country>();
        countries.add(new Country("Portugal"));
        countries.add(new Country("Belgium"));
        countries.add(new Country("Sweden"));

        when(service.getCountries()).thenReturn(countries);

        mockMvc.perform(
            get("/api/countries").contentType("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].name", is("Portugal")))
            .andExpect(jsonPath("$[1].name", is("Belgium")))
            .andExpect(jsonPath("$[2].name", is("Sweden"))
        );

        verify(service, times(1)).getCountries();
    }

    @Test
    void whenGetAllStats_thenReturnAllStats() throws Exception {
        List<DailyStats> stats = new ArrayList<DailyStats>();
        stats.add(new DailyStats(LocalDate.parse("2021-03-01"),
                    new Country("Portugal"),
                    new Cases(12, 342412, 12, 321899, 3123, 654),
                    new Deaths(55, 423, 21),
                    new Tests(21324, 123)));
        stats.add(new DailyStats(LocalDate.parse("2021-05-01"),
                    new Country("France"),
                    new Cases(441, 34712, 12, 32, null, 654),
                    new Deaths(68834, 321,56),
                    new Tests(75635, null)));

        when(service.getAllStats()).thenReturn(stats);

        mockMvc.perform(
            get("/api/stats").contentType("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].cases.balance", is(12)))
            .andExpect(jsonPath("$[0].country.name", is("Portugal")))
            .andExpect(jsonPath("$[1].deaths.total", is(56)))
            .andExpect(jsonPath("$[1].tests.total").value(IsNull.nullValue()));

        verify(service, times(1)).getAllStats();
    }

    @Test
    void whenGetStatsByExisitngCountry_thenReturnStats() throws Exception {
        List<DailyStats> stats = new ArrayList<DailyStats>();
        stats.add(new DailyStats(LocalDate.parse("2021-03-01"),
                    new Country("Portugal"),
                    new Cases(12, 342412, 12, 321899, 3123, 654),
                    new Deaths(55, 423, 21),
                    new Tests(21324, 123)));
        stats.add(new DailyStats(LocalDate.parse("2021-05-01"),
                    new Country("Portugal"),
                    new Cases(441, 34712, 12, 32, null, 654),
                    new Deaths(68834, 321,56),
                    new Tests(75635, null)));

        when(service.getStatsByCountry("Portugal")).thenReturn(stats);

        mockMvc.perform(
            get("/api/stats/country?country=Portugal").contentType("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].cases.balance", is(12)))
            .andExpect(jsonPath("$[0].country.name", is("Portugal")))
            .andExpect(jsonPath("$[1].country.name", is("Portugal")))
            .andExpect(jsonPath("$[1].deaths.total", is(56)))
            .andExpect(jsonPath("$[1].tests.total").value(IsNull.nullValue()));

        verify(service, times(1)).getStatsByCountry("Portugal");
    }

    @Test
    void whenGetStatsByInvalidCountry_thenReturnEmpty() throws Exception {
        List<DailyStats> stats = new ArrayList<DailyStats>();
        stats.add(new DailyStats(LocalDate.parse("2021-03-01"),
                    new Country("Portugal"),
                    new Cases(12, 342412, 12, 321899, 3123, 654),
                    new Deaths(55, 423, 21),
                    new Tests(21324, 123)));
        stats.add(new DailyStats(LocalDate.parse("2021-05-01"),
                    new Country("Portugal"),
                    new Cases(441, 34712, 12, 32, null, 654),
                    new Deaths(68834, 321,56),
                    new Tests(75635, null)));

        when(service.getStatsByCountry("Portugal")).thenReturn(stats);

        mockMvc.perform(
            get("/api/stats/country?country=Portu").contentType("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));

        verify(service, times(1)).getStatsByCountry("Portu");
    }

    @Test
    void whenGetStatsByValidLocalDateAndValidCountry_thenReturnStats() throws Exception {
        DailyStats ds = new DailyStats(LocalDate.parse("2021-03-01"),
                    new Country("Portugal"),
                    new Cases(12, 342412, 12, 321899, 3123, 654),
                    new Deaths(55, 423, 21),
                    new Tests(21324, 123));

        when(service.getStatsByDay("2021-03-01", "Portugal")).thenReturn(ds);

        mockMvc.perform(
            get("/api/stats/day?day=2021-03-01&country=Portugal").contentType("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.country.name", is("Portugal")))
            .andExpect(jsonPath("$.day", is("2021-03-01")));

        verify(service, times(1)).getStatsByDay("2021-03-01", "Portugal");
    }

    @Test
    void whenGetStatsByInvalidLocalDate_thenReturnNullStats() throws Exception {
        when(service.getStatsByDay("invaliddate", "Portugal")).thenReturn(new DailyStats(null, null, null, null, null));

        mockMvc.perform(
        get("/api/stats/day?day=invaliddate&country=Portugal").contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.cases", IsNull.nullValue()))
        .andExpect(jsonPath("$.tests", IsNull.nullValue()))
        .andExpect(jsonPath("$.day", IsNull.nullValue()));

        verify(service, times(1)).getStatsByDay("invaliddate", "Portugal");
    }
}
