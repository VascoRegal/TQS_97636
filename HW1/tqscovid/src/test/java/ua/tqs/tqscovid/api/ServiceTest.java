package ua.tqs.tqscovid.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.tqs.tqscovid.adapter.IExternalAPIAdapter;
import ua.tqs.tqscovid.cache.ICacheService;
import ua.tqs.tqscovid.models.Cases;
import ua.tqs.tqscovid.models.Country;
import ua.tqs.tqscovid.models.DailyStats;
import ua.tqs.tqscovid.models.Deaths;
import ua.tqs.tqscovid.models.Tests;
import ua.tqs.tqscovid.services.CovIncidenceService;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {
    
    @Mock(lenient = true)
    private IExternalAPIAdapter externalAPIAdapter;

    @Mock(lenient = true)
    private ICacheService<String, Object> cacheService;

    @InjectMocks
    private CovIncidenceService service;

    @BeforeEach
    public void setUp() throws URISyntaxException, ParseException, IOException {
        List<DailyStats> all = new ArrayList<>();
        List<DailyStats> ptstats = new ArrayList<>();
        List<Country> countires = new ArrayList<>();
        List<DailyStats> s0304 = new ArrayList<>();

        Country portugal = new Country("Portugal");
        Country france = new Country("France");
        Country japan = new Country("Japan");
        Country zebeq = new Country("Zebequistão");

        countires.add(portugal);
        countires.add(france);
        countires.add(japan);
        countires.add(zebeq);

        DailyStats pt0303 = new DailyStats(LocalDate.parse("2021-03-03"), portugal, 
            new Cases(159, 25, 1, 35111, 21, 12354316),
            new Deaths(8, 234123, 43241),
            new Tests(8913471, 23412));

        DailyStats pt0304 = new DailyStats(LocalDate.parse("2021-03-04"), portugal, 
            new Cases(774, 543, 231, 43, 12, 43214515),
            new Deaths(6654, 12342, 678673416),
            new Tests(25672, 9578125));

        DailyStats jp0304 = new DailyStats(LocalDate.parse("2021-03-04"), japan, 
            new Cases(92, 12, 8, 56, 4, 574),
            new Deaths(423, 3, 54),
            new Tests(831, 856));

        ptstats.add(pt0303); ptstats.add(pt0304);
        all.add(pt0303); all.add(pt0304); all.add(jp0304);
        s0304.add(pt0304); s0304.add(jp0304);

        List<DailyStats> jpstatdaily = new ArrayList<>();
        jpstatdaily.add(jp0304);

        List<DailyStats> ptstatdaily = new ArrayList<>();
        ptstatdaily.add(pt0303);

        Mockito.when(externalAPIAdapter.getCountries()).thenReturn(countires);
        Mockito.when(externalAPIAdapter.getAllStats()).thenReturn(all);
        Mockito.when(externalAPIAdapter.getStatsByCountry("Portugal")).thenReturn(ptstats);
        Mockito.when(externalAPIAdapter.getStatsByDay(LocalDate.parse("2021-03-04"), "Portugal")).thenReturn(ptstatdaily);
        Mockito.when(externalAPIAdapter.getStatsByDay(LocalDate.parse("2021-03-04"), "Japan")).thenReturn(jpstatdaily);
        Mockito.when(externalAPIAdapter.getStatsByDay(LocalDate.parse("2021-03-04"), null)).thenReturn(s0304);

        Mockito.when(cacheService.get("/stats")).thenReturn(Optional.empty());
        Mockito.when(cacheService.get("/countries")).thenReturn(Optional.of(countires));
        Mockito.when(cacheService.get("/countries?country=Portugal")).thenReturn(Optional.empty());
        Mockito.when(cacheService.get("/day?country=Portugal&date=2021-03-04")).thenReturn(Optional.empty());
        Mockito.when(cacheService.get("/day?country=Portugal&date=2021-03-04")).thenReturn(Optional.empty());
        Mockito.when(cacheService.get("/day?country=All&date=2021-03-04")).thenReturn(Optional.empty());
    }
    
    @Test
    void whenGetAllCountires_getAllCountires() throws URISyntaxException, ParseException, IOException {
       assertThat(service.getCountries()).hasSize(4).extracting(Country::getName).contains("Portugal", "Japan", "France", "Zebequistão");

       // Make sure the external api gets no requests
       Mockito.verify(externalAPIAdapter, VerificationModeFactory.times(0)).getCountries();

       // Make sure cache is being queried
       Mockito.verify(cacheService, VerificationModeFactory.times(1)).get("/countries");
    }

    @Test
    void whenGetPortugalStats_RetunPortugalStats() throws ParseException, IOException, URISyntaxException {
        assertThat(service.getStatsByCountry("Portugal")).hasSize(2).extracting(DailyStats::getCountry).extracting(Country::getName).containsOnly("Portugal");
        Mockito.verify(externalAPIAdapter, VerificationModeFactory.times(1)).getStatsByCountry("Portugal");
    }

    @Test
    void whenGetInvalidCountry_ReturnEmpty() throws ParseException, IOException, URISyntaxException {
        assertThat(service.getStatsByCountry("Lisboa")).hasSize(0);
        Mockito.verify(externalAPIAdapter, VerificationModeFactory.times(1)).getStatsByCountry("Lisboa");
    }

    @Test
    void whenGetAllStats_getAllStats() throws ParseException, IOException, URISyntaxException {
        assertThat(service.getAllStats()).hasSize(3).extracting(DailyStats::getCountry).extracting(Country::getName).containsOnly("Portugal", "Japan");
        Mockito.verify(externalAPIAdapter, VerificationModeFactory.times(1)).getAllStats();
    }

    @Test
    void whenGetValidDate_getStats() throws URISyntaxException, ParseException, IOException {
        assertThat(service.getStatsByDay("2021-03-04", "Portugal")).hasSize(1);
        Mockito.verify(externalAPIAdapter, VerificationModeFactory.times(1)).getStatsByDay(LocalDate.parse("2021-03-04"), "Portugal");
    }
}
